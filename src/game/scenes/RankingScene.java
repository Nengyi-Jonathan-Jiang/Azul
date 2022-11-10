package game.scenes;

import engine.core.GameObject;
import game.backend.Game;
import game.backend.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO
public class RankingScene extends PanningGameScene {

    public RankingScene(Game game){
        super(game);
        List<Player> players = game.getPlayers();
        Collections.sort(players);
        List<GameObject> playerNames = new ArrayList<>();
        for(Player p: players){
            playerNames.add(p.getBoardObject());
        }
        System.out.println(playerNames);
    }

    public boolean isFinished(){
        return false;
    }
}
