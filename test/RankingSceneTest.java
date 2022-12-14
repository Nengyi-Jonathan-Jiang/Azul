import engine.core.GameCanvas;
import engine.core.SceneManager;
import game.backend.Game;
import game.backend.player.Player;
import game.scenes.RankingScene;

import javax.swing.*;
import java.awt.Dimension;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RankingSceneTest extends JFrame {
    public RankingSceneTest(){

        GameCanvas canvas = new GameCanvas(200, 200);
        canvas.setPreferredSize(new Dimension(200, 200));
        add(canvas);
        pack();

        List<Player> playerList;
        playerList = IntStream.range(0, 4).mapToObj(i -> new Player(
                i == 2 ? "Jeremy (Bot)" : "Player " + (i + 1), i, null)
        ).collect(Collectors.toList());
        playerList.get(0).getScoreMarker().setScore(10);
        playerList.get(1).getScoreMarker().setScore(20);
        playerList.get(2).getScoreMarker().setScore(50);
        playerList.get(3).getScoreMarker().setScore(40);

        SceneManager.run(new RankingScene(new Game(playerList)), canvas);

        setVisible(true);
    }

    public static void main(String[] args) {
        new RankingSceneTest();
    }
}
