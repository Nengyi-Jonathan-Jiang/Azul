package game.scenes;

import engine.core.AbstractScene;
import engine.core.GameCanvas;
import game.backend.Game;

public class WaitScene extends AbstractScene {
    protected final Game game;
    private final int duration;
    private int animation = 0;

    public WaitScene(Game game, int duration){
        this.game = game;
        this.duration = duration;
    }

    @Override
    public void update() {
        animation++;
    }

    @Override
    public void draw(GameCanvas canvas) {
        game.getGameObject().draw(canvas);
    }

    @Override
    public boolean isFinished() {
        return animation > duration;
    }
}
