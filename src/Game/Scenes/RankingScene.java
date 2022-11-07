package Game.Scenes;

import Engine.Core.AbstractScene;
import Game.Backend.Game;

// TODO
public class RankingScene extends AbstractScene {
    private List<GameObject> playerNames;
    private GameObject winText;
    private boolean finished = false;
    public RankingScene(Game game){
        super(game);
        List<Player> players = game.getPlayers();
        Collections.sort(players);
        playerNames = new ArrayList<GameObject>();
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
