package game.backend.player;

import engine.components.RectRendererComponent;
import engine.core.GameObject;
import game.Style;
import game.backend.Tile;
import game.frontend.PatternLineGameObject;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class PatternLine implements ILine {
    public static final double TILE_SPACING = 44.3;
    private final GameObject gameObject;
    Tile.TileColor currentTileColor = Tile.TileColor.NONE;
    private Tile[] tiles;
    private int numTiles = 0;

    public PatternLine(int row) {
        tiles = new Tile[row + 1];

        gameObject = new PatternLineGameObject(row);
    }

    public void addTile(Tile t) {
        if (currentTileColor == Tile.TileColor.NONE && t.isColorTile()) {
            currentTileColor = t.getColor();
        }

        tiles[numTiles++] = t;
        gameObject.addChild(t.getGameObject());
    }

    public boolean canAddTileOfColor(Tile.TileColor t) {
        return !canAddTile() && (
                currentTileColor == Tile.TileColor.NONE ||
                        t == Tile.TileColor.FIRST ||
                        t == currentTileColor
        );
    }

    public Tile popFirstTile() {
        Tile res = tiles[0];
        tiles[0] = null;
        return res;
    }

    public List<Tile> popAllTiles() {
        List<Tile> res = Arrays.stream(tiles).filter(Objects::nonNull).collect(Collectors.toList());
        tiles = new Tile[tiles.length];
        currentTileColor = Tile.TileColor.NONE;
        numTiles = 0;
        return res;
    }

    public boolean canAddTile() {
        return numTiles >= tiles.length;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void highlight() {
        gameObject.getComponent(RectRendererComponent.class).setBorderColor(Style.HL_COLOR);
        gameObject.getComponent(RectRendererComponent.class).enable();
    }

    public void highlight2() {
        gameObject.getComponent(RectRendererComponent.class).setBorderColor(Style.HL2_COLOR);
        gameObject.getComponent(RectRendererComponent.class).enable();
    }

    public void unHighlight() {
        gameObject.getComponent(RectRendererComponent.class).disable();
    }

    public int getNumTiles(){
        return numTiles;
    }

    public int getCapacity(){
        return tiles.length;
    }

    public Tile.TileColor getCurrentTileColor() {
        return currentTileColor;
    }

    public <T> T runWithNumTiles(int num, Tile.TileColor color, Supplier<T> callback){
        int n = numTiles;
        numTiles = num;

        Tile.TileColor c = currentTileColor;
        currentTileColor = color;

        T res = callback.get();

        numTiles = n;
        currentTileColor = c;
        return res;
    }
}
