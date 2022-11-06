package Game.Scenes;

import Engine.Core.AbstractScene;
import Game.Backend.Game;

import java.util.Iterator;

public class RoundScene extends AbstractScene {
    private final Game game;

    public RoundScene(Game game){
        this.game = game;
    }

    @Override
    public Iterator<? extends AbstractScene> getScenesAfter() {
        return AbstractScene.concatIterators(
            // Distribute tiles
            AbstractScene.makeIterator(new TileDistributionScene(game)),
            // Player turns
            AbstractScene.makeLoopIterator(() -> new PlayerTurnScene(game),
                // TODO: check if factories and middle are empty
                () -> false
            ),
            // Scoring
            AbstractScene.makeLoopIterator(game.getPlayers(), p -> new ScoringScene(game))
        );
    }
}
