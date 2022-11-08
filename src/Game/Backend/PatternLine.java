package Game.Backend;

import Engine.Components.RectRendererComponent;
import Engine.Core.GameObject;
import Game.Frontend.PatternLineGameObject;

import java.util.ArrayList;
import java.util.List;

public class PatternLine {
    public static final double TILE_SPACING = 44.3;

    private Tile[] tiles;
    Tile.TileColor currentTileColor = Tile.TileColor.NONE;
    private int numTiles = 0;
    private final GameObject gameObject;

    public PatternLine(int numTiles){
        tiles = new Tile[numTiles];

        gameObject = new PatternLineGameObject(numTiles);
    }

    public void addTile(Tile t){
        if(currentTileColor == Tile.TileColor.NONE && t.isColorTile()){
            currentTileColor = t.getColor();
        }

        tiles[numTiles++] = t;
        gameObject.addChild(t.getGameObject());
    }

    public boolean canPlaceTile(Tile.TileColor t){
        return currentTileColor == Tile.TileColor.NONE || t == Tile.TileColor.FIRST_PLAYER || t == currentTileColor;
    }

    public Tile popFirstTile(){
        Tile res = tiles[0];

        for(int i = 1; i < tiles.length; i++){
            tiles[i - 1] = tiles[i];
        }
        tiles[tiles.length - 1] = null;
        return res;
    }

    public List<Tile> clearTiles(){
        List<Tile> res = new ArrayList<>();
        for(Tile t : tiles) if(t != null) res.add(t);

        tiles = new Tile[tiles.length];

        currentTileColor = Tile.TileColor.NONE;

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

    public void unhighlight(){
        gameObject.getComponent(RectRendererComponent.class).disable();
    }
}
