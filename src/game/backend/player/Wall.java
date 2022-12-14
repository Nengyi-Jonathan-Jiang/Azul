package game.backend.player;

import engine.core.GameObject;
import engine.core.Vec2;
import game.backend.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static game.backend.Tile.TileColor;

public class Wall {
    private static final TileColor[][] referencePattern = {
            {TileColor.BLUE, TileColor.ORANGE, TileColor.RED, TileColor.BLACK, TileColor.SNOW},
            {TileColor.SNOW, TileColor.BLUE, TileColor.ORANGE, TileColor.RED, TileColor.BLACK},
            {TileColor.BLACK, TileColor.SNOW, TileColor.BLUE, TileColor.ORANGE, TileColor.RED},
            {TileColor.RED, TileColor.BLACK, TileColor.SNOW, TileColor.BLUE, TileColor.ORANGE},
            {TileColor.ORANGE, TileColor.RED, TileColor.BLACK, TileColor.SNOW, TileColor.BLUE},
    };

    private final Tile[][] grid;

    private final GameObject gameObject;

    public Wall() {
        grid = new Tile[5][5];

        gameObject = new GameObject(new Vec2(220));
    }


    public boolean hasCompletedRow() {
        for (Tile[] row : grid) {
            int n = 0;
            for (int i = 0; i < 5; i++)
                if (row[i] != null)
                    n++;

            if (n == 5) return true;
        }
        return false;
    }

    public int getCol(int row, TileColor color) {
        int col;
        for (col = 0; col < 5; col++) {
            if (referencePattern[row][col] == color) {
                break;
            }
        }
        if (col == 5) throw new Error("ERROR: could not find tile color in pattern");
        return col;
    }

    public void removeTile(int row, int col){
        grid[row][col].getGameObject().removeFromParent();
        grid[row][col] = null;
    }

    @SuppressWarnings("DuplicatedCode")
    public WallScoreResult placeTile(int row, Tile square) {
        int col = getCol(row, square.getColor());
        if(grid[row][col] != null){
            throw new Error("Attempted to overwrite wall tile");
        }
        grid[row][col] = square;

        gameObject.addChild(square.getGameObject());
        square.getGameObject().setTopLeft(new Vec2(col, row).scaledBy(44.2, 45).plus(gameObject.getTopLeftOffset()));

        List<Tile> tList = new ArrayList<>();

        int rowScore = 0;
        for (int c = col - 1; c >= 0; c--) {
            if (grid[row][c] != null) {
                tList.add(grid[row][c]);
                rowScore++;
            } else break;
        }
        for (int c = col + 1; c < 5; c++) {
            if (grid[row][c] != null) {
                tList.add(grid[row][c]);
                rowScore++;
            } else break;
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

        return new WallScoreResult(tList, scoreAdded);
    }

    public boolean rowHasTileColor(int i, TileColor color) {
        return Stream.of(grid[i]).filter(Objects::nonNull).map(Tile::getColor).anyMatch(c -> c == color);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                res.append(grid[i][j] == null ? "_" : switch (grid[i][j].getColor()) {
                    case RED -> "R";
                    case ORANGE -> "Y";
                    case SNOW -> "W";
                    case BLUE -> "B";
                    case BLACK -> "K";
                    default -> "?";
                });
            }
            res.append("\n");
        }
        return res.toString();
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public Tile[][] getGrid() {
        return grid;
    }
}
