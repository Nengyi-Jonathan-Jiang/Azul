package Game.Backend;

import java.util.List;
import java.util.ArrayList;

public class PlaceTileResult {
    private final List<Tile> tileList;
    private final int scoreAdded;

    public PlaceTileResult(List<Tile> t, int s){
        tileList = t;
        scoreAdded = s;
    }
    public int getScoreAdded(){
        return scoreAdded;
    }

    public List<Tile> getTiles(){
        return tileList;
    }
}
