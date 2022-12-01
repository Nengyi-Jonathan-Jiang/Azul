package game.scenes;

import engine.core.AbstractScene;
import game.backend.Game;
import game.backend.player.Player;

import java.util.Iterator;

public class ComputerTurnScene extends PlayerTurnScene {
    public ComputerTurnScene(Game game, Player player) {
        super(game, player);
    }

    @Override
    public Iterator<AbstractScene> getScenesAfter() {
        return makeIterator(
                new WaitScene(game, 40, player + "'s turn", null),
                new ComputerFactoryOfferingScene(game, player),
                new WaitScene(game, 30, player + "'s turn", null)
        );
    }
}