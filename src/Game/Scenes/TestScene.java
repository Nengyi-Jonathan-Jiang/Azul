package Game.Scenes;

import Game.Backend.Game;
import Game.Backend.Player;

import java.util.List;

public class TestScene extends AbstractGameScene {
    public TestScene(Game game){
        super(game);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}