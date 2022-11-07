package Game.Backend;

import Engine.Core.GameObject;

import java.util.*;
import java.util.stream.Collectors;

public class Center extends AbstractTileSet{
    private Tile firstPlayerTile;
    protected final GameObject gameObject;

    public Center(){
        super();
        firstPlayerTile =  new Tile(Tile.TileColor.FIRST_PLAYER);
        gameObject = createGameObject();
    }

    @Override
    protected GameObject createGameObject() {
        return null;
    }

    public boolean hasFirstPlayerTile(){
        return firstPlayerTile != null;
    }

    @Override
    public List<Tile> getTilesOfColor(Tile.TileColor color) {
        List<Tile> res = new ArrayList<>(super.getTilesOfColor(color));
        if(firstPlayerTile != null) res.add(firstPlayerTile);
        return res;
    }

    @Override
    public List<Tile> removeTilesOfColor(Tile.TileColor color) {
        List<Tile> res = new ArrayList<>(super.removeTilesOfColor(color));
        if(firstPlayerTile != null){
            res.add(firstPlayerTile);
            firstPlayerTile = null;
        }
        return res;
    }

    @Override
    public List<Tile> getAllTiles() {
        List<Tile> res = new ArrayList<>(super.getAllTiles());
        if(firstPlayerTile != null) res.add(firstPlayerTile);
        return res;
    }

    @Override
    public List<Tile> removeAllTiles() {
        List<Tile> res = new ArrayList<>(super.removeAllTiles());
        if(firstPlayerTile != null){
            res.add(firstPlayerTile);
            firstPlayerTile = null;
        }
        return res;
    }
}






















