package game.scenes;

import engine.core.AbstractScene;

public class ActionScene extends AbstractScene {
    private final Runnable action;

    public ActionScene(Runnable action) {
        this.action = action;
    }

    @Override
    public void onExecutionStart() {
        action.run();
    }
}
