package game.backend;

import engine.core.GameCanvas;
import engine.core.GameObject;
import engine.core.Vec2;
import game.App;
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
            case 2 -> 5;
            case 3 -> 7;
            case 4 -> 9;
            default -> throw new Error("Invalid number of players passed into Game class");
        });

        wObject = new WorldGameObject();

        gObject = new GameGameObject(this);
        gObject.setPosition(new Vec2(App.WIDTH / 2f, App.HEIGHT / 2f));

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