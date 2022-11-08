package Game.Backend;

import Engine.Core.GameObject;

import java.util.ArrayList;
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

    public void addTile(Tile t){
        if(!tiles.containsKey(t.getColor())){
            tiles.put(t.getColor(), new ArrayList<>());
        }
        tiles.get(t.getColor()).add(t);
        gameObject.addChild(t.getGameObject());
    }

    public void addTiles(List<Tile> lst){
        for(Tile t : lst) addTile(t);
    }

    public List<Tile> getTilesOfColor(Tile.TileColor color){
        return tiles.getOrDefault(color, new ArrayList<>());
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

    public GameObject getGameObject(){
        return gameObject;
    }
}
