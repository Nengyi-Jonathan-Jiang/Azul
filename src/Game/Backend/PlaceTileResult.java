package Game.Backend;

import Engine.Core.Vec2;

import java.util.List;

public class PlaceTileResult {
    private final List<Tile> tileList;
    private final int scoreAdded;
    private final int row, col;

    public PlaceTileResult(List<Tile> t, int scoreAdded, int row, int col){
        tileList = t;
        this.scoreAdded = scoreAdded;
        this.row = row;
        this.col = col;
    }
    public int getScoreAdded(){
        return scoreAdded;
    }

    public List<Tile> getTiles(){
        return tileList;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public Vec2 getPos(){
        return new Vec2(row, col);
    }
}
