package Game.Backend;

import static Game.Backend.Tile.TileColor;

public class Wall {
    private static final TileColor[][] referencePattern = {
        {TileColor.BLUE, TileColor.YELLOW, TileColor.RED, TileColor.BLACK, TileColor.WHITE},
        {TileColor.WHITE, TileColor.BLUE, TileColor.YELLOW, TileColor.RED, TileColor.BLACK},
        {TileColor.BLACK, TileColor.WHITE, TileColor.BLUE, TileColor.YELLOW, TileColor.RED},
        {TileColor.RED, TileColor.BLACK, TileColor.WHITE, TileColor.BLUE, TileColor.YELLOW},
        {TileColor.YELLOW, TileColor.RED, TileColor.BLACK, TileColor.WHITE, TileColor.BLUE},
    };

    private final Tile[][] grid;

    public Wall(){
        grid = new Tile[5][5];

    }

    public boolean hasCompletedRow(){
        for(int i = 0; i < 5; i++){
            int n = 0;
            for(int j = 0; j < 5; j++)
                if(grid[n] != null)
                    n++;

            if(n == 5) return true;
        }
        return false;
    }
}
