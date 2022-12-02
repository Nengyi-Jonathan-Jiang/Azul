package game.scenes;

import engine.core.AbstractScene;
import game.backend.Game;

import java.util.Iterator;

public class GameScene extends AbstractScene {
    private final Game game;

    public GameScene(Game game) {
        this.game = game;
    }

    @Override
    public Iterator<? extends AbstractScene> getScenesAfter() {
        return concatIterators(
                makeLoopIterator(
                        () -> new RoundScene(game),
                        () -> game.getPlayers().stream().noneMatch(p -> p.getWall().hasCompletedRow())
                ),
                makeLoopIterator(game.getPlayers(), player -> new BonusCalculationScene(player, game)),
                makeIterator(new RankingScene(game))
        );
    }
}