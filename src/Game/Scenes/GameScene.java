package Game.Scenes;

import Engine.Core.AbstractScene;
import Game.Backend.Game;
import java.util.Iterator;

public class GameScene extends AbstractScene {
    private Game game;

    public GameScene(Game game){
        this.game = game;
    }

    @Override
    public Iterator<? extends AbstractScene> getScenesAfter() {
        return AbstractScene.concatIterators(
            AbstractScene.makeLoopIterator(() -> new RoundScene(game), () -> game.getPlayers().stream().anyMatch(p -> p.getWall().hasCompletedRow())),
            AbstractScene.makeLoopIterator(game.getPlayers(), player -> new BonusCalculationScene(player, game)),
            AbstractScene.makeIterator(new RankingScene(game))
        );
    }
}
