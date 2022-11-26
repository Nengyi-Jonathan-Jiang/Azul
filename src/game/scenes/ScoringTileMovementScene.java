package game.scenes;

import engine.core.AbstractScene;
import engine.core.GameObject;
import game.backend.*;
import game.backend.player.PatternLine;
import game.backend.player.Player;
import game.backend.player.WallScoreResult;
import game.util.PositionAnimation;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

class ScoringTileMovementScene extends AbstractScene {
    private final int row;
    private final Player player;
    private final Game game;
    private final AtomicReference<Tile> placedTile;
    private final AtomicReference<WallScoreResult> placeTileResult;

    ScoringTileMovementScene(int row, Player player, Game game, AtomicReference<Tile> placedTile, AtomicReference<WallScoreResult> placeTileResult) {
        this.row = row;
        this.player = player;
        this.game = game;
        this.placedTile = placedTile;
        this.placeTileResult = placeTileResult;
    }

    @Override
    public void onExecutionStart() {
        List<PatternLine> patternLines = player.getPatternLines().getLines();

        PatternLine line = patternLines.get(row);
        placedTile.set(line.popFirstTile());
        List<Tile> rest = line.popAllTiles();
        rest.stream().map(Tile::getGameObject).forEach(GameObject::removeFromParent);
        rest.forEach(game.getBag()::returnTile);
        PositionAnimation.animate(
                placedTile.get().getGameObject(),
                () -> placeTileResult.set(player.getWall().placeTile(row, placedTile.get())),
                10
        );
    }

    @Override
    public Iterator<? extends AbstractScene> getScenesAfter() {
        return makeIterator(new WaitScene(game, 10));
    }
}
