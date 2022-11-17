package game.scenes;

import engine.components.ButtonComponent;
import engine.core.AbstractScene;
import engine.core.GameCanvas;
import engine.core.Vec2;
import engine.input.MouseEvent;
import game.backend.Game;
import game.frontend.TextObject;

class ScoringConfirmationScene extends AbstractScene {
    private final Game game;
    private final TextObject continueButton;
    private boolean finished = false;

    ScoringConfirmationScene(Game game) {
        this.game = game;
        continueButton = new TextObject("Continue");
    }

    @Override
    public void onMouseClick(MouseEvent me) {
        finished |= continueButton.getComponent(ButtonComponent.class).contains(me.position);
    }

    @Override
    public void draw(GameCanvas canvas) {
        continueButton.setBottomRight(new Vec2(canvas.getWidth(), canvas.getHeight()));

        game.draw(canvas);
        continueButton.draw(canvas);
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
