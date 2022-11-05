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

    // Todo: add players argument, must wait for Player class
    public Game(List<Player> players){
        this.players = players;


        gObject = new GameGameObject(this);
        gObject.setPosition(new Vec2(App.WIDTH / 2f, App.HEIGHT / 2f));
    }

    public List<Player> getPlayers(){
        return players;
    }

    public GameObject getGameObject(){
        return gObject;
    }
}
