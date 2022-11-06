package Game.Backend;
import java.util.*;

public class Bag{
    private List<Tile> tiles;
    private List<Tile> returnedTiles;

    public Bag(){
        tiles = new ArrayList<Tile>();
        returnedTiles = new ArrayList<Tile>();

        for(var color : new Tile.TileColor[]{
            Tile.TileColor.RED, Tile.TileColor.YELLOW, Tile.TileColor.BLACK, Tile.TileColor.BLUE, Tile.TileColor.WHITE
        }){
            for(int i = 0; i < 20; i++) {
                returnedTiles.add(new Tile(color));
            }
        }

        refillBack();
    }

    public Tile popTile(){
        if(tiles.isEmpty()){
            if(returnedTiles.isEmpty()){
                // No more Tiles! OH NO!!!!!! WHAT SHALL WE DO????
                // We throw an obnoxious error, obviously
                // At least its not a null pointer exception
                // Too lazy to actually implement graceful handling of this situation haha
                throw new Error("Out of Tiles");
            }
            refillBack();
        }
        return tiles.remove(tiles.size() - 1);
    }

    public void returnTile(Tile t){
        returnedTiles.add(t);
    }
  
    public void refillBack(){
        tiles.addAll(returnedTiles);
        Collections.shuffle(tiles);
    }
}