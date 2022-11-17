package game.scenes;

import engine.core.AbstractScene;
import game.backend.*;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

class PatternLineScoringScene extends AbstractScene {
    private final List<PatternLine> patternLines;
    private final Integer row;
    private final Game game;
    private final Player player;
    AtomicReference<WallScoreResult> placeTileResult;
    AtomicReference<Tile> placedTile = new AtomicReference<>();

    public PatternLineScoringScene(Game game, Player player, Wall wall, Integer row, List<PatternLine> patternLines) {
        this.patternLines = patternLines;
        this.row = row;
        this.game = game;
        this.player = player;
    }

    @Override
    public Iterator<? extends AbstractScene> getScenesBefore() {
        return makeIterator(
            new ScoringTileMovementScene(row, player, game, placedTile, placeTileResult),
            new WaitScene(game, 10),
            new ScoreMarkerMovementScene(player, placedTile.get(), placeTileResult.get()),
            new WaitScene(game, 40)
        );
    }

    @Override
    public void onExecutionEnd() {  // Unhighlight anything that was highlighted
        placeTileResult.get().getTiles().forEach(Tile::unHighlight);
        placedTile.get().unHighlight();
    }
}
