package game.scenes;

import game.backend.Game;

public class WaitScene extends BasicGameScene {
    protected final Game game;
    private final int duration;
    private int animation = 0;

    public WaitScene(Game game, int duration, String... infoText) {
        super(game, true);
        this.game = game;
        this.duration = duration;
        infoPanel.setChildren(infoText);
    }

    @Override
    public void update() {
        animation++;
    }

    @Override
    public boolean isFinished() {
        return animation > duration;
    }
}
