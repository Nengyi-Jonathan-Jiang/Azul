package Game.Scenes;

import Engine.Core.Scene;
import Game.Backend.Game;

import java.util.Iterator;

// TODO
public class RoundScene extends Scene {
    private Game game;

    public RoundScene(Game game){

    }

    @Override
    public Iterator<? extends Scene> getScenesAfter() {
        return Scene.concatIterators(
            // Distribute tiles
            Scene.makeIterator(new TileDistributionScene(game)),
            // Player turns
            Scene.makeLoopIterator(() -> new PlayerTurnScene(game),
                // TODO: check if factories and middle are empty
                () -> false
            ),
            // Scoring
            Scene.makeLoopIterator(game.getPlayers(), p -> new ScoringScene(game))
        );
    }
}
