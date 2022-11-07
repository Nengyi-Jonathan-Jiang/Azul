package Game.Scenes;

import Engine.Components.*;
import Engine.Core.AbstractScene;
import Engine.Core.GameCanvas;
import Engine.Core.GameObject;
import Engine.Core.Vec2;
import Game.Backend.Factory;
import Game.Backend.Game;
import Game.Backend.Player;
import Game.Backend.Tile;
import Game.Style;
import Game.App;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class FactoryOfferingScene extends AbstractGameScene {
    private final GameObject playerTurnIndicator;
    private GameObject instructions;
    private boolean finished = false;

    private Player player;

    public FactoryOfferingScene(Game game, Player player) {
        super(game);
        this.player = player;

        playerTurnIndicator = new GameObject(
                new RectRendererComponent(Style.FG_COLOR, Style.BG_COLOR),
                new TextRendererComponent(player.getName() + "'s Turn", new TextStyle(Style.font_medium, Style.FG_COLOR, TextStyle.ALIGN_CENTER))
        );
        Vec2 TEXT_SIZE = playerTurnIndicator.getComponent(TextRendererComponent.class).getRenderedSize()
                .plus(new Vec2(Style.TEXT_PADDING * 2.5));
        playerTurnIndicator.setSize(TEXT_SIZE);

        playerTurnIndicator.setTopLeft(Vec2.zero);

        instructions = new GameObject(
                new RectRendererComponent(Style.FG_COLOR, Style.BG_COLOR),
                new TextRendererComponent("", new TextStyle(Style.font_medium, Style.FG_COLOR, TextStyle.ALIGN_CENTER))
        );

        setInstructions("Click on a tile in the factories or the center to select it");
    }

    public void setInstructions(String text) {
        instructions.getComponent(TextRendererComponent.class).setText(text);
        Vec2 TEXT_SIZE = instructions.getComponent(TextRendererComponent.class)
                .getRenderedSize()
                .plus(new Vec2(Style.TEXT_PADDING * 2.5));
        instructions.setSize(TEXT_SIZE);
        instructions.setTopRight(new Vec2(App.WIDTH, 0));
    }


    private List<Tile> selectedTiles = new ArrayList<>();

    @Override
    public void onMouseClick(MouseEvent me) {
        List<Factory> factories = game.getMiddle().getFactories();

        boolean found = false;
        outer:
        for (Factory factory : factories) {
            List<Tile> allTiles = factory.getAllTiles();
            for (Tile t : allTiles) {
                if (t.getGameObject().getComponent(ButtonComponent.class).contains(me)) {
                    selectTile(factory, t);
                    found = true;
                    break outer;
                }
            }
        }

        if (!found) selectedTiles.clear();

        // Unhighlight all non-selected tiles
        factories.forEach(f -> f.getAllTiles().forEach(t -> {
            if (selectedTiles.contains(t)) t.highlight();
            else t.unHighlight();
        }));
    }

    @Override
    public void onExecutionEnd() {
        game.getMiddle().getFactories().forEach(f -> f.getAllTiles().forEach(Tile::unHighlight));
    }

    private void selectTile(Factory f, Tile t) {
        if (selectedTiles.contains(t)) {
            // Unhighlight all tiles
            game.getMiddle().getFactories().stream().map(Factory::getAllTiles).forEach(l -> l.forEach(Tile::unHighlight));

            f.removeTilesOfColor(t.getColor());

            player.getHand().clear();
            for (Tile tile : selectedTiles) {
                Vec2 originalPos = tile.getGameObject()
                        .getAbsolutePosition()
                        .minus(game.getGameObject().getAbsolutePosition());

                player.getHand().addTile(tile);

                Vec2 targetPos = tile.getGameObject().getPosition();

                tile.getGameObject().setPosition(originalPos);
                tile.getGameObject().getComponent(PositionAnimationComponent.class).moveTo(targetPos, 10);

            }

            //game.getMiddle().getCenter().addTiles(f.removeTilesOfColor(t.getColor()));

            f.removeAllTiles().forEach(tile -> {
                Vec2 originalPos = tile.getGameObject()
                        .getAbsolutePosition()
                        .minus(game.getGameObject().getAbsolutePosition());

                game.getMiddle().getCenter().addTile(tile);

                Vec2 targetPos = tile.getGameObject().getPosition();

                tile.getGameObject().setPosition(originalPos);
                tile.getGameObject().getComponent(PositionAnimationComponent.class).moveTo(targetPos, 10);

            });

            finished = true;
        } else {
            selectedTiles = f.getTilesOfColor(t.getColor());
        }
    }

    @Override
    public void draw(GameCanvas canvas) {
        super.draw(canvas);
        playerTurnIndicator.draw(canvas);
        instructions.draw(canvas);
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
