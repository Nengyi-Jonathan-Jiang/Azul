package game.scenes;

import engine.core.AbstractScene;
import engine.core.InstantaneousScene;
import engine.core.Vec2;
import game.App;
import game.backend.Game;
import game.backend.player.Player;
import game.util.PositionAnimation;

import java.util.Iterator;

public class PlayerTurnScene extends InstantaneousScene {
    protected final Player player;
    protected final Game game;


    public PlayerTurnScene(Game game, Player player) {
        this.game = game;
        this.player = player;
    }

    @Override
    public void execute() {
        PositionAnimation.animate(game.getGameObject(), () -> game.getGameObject().setPosition(
                player.getGameObject().getPosition()
                        .scaledBy(-.5, -.5)
        ), 10);
    }

    @Override
    public Iterator<AbstractScene> getScenesAfter() {
        return makeIterator(new FactoryOfferingScene(game, player));
    }
}
