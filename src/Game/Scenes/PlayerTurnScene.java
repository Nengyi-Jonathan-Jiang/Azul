package Game.Scenes;

import Engine.Core.AbstractScene;
import Game.Backend.Game;
import Game.Backend.Player;

import java.util.Iterator;

// TODO
public class PlayerTurnScene extends AbstractScene {
    public Game game;
    public Player player;

    public PlayerTurnScene(Game game, Player player) {
        this.game = game;
        this.player = player;
    }



    @Override
    public Iterator<AbstractScene> getScenesAfter() {
        return AbstractGameScene.makeIterator(new FactoryOfferingScene(game, player), new PatternLineScene(game, player));
    }
}
