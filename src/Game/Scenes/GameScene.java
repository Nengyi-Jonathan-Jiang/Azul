package Game.Scenes;

import Engine.Core.GameCanvas;
import Engine.Core.Input;
import Engine.Core.Scene;
import Engine.Core.Vec2;
import Game.Backend.Game;
import Game.Backend.Player;

import java.awt.event.MouseEvent;
import java.util.Iterator;

public class GameScene extends Scene {
    private Game game;

    public GameScene(Game game){
        this.game = game;
    }

    @Override
    public Iterator<? extends Scene> getScenesAfter() {
        return Scene.concatIterators(
            Scene.makeLoopIterator(() -> new RoundScene(game), () -> game.getPlayers().stream().anyMatch(p -> p.getWall().hasCompletedRow())),
            Scene.makeLoopIterator(game.getPlayers(), player -> new BonusCalculationScene(player, game)),
            Scene.makeIterator(new RankingScene(game))
        );
    }
}
