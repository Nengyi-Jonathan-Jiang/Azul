package Game.Scenes;

import Engine.Core.AbstractScene;
import Game.Backend.Game;

// TODO
public class ScoringScene extends AbstractScene {
    private Game game;

    public ScoringScene(Game game) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
