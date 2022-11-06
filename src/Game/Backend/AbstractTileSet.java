package Game.Backend;

import Engine.Core.GameObject;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public abstract class AbstractTileSet {
    protected final Map<Tile.TileColor, List<Tile>> tiles;
    protected final GameObject gameObject;

    // The child classes must create
    public AbstractTileSet(){
        tiles = new TreeMap<>();
        gameObject = createGameObject();
    }

    abstract protected GameObject createGameObject();

    public void addTiles(List<Tile> lst){
        for(Tile t : lst){
            tiles.get(t.getColor()).add(t);
            gameObject.addChild(t.getGameObject());
        }
    }

    public List<Tile> getTilesOfColor(Tile.TileColor color){
        return tiles.get(color);
    }

    public List<Tile> removeTilesOfColor(Tile.TileColor color){
        return tiles.remove(color);
    }

    public List<Tile> getAllTiles(){
        return tiles.values().stream().flatMap(List::stream).collect(Collectors.toList());
    }

    public List<Tile> removeAllTiles(){
        List<Tile> res = getAllTiles();
        tiles.clear();
        return res;
    }
}
