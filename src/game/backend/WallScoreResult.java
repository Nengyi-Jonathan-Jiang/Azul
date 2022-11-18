package game.backend;

import java.util.List;

public class WallScoreResult {
    private final List<Tile> tileList;
    private final int scoreAdded;

    public WallScoreResult(List<Tile> t, int scoreAdded) {
        tileList = t;
        this.scoreAdded = scoreAdded;
    }

    public int getScoreAdded() {
        return scoreAdded;
    }

    public List<Tile> getTiles() {
        return tileList;
    }
}
