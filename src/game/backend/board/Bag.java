package game.backend.board;

import game.backend.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bag {
    private final List<Tile> tiles;
    private final List<Tile> returnedTiles;

    public Bag() {
        tiles = new ArrayList<>();
        returnedTiles = new ArrayList<>();

        for (var color : new Tile.TileColor[]{
                Tile.TileColor.RED, Tile.TileColor.ORANGE, Tile.TileColor.BLACK, Tile.TileColor.BLUE, Tile.TileColor.SNOW
        }) {
            for (int i = 0; i < 20; i++) {
                returnedTiles.add(new Tile(color));
            }
        }

        refillBack();
    }

    public Tile popTile() {
        if (tiles.isEmpty()) {
            if (returnedTiles.isEmpty()) {
                // No more Tiles! OH NO!!!!!! WHAT SHALL WE DO????
                // We throw an obnoxious error, obviously
                // At least it isn't a null pointer exception
                // Too lazy to actually implement graceful handling of this situation haha
                throw new Error("Out of Tiles");
            }
            refillBack();
        }

        return tiles.remove(tiles.size() - 1);
    }

    public void returnTile(Tile t) {
        returnedTiles.add(t);
    }

    public void refillBack() {
        tiles.addAll(returnedTiles);
        returnedTiles.clear();
        Collections.shuffle(tiles);
    }

    public int getCount(){
        return tiles.size();
    }
    public int getReturnedCount(){
        return returnedTiles.size();
    }
}
