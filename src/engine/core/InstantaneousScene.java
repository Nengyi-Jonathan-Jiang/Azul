package engine.core;

import engine.input.MouseEvent;

import java.awt.event.KeyEvent;

/**
 * Extend this class when your intention is to make an instant scene executing a single action as its body
 */
public abstract class InstantaneousScene extends AbstractScene {
    @Override
    public final void update() {}
    @Override
    public final void draw(GameCanvas canvas){}
    @Override
    public final void onKeyPress(KeyEvent ke) {}
    @Override
    public final void onMouseClick(MouseEvent me) {}
    @Override
    public final void onExecutionEnd() {}
    @Override
    public final void onExecutionStart() {
        execute();
    }
    @Override
    public final boolean isFinished() {
        return true;
    }

    public void execute(){}

}
