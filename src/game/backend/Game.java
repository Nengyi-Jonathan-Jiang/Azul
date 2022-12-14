package game.backend;

import engine.core.GameCanvas;
import engine.core.GameObject;
import engine.core.Vec2;
import game.App;
import game.backend.board.Bag;
import game.backend.board.Middle;
import game.backend.player.Player;
import game.frontend.GameGameObject;
import game.frontend.WorldGameObject;

import java.util.List;

public class Game {
    private final GameObject gObject;
    private final GameObject wObject;

    private final List<Player> players;
    private final Bag bag;
    private final Middle middle;

    public Game(List<Player> players) {
        this.players = players;
        this.bag = new Bag();
        this.middle = new Middle(switch (players.size()) {
            case 1 -> 2;
            case 2 -> 5;
            case 3 -> 7;
            case 4 -> 9;
            default -> throw new Error("Invalid number of players passed into Game class");
        });

        wObject = new WorldGameObject();

        gObject = new GameGameObject(this);

        gObject.addChild(middle.getGameObject());

        wObject.addChild(gObject);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public GameObject getGameObject() {
        return gObject;
    }

    public void draw(GameCanvas canvas) {
        wObject.draw(canvas);
    }

    public Middle getMiddle() {
        return middle;
    }

    public Bag getBag() {
        return bag;
    }
}