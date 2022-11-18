package game.scenes;

import engine.core.AbstractScene;
import game.backend.*;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

class PatternLineScoringScene extends AbstractScene {
    private final Integer row;
    private final Game game;
    private final Player player;
    private final AtomicReference<WallScoreResult> placeTileResult = new AtomicReference<>();
    private final AtomicReference<Tile> placedTile = new AtomicReference<>();

    public PatternLineScoringScene(Game game, Player player, Integer row) {
        this.row = row;
        this.game = game;
        this.player = player;
    }

    @Override
    public Iterator<? extends AbstractScene> getScenesBefore() {
        return makeIterator(
            new ScoringTileMovementScene(row, player, game, placedTile, placeTileResult),
            new ScoreMarkerMovementScene(game, player, placedTile, placeTileResult)
        );
    }
}
