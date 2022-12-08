package game.scenes;

import engine.components.ButtonComponent;
import engine.components.PositionAnimationComponent;
import engine.core.*;
import engine.input.MouseEvent;
import game.backend.*;
import game.backend.board.AbstractTileSet;
import game.backend.player.FloorLine;
import game.backend.player.ILine;
import game.backend.player.PatternLine;
import game.backend.player.Player;
import game.frontend.BagCountDisplay;
import game.frontend.TileToolTipObject;
import game.util.PositionAnimation;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FactoryOfferingScene extends BasicGameScene {
    protected final Player player;
    boolean finished = false;

    protected List<Tile> selectedTiles = null;
    protected AbstractTileSet selectedTileSet = null;
    protected Tile.TileColor selectedTileColor = Tile.TileColor.NONE;
    private List<ILine> availableLines = null;
    protected ILine selectedLine = null;
    private final BagCountDisplay bagCountText;

    private TileToolTipObject ttto = null;


    public FactoryOfferingScene(Game game, Player player) {
        super(game);
        this.player = player;

        infoPanel.setChildren(player.getName() + "'s Turn", "", "Next");

        setInstructions("Click on a tile in the factories or the center to select it");
        bagCountText = new BagCountDisplay(game);
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
        infoPanel.set(1, text);
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
            for (ILine line : player.getAvailableLinesForColor(selectedTileColor)) {
                if (line.getGameObject().getComponent(ButtonComponent.class).contains(me.position)) {
                    unselectPatternLine();
                    selectPatternLine(line);
                    return;
                }
            }
        }

        // Handle continue button
        if (selectedTiles != null && selectedLine != null) {
            if (infoPanel.getRight().getComponent(ButtonComponent.class).contains(me.position)) {
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
        selectedTileColor = t.getColor();
        availableLines = player.getAvailableLinesForColor(selectedTileColor);

        setInstructions("Click on the pattern line you will place the tile in");
    }

    private void unselectTiles() {
        selectedTiles = null;
        selectedTileSet = null;
        selectedTileColor = Tile.TileColor.NONE;
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
        if (availableLines != null) availableLines.forEach(ILine::unHighlight);
    }

    private void confirmSelect() {
        if (selectedTiles == null || selectedLine == null) return;

        unHighlightAll();

        // Remove selected tiles from tileset
        selectedTileSet.removeTilesOfColor(selectedTileColor);

        // Animate tile movement to pattern line
        for (Tile tile : selectedTiles) {
            if (selectedLine instanceof FloorLine || selectedLine.canAddTile() || tile.isFirstPlayerMarker()) {
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

        selectedLine = null;
        selectedTiles = null;
        selectedTileSet = null;
        selectedTileColor = Tile.TileColor.NONE;
        availableLines = null;

        finished = true;
    }

    @Override
    public void draw(GameCanvas canvas) {
        if(selectedLine != null && selectedTileColor != null){
            infoPanel.getRight().enable();
        } else infoPanel.getRight().disable();

        super.draw(canvas);
        bagCountText.draw(canvas);
        if(ttto != null) ttto.draw(canvas);
    }

    @Override
    public void update() {
        super.update();

        Vec2 mp = Input.getMousePosition();

        unHighlightAll();
        if (selectedTiles != null) selectedTiles.forEach(Tile::highlight);
        if (availableLines != null) availableLines.forEach(ILine::highlight);
        if (selectedLine != null) selectedLine.highlight2();

        ttto = null;
        // Handle hover on tile
        for (AbstractTileSet factory : Stream.concat(
                game.getMiddle().getFactories().stream(),
                Stream.of(game.getMiddle().getCenter())
        ).collect(Collectors.toList())) {
            for (Tile t : factory.getAllTiles()) {
                if (t.isFirstPlayerMarker()) continue;
                if (t.getGameObject().getComponent(ButtonComponent.class).contains(mp)) {
                    if (selectedTiles != null) selectedTiles.forEach(Tile::unHighlight);
                    List<Tile> tiles = factory.getTilesOfColor(t.getColor());
                    tiles.forEach(Tile::highlight);
                    player.getPatternLines().getLines().forEach(ILine::unHighlight);
                    player.getAvailableLinesForColor(t.getColor()).forEach(ILine::highlight);

                    int cnt = tiles.size();
                    boolean hasFirstPlayer = tiles.stream().anyMatch(Tile::isFirstPlayerMarker);
                    ttto = new TileToolTipObject(t.getColor(), cnt - (hasFirstPlayer ? 1 : 0), hasFirstPlayer);
                    ttto.setBottomLeft(mp);
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

    @Override
    public Iterator<? extends AbstractScene> getScenesAfter() {
        return makeIterator(new WaitScene(game, 40, infoPanel.getArgs()));
    }
}