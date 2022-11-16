package game.scenes;

import engine.core.AbstractScene;
import engine.core.GameCanvas;
import engine.core.Vec2;
import game.backend.Game;
import game.backend.Player;
import game.App;
import game.util.PositionAnimation;

import java.util.Iterator;

// TODO
public class PlayerTurnScene extends AbstractScene {
    public final Game game;
    public final Player player;

    public PlayerTurnScene(Game game, Player player) {
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

    private int animation = 0;

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
        return PanningGameScene.makeIterator(new FactoryOfferingScene(game, player)
//                , new PatternLineScene(game, player)
        );
    }
}
