package Game.Backend;

import Engine.Core.GameObject;
import Engine.Core.Vec2;
import Game.Frontend.TilePileGameObject;

import java.util.*;
import java.util.stream.Collectors;

public class Center extends AbstractTileSet {
    private Tile firstPlayerTile;

    public Center(){
        super();

        addFirstPlayerTile(new Tile(Tile.TileColor.FIRST_PLAYER));
    }

    @Override
    protected GameObject createGameObject() {
        return new TilePileGameObject();
    }

    public boolean hasFirstPlayerTile(){
        return firstPlayerTile != null;
    }

    @Override
    public void addTile(Tile t) {
        if(t.getColor() == Tile.TileColor.FIRST_PLAYER)
            addFirstPlayerTile(t);
        else
            super.addTile(t);
    }

    @Override
    public List<Tile> getTilesOfColor(Tile.TileColor color) {
        List<Tile> res = new ArrayList<>(super.getTilesOfColor(color));
        if(firstPlayerTile != null) res.add(firstPlayerTile);

        System.out.println("Called getTilesOfColor: returned " + res + " (hasFirstPlayerTile=" + hasFirstPlayerTile() + ")");
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

    public void addFirstPlayerTile(Tile t){
        if(t == null || t.getColor() != Tile.TileColor.FIRST_PLAYER){
            throw new Error("ERROR: passed non first player tile to addFirstPlayerTile");
        }

        firstPlayerTile = t;
        gameObject.addChild(t.getGameObject());
    }
}