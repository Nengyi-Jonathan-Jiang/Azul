package Game.Scenes;

import Engine.Core.GameObject;
import Game.Backend.Game;
import Game.Backend.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO
public class RankingScene extends PanningGameScene {
    private final List<GameObject> playerNames;
    private GameObject winText;
    private final boolean finished = false;
    public RankingScene(Game game){
        super(game);
        List<Player> players = game.getPlayers();
        Collections.sort(players);
        playerNames = new ArrayList<>();
        for(Player p: players){
            playerNames.add(p.getBoardObject());
        }
    }
    public GameObject getWinner(){
        return playerNames.get(0);  
    }
    
    public boolean isFinished(){
         return finished;   
    }
}
