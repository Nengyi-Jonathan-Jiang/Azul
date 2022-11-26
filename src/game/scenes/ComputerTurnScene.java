package game.scenes;

import engine.core.AbstractScene;
import engine.core.GameCanvas;
import engine.core.Vec2;
import game.App;
import game.backend.Game;
import game.backend.player.Player;
import game.util.PositionAnimation;

import java.util.Iterator;

public class ComputerTurnScene extends AbstractScene {
    public final Game game;
    public final Player player;
    private int animation = 0;


    public ComputerTurnScene(Game game, Player player) {
        this.game = game;
        this.player = player;
    }

    @Override
    public void onExecutionStart() {
        PositionAnimation.animate(game.getGameObject(), () -> game.getGameObject().setPosition(
                player.getGameObject().getPosition()
                        .scaledBy(-.5, -.5)
                        .plus(new Vec2(App.WIDTH, App.HEIGHT).scaledBy(.5))
        ), 10);
    }

    @Override
    public void update() {
        animation++;
    }

    @Override
    public void draw(GameCanvas canvas) {
        game.draw(canvas);
    }

    @Override
    public boolean isFinished() {
        return animation > 10;
    }

    @Override
    public Iterator<AbstractScene> getScenesAfter() {
        return makeIterator(new ComputerFactoryOfferingScene(game, player));
    }
}