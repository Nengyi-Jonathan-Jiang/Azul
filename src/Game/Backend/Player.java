package Game.Backend;

import Engine.Core.GameObject;
import Game.Frontend.PlayerGameObject;

public class Player {
    private String name;

    private GameObject playerObject;

    public Player(String playerName){
        name = playerName;
        playerObject = new PlayerGameObject(playerName);
    }

    public GameObject getGameObject(){
        return playerObject;
    }
}
