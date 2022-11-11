package game.backend;

import engine.components.RectRendererComponent;
import engine.core.GameObject;
import game.frontend.PatternLineGameObject;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PatternLine {
    public static final double TILE_SPACING = 44.3;

    private Tile[] tiles;
    Tile.TileColor currentTileColor = Tile.TileColor.NONE;
    private int numTiles = 0;
    private final GameObject gameObject;

    public PatternLine(int row){
        tiles = new Tile[row + 1];

        gameObject = new PatternLineGameObject(row);
    }

    public void addTile(Tile t){
        if(currentTileColor == Tile.TileColor.NONE && t.isColorTile()){
            currentTileColor = t.getColor();
        }

        tiles[numTiles++] = t;
        gameObject.addChild(t.getGameObject());
    }

    public boolean canPlaceTile(Tile.TileColor t){
        return  !isFilled() && (
                currentTileColor == Tile.TileColor.NONE ||
                t == Tile.TileColor.FIRST_PLAYER ||
                t == currentTileColor
        );
    }

    public Tile popFirstTile(){
        Tile res = tiles[0];
        tiles[0] = null;
        return res;
    }

    public List<Tile> popAllTiles(){
        List<Tile> res = Arrays.stream(tiles).filter(Objects::nonNull).collect(Collectors.toList());
        tiles = new Tile[tiles.length];
        currentTileColor = Tile.TileColor.NONE;
        numTiles = 0;
        return res;
    }

    public boolean isFilled(){
        return numTiles == tiles.length;
    }

    public GameObject getGameObject(){
        return gameObject;
    }

    public void highlight(){
        gameObject.getComponent(RectRendererComponent.class).enable();
    }

    public void unHighlight(){
        gameObject.getComponent(RectRendererComponent.class).disable();
    }
}
