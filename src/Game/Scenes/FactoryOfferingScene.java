package Game.Scenes;

import Engine.Components.*;
import Engine.Core.GameCanvas;
import Engine.Core.GameObject;
import Engine.Core.Vec2;
import Game.Backend.*;
import Game.Style;
import Game.App;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        // Handle click on tile in factory

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


        // Handle click in center
        if (!found) {
            List<Tile> allTiles = game.getMiddle().getCenter().getAllTiles()
                    .stream().filter(Tile::isColorTile)
                    .collect(Collectors.toList());

            for (Tile t : allTiles) {
                if (t.getGameObject().getComponent(ButtonComponent.class).contains(me)) {
                    selectTile(game.getMiddle().getCenter(), t);
                    break;
                }
            }
        }

        // Unhighlight all non-selected tiles
        factories.forEach(f -> f.getAllTiles().forEach(Tile::unHighlight));
        game.getMiddle().getCenter().getAllTiles().forEach(Tile::unHighlight);
        selectedTiles.forEach(Tile::highlight);
    }

    @Override
    public void onExecutionEnd() {
        game.getMiddle().getFactories().forEach(f -> f.getAllTiles().forEach(Tile::unHighlight));
        game.getMiddle().getCenter().getAllTiles().forEach(Tile::unHighlight);
    }

    private void selectTile(AbstractTileSet s, Tile t) {
        if (selectedTiles.contains(t)) {
            // Unhighlight all tiles
            game.getMiddle().getFactories().stream().map(Factory::getAllTiles).forEach(l -> l.forEach(Tile::unHighlight));
            game.getMiddle().getCenter().getAllTiles().forEach(Tile::unHighlight);

            s.removeTilesOfColor(t.getColor());

            player.getHand().clear();
            for (Tile tile : selectedTiles) {
                Vec2 originalPos = tile.getGameObject()
                        .getAbsolutePosition()
                        .minus(player.getHand().getGameObject().getAbsolutePosition());

                player.getHand().addTile(tile);

                Vec2 targetPos = tile.getGameObject().getPosition();

                tile.getGameObject().setPosition(originalPos);
                tile.getGameObject().getComponent(PositionAnimationComponent.class).moveTo(targetPos, 10);
            }

            selectedTiles.clear();

            List<Tile> removed = Stream.concat(
                    s.removeAllTiles().stream(),
                    game.getMiddle().getCenter().removeAllTiles().stream()
            )
                    .sorted(Tile::compareTo)
                    .collect(Collectors.toList());
            List<Vec2> originalPositions = removed.stream().map(Tile::getGameObject).map(GameObject::getAbsolutePosition).collect(Collectors.toList());
            removed.stream().map(Tile::getGameObject).forEach(GameObject::removeFromParent);

            for (int i = 0; i < removed.size(); i++) {
                Tile tile = removed.get(i);
                Vec2 originalPos = originalPositions.get(i);

                game.getMiddle().getCenter().addTile(tile);

                Vec2 targetPos = tile.getGameObject().getPosition();

                Vec2 newPosition = tile.getGameObject().getAbsolutePosition();
                Vec2 delta = newPosition.minus(originalPos);

                tile.getGameObject().setPosition(targetPos.minus(delta));
                tile.getGameObject().getComponent(PositionAnimationComponent.class).moveTo(targetPos, 10);
            }

            finished = true;
        } else {
            selectedTiles = s.getTilesOfColor(t.getColor());
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
