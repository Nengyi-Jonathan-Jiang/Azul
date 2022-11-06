package Game.Backend;

import Engine.Components.ImageRendererComponent;
import Engine.Core.GameObject;
import Engine.Core.Vec2;
import Game.App;
import Game.Frontend.GameGameObject;
import Game.Frontend.PlayerGameObject;

import java.util.List;

public class Game {
    private final GameObject gObject;

    private final List<Player> players;
    private final Bag bag;
    private final Middle middle;

    // Todo: add players argument, must wait for Player class
    public Game(List<Player> players){
        this.players = players;
        this.bag = new Bag();
        this.middle = new Middle(switch(players.size()){
            case 2 -> 5;
            case 3 -> 7;
            case 4 -> 9;
            default -> throw new Error("Invalid number of players passed into Game class");
        });

        gObject = new GameGameObject(this);
        gObject.setPosition(new Vec2(App.WIDTH / 2f, App.HEIGHT / 2f));

        gObject.addChild(middle.getGameObject());
    }

    public List<Player> getPlayers(){
        return players;
    }

    public GameObject getGameObject(){
        return gObject;
    }

    public Middle getMiddle() {
        return middle;
    }

    public Bag getBag() {
        return bag;
    }
}
