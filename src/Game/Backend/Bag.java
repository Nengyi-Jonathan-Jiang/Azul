package Game.Backend;
import java.lang.Math.*;
public class Bag{
  private List<Tile> tiles;
  private List<Tile> returnedTiles;
  
  public Bag(){
    tiles = new ArrayList<Tile>();
    returnedTiles = new ArrayList<Tile>();
  }
  
  public Tile getTile(){
    Tile t = tiles.remove(Math.random() * tiles.size());
    return t;
  }
  public void returnTile(Tile t){
    returnedTiles.add(t); 
  }
  
  public void refillBack(){
    tiles.addAll(returnedTiles); 
  }
  
  
  
  
  
}
