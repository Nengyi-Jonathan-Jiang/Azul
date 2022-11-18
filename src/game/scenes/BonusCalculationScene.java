package game.scenes;

import engine.core.AbstractScene;
import game.backend.Game;
import game.backend.Player;
import game.backend.Tile;
import game.backend.WallScoreResult;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BonusCalculationScene extends PanningGameScene {
    private final Player player;
    public BonusCalculationScene(Player player, Game game){
        super(game);
        this.player = player;

    }

    public List<WallScoreResult> getRowBonuses(){
        List<WallScoreResult> res = new ArrayList<>();

        Tile[][] pBoard = player.getWall().getGrid();
        for (Tile[] value : pBoard) {
            List<Tile> tiles = Arrays.stream(value).filter(Objects::nonNull).collect(Collectors.toList());
            if (tiles.size() == 5) {
                res.add(new WallScoreResult(tiles, 2));
            }
        }

        return res;
    }

    public List<WallScoreResult> getColBonuses(){
        List<WallScoreResult> res = new ArrayList<>();

        Tile[][] pBoard = player.getWall().getGrid();
        for(int i = 0; i< pBoard.length; i++) {
            int c = i;

            List<Tile> tiles = Arrays.stream(pBoard).map(row -> row[c]).filter(Objects::nonNull).collect(Collectors.toList());
            if(tiles.size() == 5){
                res.add(new WallScoreResult(tiles, 7));
            }
        }

        return res;
    }

    public List<WallScoreResult> getColorBonuses(){
        List<WallScoreResult> res = new ArrayList<>();

        Tile[][] pBoard = player.getWall().getGrid();
        for(Tile.TileColor color : Tile.allColors) {
            List<Tile> tiles = Arrays.stream(pBoard).flatMap(Arrays::stream).filter(t -> t != null && t.getColor() == color).collect(Collectors.toList());
            if(tiles.size() == 5){
                res.add(new WallScoreResult(tiles, 10));
            }
        }

        return res;
    }

    @Override
    public Iterator<? extends AbstractScene> getScenesAfter() {
        return concatIterators(
                Stream.of(getRowBonuses(), getColBonuses(), getColorBonuses())
                        .flatMap(List::stream)
                        .map(result -> new ScoreMarkerMovementScene(game, player, null, new AtomicReference<>(result)))
                        .collect(Collectors.toList())
                        .iterator(),
                makeIterator(
                    new ScoringConfirmationScene(game)
                )
        );
    }
}
