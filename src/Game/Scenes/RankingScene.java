package Game.Scenes;

import Engine.Core.GameObject;
import Game.Backend.Game;
import Game.Backend.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO
public class RankingScene extends PanningGameScene {
    private List<GameObject> playerNames;
    private GameObject winText;
    private boolean finished = false;
    public RankingScene(Game game){
        super(game);
        List<Player> players = game.getPlayers();
        Collections.sort(players);
        playerNames = new ArrayList<>();
        for(Player p: players){
            playerNames.add(p.getGameObject());   
        }
    }
    public GameObject getWinner(){
        return playerNames.get(0);  
    }
    
    public boolean isFinished(){
         return finished;   
    }
}
