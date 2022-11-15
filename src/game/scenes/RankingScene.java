package game.scenes;

import engine.components.ButtonComponent;
import engine.components.ImageRendererComponent;
import engine.components.TextStyle;
import engine.core.*;
import game.App;
import game.Style;
import game.backend.Game;
import game.backend.Player;
import game.frontend.TextObject;
import engine.input.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RankingScene extends AbstractScene {
    private final GameObject background;
    private final TextObject winText;
    private final GameObject playAgainButton;
    private final List<Player> players = new ArrayList<>();
    private final List<GameObject> playerNames = new ArrayList<>();
    private final List<GameObject> playerScores = new ArrayList<>();
    private boolean finished;

    public RankingScene(Game game){

        Vec2 SCREEN_CENTER = new Vec2(App.WIDTH, App.HEIGHT).scaledBy(0.5);

        background = new GameObject(new ImageRendererComponent("table.jpg"));
        background.setSize(background.getComponent(ImageRendererComponent.class).getImageSize());
        background.setPosition(SCREEN_CENTER);

        players.addAll(game.getPlayers());

        winText = new TextObject("");
        winText.setPosition(new Vec2(0, -100).plus(SCREEN_CENTER));

        playAgainButton = new TextObject("Play Again?");
        playAgainButton.setPosition(new Vec2(App.WIDTH / 2., App.HEIGHT - 100));
    }

    @Override
    public void onExecutionStart() {
        Collections.sort(players);

        Vec2 SCREEN_CENTER = new Vec2(App.WIDTH, App.HEIGHT).scaledBy(0.5);

        for(int i = 0; i < players.size(); i++){
            Player p = players.get(i);

            GameObject nameObject = new TextObject(p.getName(), new TextStyle(Style.font_medium, Style.FG_COLOR, TextStyle.ALIGN_LEFT | TextStyle.ALIGN_VERTICAL));
            TextObject scoreObject = new TextObject(
                    p.getScoreMarker().getScore() + "",
                    new TextStyle(Style.font_medium, Style.FG_COLOR, TextStyle.ALIGN_RIGHT | TextStyle.ALIGN_VERTICAL)
            );

            nameObject.setSize(nameObject.getSize().scaledBy(0, 1).plus(new Vec2(400, 0)));
            scoreObject.setSize(nameObject.getSize().scaledBy(0, 1).plus(new Vec2(400, 0)));

            scoreObject.getRectComponent().disable();

            nameObject.setPosition(new Vec2(0, i * 100).plus(SCREEN_CENTER));
            scoreObject.setPosition(new Vec2(0, i * 100).plus(SCREEN_CENTER));

            playerNames.add(nameObject);
            playerScores.add(scoreObject);

            System.out.println(nameObject);
            System.out.println(scoreObject);
        }

        winText.setText(players.get(0).getName() + " wins!");
    }

    @Override
    public void draw(GameCanvas canvas) {
        background.draw(canvas);
        winText.draw(canvas);
        playerNames.forEach(o -> o.draw(canvas));
        playerScores.forEach(o -> o.draw(canvas));
        playAgainButton.draw(canvas);
    }

    public boolean isFinished(){
       return finished;
    }

    @Override
    public void onMouseClick(MouseEvent me) {
        super.onMouseClick(me);
        if(playAgainButton.getComponent(ButtonComponent.class).contains(me.position)){
            finished = true;
        }
    }
}
