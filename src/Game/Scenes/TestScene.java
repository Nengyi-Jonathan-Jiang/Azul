package Game.Scenes;

import Game.Backend.Game;
import Game.Backend.Player;

import java.util.List;

public class TestScene extends AbstractGameScene {
    public TestScene(){
        super(new Game(List.of(
            new Player("Player 1"),
            new Player("Player 2")
        )));
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}