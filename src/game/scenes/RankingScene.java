package game.scenes;

import engine.core.AbstractScene;
import engine.core.GameObject;
import game.backend.Game;
import game.backend.Player;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


// TODO
public class RankingScene extends AbstractScene {
    private final Game game;

    public RankingScene(Game game){
        this.game = game;
        List<Player> players = game.getPlayers();
        Collections.sort(players);
        List<GameObject> playerNames = new ArrayList<>();
        for(Player p: players){
            playerNames.add(p.getBoardObject());
        }

        playAgainButton = new TextObject("Play Again?");
        playAgainButton.setPosition(new Vec2(App.WIDTH / 2., App.HEIGHT - 100));
    }

    public boolean isFinished(){

       return finished;
    }

    @Override
    public void onMouseClick(MouseEvent me) {
        super.onMouseClick(me);
        if(playAgainButton.getComponent(ButtonComponent.class).contains(me)){
            finished = true;
        }
    }
}
