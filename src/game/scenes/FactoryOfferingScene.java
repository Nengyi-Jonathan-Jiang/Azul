package game.scenes;

import engine.components.ButtonComponent;
import engine.components.PositionAnimationComponent;
import engine.components.RectRendererComponent;
import engine.core.GameCanvas;
import engine.core.GameObject;
import engine.core.Input;
import engine.core.Vec2;
import engine.input.MouseEvent;
import game.Style;
import game.backend.*;
import game.backend.board.AbstractTileSet;
import game.backend.player.FloorLine;
import game.backend.player.ILine;
import game.backend.player.PatternLine;
import game.backend.player.Player;
import game.frontend.TextObject;
import game.util.PositionAnimation;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FactoryOfferingScene extends PanningGameScene {
    private final TextObject playerTurnIndicator;
    private final TextObject instructions;
    private final Player player;
    private final TextObject confirmButton;
    private boolean finished = false;

    private final GameObject panel;
    private static final double PANEL_SIZE = 65;


    private List<Tile> selectedTiles = null;
    private AbstractTileSet selectedTileSet = null;
    private Tile selectedTile = null;
    private List<ILine> availableLines = null;
    private ILine selectedLine = null;


    public FactoryOfferingScene(Game game, Player player) {
        super(game);
        this.player = player;

        panel = new GameObject(new RectRendererComponent(Style.FG_COLOR, Style.makeTransparent(Style.DM_COLOR, 127)));

        playerTurnIndicator = new TextObject(player.getName() + "'s Turn");
        playerTurnIndicator.setSize(new Vec2(180, 45));

        instructions = new TextObject("");
        instructions.setSize(new Vec2(PANEL_SIZE - 2 * Style.TEXT_PADDING, 45));
        setInstructions("Click on a tile in the factories or the center to select it");

        System.out.println(instructions.getSize().y);

        confirmButton = new TextObject("Next");

        panel.addChildren(playerTurnIndicator, instructions, confirmButton);
    }

    @Override
    public void onExecutionEnd() {
        player.unhighlight();
    }

    @Override
    public void onExecutionStart() {
        player.highlight();
    }

    public void setInstructions(String text) {
        instructions.setText(text);
    }

    public void updateUIPositions(GameCanvas canvas) {
        panel.setSize(new Vec2(canvas.getWidth(), PANEL_SIZE));
        panel.setTopLeft(Vec2.zero);

        Vec2 offset = panel.getTopLeftOffset();
        playerTurnIndicator.setTopLeft(new Vec2(Style.TEXT_PADDING, Style.TEXT_PADDING).plus(offset));
        instructions.setTopLeft(new Vec2(Style.TEXT_PADDING + 200, Style.TEXT_PADDING).plus(offset));

        confirmButton.setTopRight(new Vec2(canvas.getWidth() - Style.TEXT_PADDING, Style.TEXT_PADDING).plus(offset));

        if (selectedTile != null && selectedLine != null) {
            confirmButton.enable();
        }
        else{
            confirmButton.disable();
        }
    }

    @Override
    public void onMouseClick(MouseEvent me) {
        // Ignore middle and right click
        if (me.button != MouseEvent.MouseButton.LEFT) return;

        // Handle click on tile
        for (AbstractTileSet factory : game.getMiddle().getAllTileSets()) {
            for (Tile t : factory.getAllTiles()) {
                if (t.isFirstPlayerMarker()) continue;
                if (t.getGameObject().getComponent(ButtonComponent.class).contains(me.position)) {
                    unselectTiles();
                    selectTile(factory, t);
                    return;
                }
            }
        }

        // Handle click on rows
        if (selectedTiles != null) {
            for (ILine line : player.getAvailableLinesForColor(selectedTile.getColor())) {
                if (line.getGameObject().getComponent(ButtonComponent.class).contains(me.position)) {
                    unselectPatternLine();
                    selectPatternLine(line);
                    return;
                }
            }
        }

        // Handle continue button
        if (selectedTiles != null && selectedLine != null) {
            if (confirmButton.getComponent(ButtonComponent.class).contains(me.position)) {
                confirmSelect();
                return;
            }
        }

        // If nothing was clicked, unselect everything
        unselectTiles();
    }

    private void selectPatternLine(ILine line) {
        selectedLine = line;
        setInstructions("Click the next button to finish your turn");
    }

    private void unselectPatternLine() {
        selectedLine = null;
        setInstructions("Click on the pattern line you will place the tile in");
    }

    private void selectTile(AbstractTileSet s, Tile t) {
        selectedTiles = s.getTilesOfColor(t.getColor());
        selectedTileSet = s;
        selectedTile = t;
        availableLines = player.getAvailableLinesForColor(selectedTile.getColor());

        setInstructions("Click on the pattern line you will place the tile in");
    }

    private void unselectTiles() {
        selectedTiles = null;
        selectedTileSet = null;
        selectedTile = null;
        availableLines = null;
        selectedLine = null;

        setInstructions("Click on a tile in the factories or the center to select it");
    }

    private void unHighlightAll() {
        game.getMiddle().getFactories().forEach(f -> f.getAllTiles().forEach(Tile::unHighlight));
        game.getMiddle().getCenter().getAllTiles().forEach(Tile::unHighlight);
        player.getPatternLines().getLines().forEach(PatternLine::unHighlight);
        player.getFloorLine().unHighlight();
        if (selectedLine != null) selectedLine.unHighlight();
        if (selectedTiles != null) selectedTiles.forEach(Tile::unHighlight);
        if (selectedTile != null) selectedTile.unHighlight();
        if (availableLines != null) availableLines.forEach(ILine::unHighlight);
    }

    private void confirmSelect() {
        if (selectedTiles == null || selectedLine == null) return;

        unHighlightAll();

        // Remove selected tiles from tileset
        selectedTileSet.removeTilesOfColor(selectedTile.getColor());

        // Animate tile movement to pattern line
        for (Tile tile : selectedTiles) {
            if (selectedLine instanceof FloorLine || !selectedLine.canAddTile() || tile.isFirstPlayerMarker()) {
                if (player.getFloorLine().isFull()) {
                    game.getBag().returnTile(tile);
                    tile.getGameObject().removeFromParent();
                } else {
                    PositionAnimation.animate(tile.getGameObject(), () -> player.getFloorLine().addTile(tile), 10);
                }
            } else {
                PositionAnimation.animate(tile.getGameObject(), () -> selectedLine.addTile(tile), 10);
            }
        }
        selectedTiles.clear();

        // Animate tile movement to center
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

        unselectPatternLine();
        unselectTiles();

        finished = true;
    }

    @Override
    public void draw(GameCanvas canvas) {
        super.draw(canvas);

        updateUIPositions(canvas);

//        playerTurnIndicator.draw(canvas);
//        instructions.draw(canvas);
//
//        if (selectedTile != null && selectedLine != null)
//            confirmButton.draw(canvas);
        panel.draw(canvas);
    }

    @Override
    public void update() {
        super.update();

        Vec2 mp = Input.getMousePosition();

        unHighlightAll();
        if (selectedTile != null) selectedTile.highlight();
        if (selectedTiles != null) selectedTiles.forEach(Tile::highlight);
        if (availableLines != null) availableLines.forEach(ILine::highlight);
        if (selectedLine != null) selectedLine.highlight2();

        // Handle hover on tile
        for (AbstractTileSet factory : Stream.concat(
                game.getMiddle().getFactories().stream(),
                Stream.of(game.getMiddle().getCenter())
        ).collect(Collectors.toList())) {
            for (Tile t : factory.getAllTiles()) {
                if (t.isFirstPlayerMarker()) continue;
                if (t.getGameObject().getComponent(ButtonComponent.class).contains(mp)) {
                    if (selectedTiles != null) selectedTiles.forEach(Tile::unHighlight);
                    factory.getTilesOfColor(t.getColor()).forEach(Tile::highlight);
                    player.getPatternLines().getLines().forEach(ILine::unHighlight);
                    player.getAvailableLinesForColor(t.getColor()).forEach(ILine::highlight);
                    return;
                }
            }
        }

        // Handle hover on rows
        if (availableLines != null) {
            for (ILine line : availableLines) {
                if (line.getGameObject().getComponent(ButtonComponent.class).contains(mp)) {
                    if (selectedLine != null) selectedLine.highlight();
                    line.highlight2();
                    return;
                }
            }
        }
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}