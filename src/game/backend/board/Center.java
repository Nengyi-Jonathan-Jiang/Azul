package game.backend.board;

import engine.core.GameObject;
import game.backend.Tile;
import game.frontend.TilePileGameObject;

import java.util.ArrayList;
import java.util.List;

public class Center extends AbstractTileSet {
    private Tile firstPlayerTile;

    public Center() {
        super();
    }

    @Override
    protected GameObject createGameObject() {
        return new TilePileGameObject();
    }

    @Override
    public void addTile(Tile t) {
        if (t.getColor() == Tile.TileColor.FIRST_PLAYER)
            addFirstPlayerTile(t);
        else
            super.addTile(t);
    }

    @Override
    public List<Tile> getTilesOfColor(Tile.TileColor color) {
        List<Tile> res = new ArrayList<>(super.getTilesOfColor(color));
        if (firstPlayerTile != null) res.add(0, firstPlayerTile);
        return res;
    }

    @Override
    public List<Tile> removeTilesOfColor(Tile.TileColor color) {
        List<Tile> res = new ArrayList<>(super.removeTilesOfColor(color));
        if (firstPlayerTile != null) {
            res.add(firstPlayerTile);
            firstPlayerTile = null;
        }
        return res;
    }

    @Override
    public List<Tile> getAllTiles() {
        List<Tile> res = new ArrayList<>(super.getAllTiles());
        if (firstPlayerTile != null) res.add(firstPlayerTile);
        return res;
    }

    @Override
    public List<Tile> removeAllTiles() {
        List<Tile> res = new ArrayList<>(super.removeAllTiles());
        if (firstPlayerTile != null) {
            res.add(firstPlayerTile);
            firstPlayerTile = null;
        }
        return res;
    }

    public void addFirstPlayerTile(Tile t) {
        if (t == null || t.getColor() != Tile.TileColor.FIRST_PLAYER) {
            throw new Error("ERROR: passed non first player tile to addFirstPlayerTile");
        }

        firstPlayerTile = t;
        gameObject.addChild(t.getGameObject());
    }
}