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


}
