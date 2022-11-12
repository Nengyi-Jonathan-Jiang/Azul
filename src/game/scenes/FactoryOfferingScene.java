package game.scenes;

import engine.components.*;
import engine.core.GameCanvas;
import engine.core.GameObject;
import engine.core.Vec2;
import game.backend.*;
import game.frontend.TextObject;
import game.App;
import game.util.PositionAnimation;

import javax.swing.*;
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


    private List<Tile> selectedTiles = null;
    private AbstractTileSet selectedTileSet = null;
    private Tile selectedTile = null;

    private PatternLine selectedLine = null;


    public FactoryOfferingScene(Game game, Player player) {
        super(game);
        this.player = player;

        playerTurnIndicator = new TextObject(player.getName() + "'s Turn");
        playerTurnIndicator.setTopLeft(Vec2.zero);

        instructions = new TextObject("");
        setInstructions("Click on a tile in the factories or the center to select it");

        confirmButton = new TextObject("Confirm Move");
        confirmButton.setBottomRight(new Vec2(App.WIDTH, App.HEIGHT));
    }

    public void setInstructions(String text) {
        instructions.setText(text);
        instructions.setTopRight(new Vec2(App.WIDTH, 0));
    }

    @Override
    public void onMouseClick(MouseEvent me) {
        // Ignore middle and right click
        if(!SwingUtilities.isLeftMouseButton(me)) return;

        // Handle click on tile
        for (AbstractTileSet factory : Stream.concat(
            game.getMiddle().getFactories().stream(),
            Stream.of(game.getMiddle().getCenter())
        ).collect(Collectors.toList())) {
            for (Tile t : factory.getAllTiles()) {
                if (t.getGameObject().getComponent(ButtonComponent.class).contains(me)) {
                    unselectPatternLine();
                    unselectTiles();
                    selectTile(factory, t);
                    return;
                }
            }
        }

        // Handle click on rows
        if(selectedTiles != null){
            for(PatternLine line : player.getAvailablePatternLinesForColor(selectedTile.getColor())){
                if(line.getGameObject().getComponent(ButtonComponent.class).contains(me)){
                    unselectPatternLine();
                    selectPatternLine(line);
                    return;
                }
            }
        }

        // Handle continue button
        if(selectedTiles != null && selectedLine != null) {
            if (confirmButton.getComponent(ButtonComponent.class).contains(me)) {
                confirmSelect();
                return;
            }
        }

        // If nothing was clicked, unselect everything
        unselectPatternLine();
        unselectTiles();
    }

    private void selectPatternLine(PatternLine line) {
        selectedLine = line;

        player.getPatternLines().getLines().forEach(PatternLine::unHighlight);
        line.highlight();
    }

    private void unselectPatternLine() {
        if(selectedLine == null) return;

        selectedLine.unHighlight();
        selectedLine = null;

        if(selectedTileSet != null && selectedTile != null)
            selectTile(selectedTileSet, selectedTile);
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

        setInstructions("Click on the pattern line you will place the tile in");
    }

    private void unselectTiles() {
        if(selectedTiles == null) return;

        selectedTiles.forEach(Tile::unHighlight);

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

    private void confirmSelect() {
        if(selectedTiles == null || selectedLine == null) return;

        // Unhighlight selected tiles and selected line
        selectedTiles.forEach(Tile::unHighlight);
        selectedLine.unHighlight();

        // Update tileset
        selectedTileSet.removeTilesOfColor(selectedTile.getColor());

        // Animate move to line
        for (Tile tile : selectedTiles) {
            if(selectedLine.isFilled() || tile.isFirstPlayerMarker()){
                if(player.getFloorLine().isFull()){
                    game.getBag().returnTile(tile);
                    tile.getGameObject().removeFromParent();

                    System.out.println("Floor line overflow on tile " + tile);
                }
                else {
                    PositionAnimation.animate(tile.getGameObject(), () -> player.getFloorLine().push(tile), 10);

                    System.out.println("Pattern line overflow on tile " + tile);
                }
            }
            else{
                PositionAnimation.animate(tile.getGameObject(), () -> selectedLine.addTile(tile), 10);

                System.out.println("Moved " + tile + " to pattern line");
            }
        }
        selectedTiles.clear();

        // Move remaining tiles to center
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

        finished = true;
    }

    @Override
    public void draw(GameCanvas canvas) {
        super.draw(canvas);
        playerTurnIndicator.draw(canvas);
        instructions.draw(canvas);

        if(selectedTile != null && selectedLine != null)
            confirmButton.draw(canvas);
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}