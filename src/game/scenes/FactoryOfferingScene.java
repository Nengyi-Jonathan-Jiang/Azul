package game.scenes;

import engine.components.*;
import engine.core.GameCanvas;
import engine.core.GameObject;
import engine.core.Vec2;
import game.backend.*;
import game.frontend.TextObject;
import game.App;
import game.util.PositionAnimation;

import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FactoryOfferingScene extends PanningGameScene {
    private final TextObject playerTurnIndicator;
    private final TextObject instructions;
    private final Player player;
    private final TextObject confirmButton;
    private boolean finished = false;

    public FactoryOfferingScene(Game game, Player player) {
        super(game);
        this.player = player;

        playerTurnIndicator = new TextObject(player.getName() + "'s Turn");
        playerTurnIndicator.setTopLeft(Vec2.zero);

        instructions = new TextObject("");
        setInstructions("Click on a tile in the factories or the center to select it");

        confirmButton = new TextObject("Confirm tile selection");
        confirmButton.setBottomRight(new Vec2(App.WIDTH, App.HEIGHT));
    }

    public void setInstructions(String text) {
        instructions.setText(text);
        instructions.setTopRight(new Vec2(App.WIDTH, 0));
    }


    private List<Tile> selectedTiles = null;
    private AbstractTileSet selectedTileSet = null;
    private Tile selectedTile = null;

    @Override
    public void onMouseClick(MouseEvent me) {

        // Handle continue button
        if (confirmButton.getComponent(ButtonComponent.class).contains(me)) {
            confirmSelect();
            return;
        }

        // Handle click on tile

        List<AbstractTileSet> tileSets = Stream.concat(
            game.getMiddle().getFactories().stream(),
            Stream.of(game.getMiddle().getCenter())
        ).collect(Collectors.toList());

        for (AbstractTileSet factory : tileSets) {
            for (Tile t : factory.getAllTiles()) {
                if (t.getGameObject().getComponent(ButtonComponent.class).contains(me)) {
                    selectTile(factory, t);
                    return;
                }
            }
        }


        // Handle click in center
        for (Tile t : game.getMiddle().getCenter().getAllTiles()) {
            if (t.getGameObject().getComponent(ButtonComponent.class).contains(me)) {
                selectTile(game.getMiddle().getCenter(), t);
                return;
            }
        }

        unselectTiles();
    }

    private void unselectTiles() {
        if(selectedTiles != null) selectedTiles.forEach(Tile::unHighlight);

        selectedTiles = null;
        selectedTileSet = null;
        selectedTile = null;

        player.getPatternLines().getLines().forEach(PatternLine::unHighlight);

        setInstructions("Click on a tile in the factories or the center to select it");
    }

    @Override
    public void onExecutionEnd() {
        game.getMiddle().getFactories().forEach(f -> f.getAllTiles().forEach(Tile::unHighlight));
        game.getMiddle().getCenter().getAllTiles().forEach(Tile::unHighlight);
    }

    private void selectTile(AbstractTileSet s, Tile t) {
        if(t.isFirstPlayerMarker()) return;

        if(selectedTiles != null) selectedTiles.forEach(Tile::unHighlight);

        selectedTiles = s.getTilesOfColor(t.getColor());
        selectedTileSet = s;
        selectedTile = t;

        selectedTiles.forEach(Tile::highlight);

        player.getPatternLines().getLines().forEach(PatternLine::unHighlight);
        player.getAvailablePatternLinesForColor(t.getColor()).forEach(PatternLine::highlight);

        setInstructions("Click the confirm button to continue");
    }

    private void confirmSelect() {
        if(selectedTiles == null) return;

        // Unhighlight all tiles
        game.getMiddle().getFactories().stream().map(Factory::getAllTiles).forEach(l -> l.forEach(Tile::unHighlight));
        game.getMiddle().getCenter().getAllTiles().forEach(Tile::unHighlight);

        selectedTileSet.removeTilesOfColor(selectedTile.getColor());

        player.getHand().clear();
        for (Tile tile : selectedTiles) {
            PositionAnimation.animate(tile.getGameObject(), () -> player.getHand().addTile(tile), 10);
        }

        selectedTiles.clear();

        List<Tile> removed =
                Stream.concat(
                                selectedTileSet.removeAllTiles().stream(),
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

        game.getMiddle().getFactories().forEach(f -> f.getAllTiles().forEach(Tile::unHighlight));
        game.getMiddle().getCenter().getAllTiles().forEach(Tile::unHighlight);
        selectedTiles.forEach(Tile::highlight);

        finished = true;
    }

    @Override
    public void draw(GameCanvas canvas) {
        super.draw(canvas);
        playerTurnIndicator.draw(canvas);
        instructions.draw(canvas);

        if(selectedTile != null)
            confirmButton.draw(canvas);
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
