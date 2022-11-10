package game.backend;

import java.util.List;

public class PlaceTileResult {
    private final List<Tile> tileList;
    private final int scoreAdded;

    public PlaceTileResult(List<Tile> t, int scoreAdded){
        tileList = t;
        this.scoreAdded = scoreAdded;
    }
    public int getScoreAdded(){
        return scoreAdded;
    }

    public List<Tile> getTiles(){
        return tileList;
    }
}
