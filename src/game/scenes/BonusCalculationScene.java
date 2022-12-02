package game.scenes;

import engine.components.PositionAnimationComponent;
import engine.core.AbstractScene;
import engine.core.InstantaneousScene;
import engine.core.Vec2;
import game.App;
import game.backend.Game;
import game.backend.player.Player;
import game.backend.Tile;
import game.backend.player.WallScoreResult;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BonusCalculationScene extends InstantaneousScene {
    private final Game game;
    private final Player player;

    public BonusCalculationScene(Player player, Game game) {
        this.game = game;
        this.player = player;
    }

    @Override
    public void execute() {
        game.getGameObject().getComponent(PositionAnimationComponent.class).moveTo(
                player.getGameObject().getPosition().scaledBy(-1),
                10
        );
    }

    public List<WallScoreResult> getRowBonuses() {
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

    public List<WallScoreResult> getColBonuses() {
        List<WallScoreResult> res = new ArrayList<>();

        Tile[][] pBoard = player.getWall().getGrid();
        for (int i = 0; i < pBoard.length; i++) {
            int c = i;

            List<Tile> tiles = Arrays.stream(pBoard).map(row -> row[c]).filter(Objects::nonNull).collect(Collectors.toList());
            if (tiles.size() == 5) {
                res.add(new WallScoreResult(tiles, 7));
            }
        }

        return res;
    }

    public List<WallScoreResult> getColorBonuses() {
        List<WallScoreResult> res = new ArrayList<>();

        Tile[][] pBoard = player.getWall().getGrid();
        for (Tile.TileColor color : Tile.allColors) {
            List<Tile> tiles = Arrays.stream(pBoard).flatMap(Arrays::stream).filter(t -> t != null && t.getColor() == color).collect(Collectors.toList());
            if (tiles.size() == 5) {
                res.add(new WallScoreResult(tiles, 10));
            }
        }

        return res;
    }

    @Override
    public Iterator<? extends AbstractScene> getScenesAfter() {
        return concatIterators(
            makeIterator(new WaitScene(game, 10, "Calculating bonuses for " + player, null)),
            Stream.of(getRowBonuses(), getColBonuses(), getColorBonuses())
                .flatMap(List::stream)
                .map(result ->
                    new ScoreMarkerMovementScene(game, player, null, new AtomicReference<>(result)){
                        @Override
                        public Iterator<? extends AbstractScene> getScenesAfter() { return makeIterator(
                            new WaitScene(game, 50, "Calculating bonuses for " + player, null),
                            new UnhighlightWallScene(player)
                        );}
                    }
                )
                .collect(Collectors.toList())
                .iterator(),
            makeIterator(new ScoringConfirmationScene(game, player){
                @Override
                public void onExecutionStart() {
                    super.onExecutionStart();
                    infoPanel.setChildren("Calculating bonuses for " + player, "Continue");
                }
            })
        );
    }
}
