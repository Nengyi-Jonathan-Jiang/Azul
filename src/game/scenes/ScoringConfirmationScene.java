package game.scenes;

import engine.components.ButtonComponent;
import engine.input.MouseEvent;
import game.backend.Game;
import game.backend.player.Player;

class ScoringConfirmationScene extends BasicGameScene {
    private boolean finished = false;

    ScoringConfirmationScene(Game game, Player player) {
        super(game);
        infoPanel.setChildren("Scoring " + player, "Continue");
    }

    @Override
    public void onMouseClick(MouseEvent me) {
        finished |= infoPanel.getRight().getComponent(ButtonComponent.class).contains(me.position);
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}