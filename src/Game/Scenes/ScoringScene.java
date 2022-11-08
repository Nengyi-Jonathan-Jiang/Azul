package Game.Scenes;

import Engine.Core.AbstractScene;
import Game.Backend.Game;
import Game.Backend.Player;

// TODO
public class ScoringScene extends AbstractScene {
    private Game game;
    private Player player

    private boolean finished;

    public ScoringScene(Game g, Player p) {
        game = g;
        player = p;
    }

    @Override
    public boolean isFinished() {

        return false;
    }
}
