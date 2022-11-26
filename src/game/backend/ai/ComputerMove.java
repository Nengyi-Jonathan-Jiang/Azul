package game.backend.ai;

import game.backend.Game;
import game.backend.Tile;
import game.backend.board.AbstractTileSet;
import game.backend.player.ILine;
import game.backend.player.Player;

import java.util.List;

public class ComputerMove {
    public final Game game;
    public final Player player;
    public final AbstractTileSet selectedTileSet;
    public final List<Tile> selectedTiles;
    public final ILine selectedLine;
    public final Tile.TileColor color;

    public ComputerMove(Game game, Player player, AbstractTileSet selectedTileSet, List<Tile> selectedTiles, ILine selectedLine, Tile.TileColor color) {
        this.game = game;
        this.player = player;
        this.selectedTileSet = selectedTileSet;
        this.selectedTiles = selectedTiles;
        this.selectedLine = selectedLine;
        this.color = color;
    }
}
