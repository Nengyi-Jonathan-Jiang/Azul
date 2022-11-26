package game.backend.ai.styles;

import game.backend.*;
import game.backend.ai.*;
import game.backend.board.*;
import game.backend.player.*;

import java.util.List;

public class GreedyComputer extends PlayStyle {
    @Override
    public ComputerMove getMove(Player player, Game game) {
        AbstractTileSet selectedTileSet = null;
        ILine selectedRow = null;
        List<Tile> selectedTiles = null;
        Tile.TileColor color = Tile.TileColor.NONE;
        double score = Double.NEGATIVE_INFINITY;

        for(AbstractTileSet tileSet : game.getMiddle().getAllTileSets()){
            for(Tile.TileColor c : tileSet.getColors()){
                List<Tile> tiles = tileSet.getTilesOfColor(c);
                if(tiles.isEmpty()) continue;

                for(ILine line: player.getAvailableLinesForColor(c)){
                    double newScore = player.whatIfScore(line, tiles.size(), c);
                    if(newScore > score){
                        selectedTiles = tiles;
                        selectedTileSet = tileSet;
                        selectedRow = line;
                        color = c;

                        score = newScore;
                    }
                }
            }
        }

        return new ComputerMove(game, player, selectedTileSet, selectedTiles, selectedRow, color);
    }
}