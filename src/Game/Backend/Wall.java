package Game.Backend;

import java.util.List;

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
    private int score;
    private int finalScore = 0;

    public Wall(){
        grid = new Tile[5][5];
        score = 0;
        finalScore = 0;
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

    public int getScore(){

        if(!hasCompletedRow()){
            for(int i=0; i<grid.length; i++){
                for(int k=0; k<grid[i].length; k++){
                    if(grid[i][k] != null){
                        score++;
                        if(grid[i+1][k] != null){
                            score++;
                        }
                        if(grid[i-1][k] != null){
                            score++;
                        }

                        score++;
                        if(grid[i][k+1] != null){
                            score++;
                        }
                        if(grid[i][k-1]!=null){
                            score++;
                        }
                    }
                }
            }
        }
        return score;
    }



    public boolean rowHasTileColor(int i, TileColor color){
        return List.of(grid[i]).stream().map(Tile::getColor).anyMatch(c -> c == color);
    }
}
