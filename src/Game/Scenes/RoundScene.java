package Game.Scenes;

import Engine.Core.AbstractScene;
import Game.Backend.Game;

import java.util.Iterator;
import java.util.function.Supplier;

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
            AbstractScene.makeLoopIterator(
                new Supplier<>() {
                    int player = 0;
                    @Override
                    public AbstractScene get() {
                        return new PlayerTurnScene(game, game.getPlayers().get(player++ % game.getPlayers().size()));
                    }
                },
                // TODO: check if center is empty too
                () -> !game.getMiddle().getFactories().isEmpty()
            ),
            // Scoring
            AbstractScene.makeLoopIterator(game.getPlayers(), p -> new ScoringScene(game))
        );
    }
}
