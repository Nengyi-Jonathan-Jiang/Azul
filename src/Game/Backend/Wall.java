package Game.Backend;

import Engine.Components.RectRendererComponent;
import Engine.Core.GameObject;
import Engine.Core.Vec2;

import Game.Style;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private GameObject gameObject;

    public Wall() {
        grid = new Tile[5][5];
        score = 0;

        gameObject = new GameObject(new Vec2(220));
    }


    public boolean hasCompletedRow() {
        for (int i = 0; i < 5; i++) {
            int n = 0;
            for (int j = 0; j < 5; j++)
                if (grid[n] != null)
                    n++;

            if (n == 5) return true;
        }
        return false;
    }

    public int getScore() {

        if (!hasCompletedRow()) {
            for (int i = 0; i < grid.length; i++) {
                for (int k = 0; k < grid[i].length; k++) {
                    if (grid[i][k] != null) {
                        score++;
                        if (grid[i + 1][k] != null) {
                            score++;
                        }
                        if (grid[i - 1][k] != null) {
                            score++;
                        }

                        score++;
                        if (grid[i][k + 1] != null) {
                            score++;
                        }
                        if (grid[i][k - 1] != null) {
                            score++;
                        }
                    }
                }
            }
        }
        return score;
    }

    public int getCol(int row, TileColor color){
        int col;
        for (col = 0; col < 5; col++) {
            if (referencePattern[row][col] == color) {
                break;
            }
        }
        if(col == 5) throw new Error("ERROR: could not find tile color in pattern");
        return col;
    }

    public PlaceTileResult placeTile(int row, Tile square) {
        int col = getCol(row, square.getColor());
        grid[row][col] = square;
        gameObject.addChild(square.getGameObject());
        square.getGameObject().setTopLeft(new Vec2(col, row).scaledBy(44.2, 45).plus(gameObject.getTopLeftOffset()));

        List<Tile> tList = new ArrayList<>();

        int rowScore = 0;
        for(int c = col - 1; c >= 0; c--){
            if (grid[row][c] != null) {
                tList.add(grid[row][c]);
                rowScore++;
            }
            else break;
        }
        for(int c = col + 1; c < 5; c++){
            if (grid[row][c] != null) {
                tList.add(grid[row][c]);
                rowScore++;
            }
            else break;
        }

        int colScore = 0;
        for (int r = row - 1; r >= 0; r--) {
            if (grid[r][col] != null) {
                tList.add(grid[r][col]);
                colScore++;
            } else break;
        }
        for (int r = row + 1; r < 5; r++) {
            if (grid[r][col] != null) {
                tList.add(grid[r][col]);
                colScore++;
            } else break;
        }

        int scoreAdded = colScore + rowScore + (colScore > 0 && rowScore > 0 ? 2 : 1);

        return new PlaceTileResult(tList, scoreAdded, row, col);
    }

    public boolean rowHasTileColor(int i, TileColor color) {
        return Stream.of(grid[i]).filter(Objects::nonNull).map(Tile::getColor).anyMatch(c -> c == color);
    }

    public GameObject getGameObject() {
        return gameObject;
    }
}
