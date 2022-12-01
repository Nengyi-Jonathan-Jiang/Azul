package game.scenes;

import engine.core.AbstractScene;
import engine.core.InstantaneousScene;
import engine.core.Vec2;
import game.App;
import game.backend.Game;
import game.backend.player.Player;
import game.util.PositionAnimation;

import java.util.Iterator;

public class ComputerTurnScene extends InstantaneousScene {
    public final Game game;
    public final Player player;


    public ComputerTurnScene(Game game, Player player) {
        this.game = game;
        this.player = player;
    }

    @Override
    public void execute() {
        PositionAnimation.animate(game.getGameObject(), () -> game.getGameObject().setPosition(
                player.getGameObject().getPosition()
                        .scaledBy(-.5, -.5)
                        .plus(new Vec2(App.WIDTH, App.HEIGHT).scaledBy(.5))
        ), 10);
    }

    @Override
    public Iterator<AbstractScene> getScenesAfter() {
        return makeIterator(
                new WaitScene(game, 40, player + "'s turn", null),
                new ComputerFactoryOfferingScene(game, player),
                new WaitScene(game, 30, player + "'s turn", null)
        );
    }
}