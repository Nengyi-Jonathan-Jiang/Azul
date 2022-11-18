package game.scenes;

import engine.core.AbstractScene;
import game.backend.Game;
import game.backend.WallScoreResult;
import game.backend.Player;
import game.backend.Tile;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

class ScoreMarkerMovementScene extends AbstractScene {
    private final Game game;
    private final Player player;
    private final AtomicReference<Tile> placedTile;
    private final AtomicReference<WallScoreResult> result;

    ScoreMarkerMovementScene(Game game, Player player, AtomicReference<Tile> placedTile, AtomicReference<WallScoreResult> result) {
        this.game = game;
        this.player = player;
        this.placedTile = placedTile;
        this.result = result;
    }

    @Override
    public void onExecutionStart() {
        int score = player.getScoreMarker().getScore();
        int newScore = score + result.get().getScoreAdded();

        player.getScoreMarker().setScore(newScore);

        result.get().getTiles().forEach(Tile::highlight);

        if(placedTile != null) placedTile.get().highlight();
    }

    @Override
    public Iterator<? extends AbstractScene> getScenesAfter() {
        return makeIterator(new WaitScene(game, 50), new UnhighlightWallScene(player));
    }
}