package game.scenes;

import engine.components.ButtonComponent;
import engine.components.ImageRendererComponent;
import engine.components.RectRendererComponent;
import engine.components.TextStyle;
import engine.core.AbstractScene;
import engine.core.GameCanvas;
import engine.core.GameObject;
import engine.core.Vec2;
import engine.input.MouseEvent;
import game.App;
import game.Style;
import game.backend.Game;
import game.backend.player.Player;
import game.frontend.TextObject;

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

    public RankingScene(Game game) {
        Vec2 SCREEN_CENTER = new Vec2(App.WIDTH, App.HEIGHT).scaledBy(0.5);

        background = new GameObject(new ImageRendererComponent("table.jpg"));
        background.setSize(background.getComponent(ImageRendererComponent.class).getImageSize());
        background.setPosition(SCREEN_CENTER);

        // Logo image
        GameObject logo = new GameObject(new Vec2(0, App.HEIGHT * -.39), Vec2.zero, new ImageRendererComponent("logo.png"));
        logo.setSize(new Vec2(575, 408).scaledBy(.5));
        background.addChild(logo);

        players.addAll(game.getPlayers());



        winText = new TextObject("", new TextStyle(Style.font_huge, TextStyle.ALIGN_CENTER));
        winText.setPosition(new Vec2(0, -50));

        GameObject text1 = new TextObject("Leaderboard", new TextStyle(Style.font_huge, TextStyle.ALIGN_CENTER));
        text1.setPosition(new Vec2(0, -200));
        text1.setSize(new Vec2(430, 80));

        playAgainButton = new TextObject("Play Again?", new TextStyle(Style.font_large, TextStyle.ALIGN_CENTER));
        playAgainButton.setPosition(new Vec2(0, 350));
        playAgainButton.setSize(new Vec2(240, 80));

        background.addChildren(winText, playAgainButton, text1);
    }

    @Override
    public void onExecutionStart() {
        Collections.sort(players);

        Vec2 SCREEN_CENTER = new Vec2(App.WIDTH, App.HEIGHT).scaledBy(0.5);

        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);

            GameObject back = new GameObject(new RectRendererComponent(Style.FG_COLOR, Style.BG_COLOR));
            back.setSize(new Vec2(430, 40));

            TextObject nameObject = new TextObject(p.getName(), new TextStyle(Style.font_medium, Style.FG_COLOR, TextStyle.ALIGN_LEFT | TextStyle.ALIGN_VERTICAL));
            TextObject scoreObject = new TextObject(
                    p.getScoreMarker().getScore() + "",
                    new TextStyle(Style.font_medium, Style.FG_COLOR, TextStyle.ALIGN_RIGHT | TextStyle.ALIGN_VERTICAL)
            );

            nameObject.setSize(nameObject.getSize().scaledBy(0, 1).plus(new Vec2(400, 0)));
            scoreObject.setSize(nameObject.getSize().scaledBy(0, 1).plus(new Vec2(400, 0)));

            nameObject.getRectComponent().disable();
            scoreObject.getRectComponent().disable();

            back.addChildren(nameObject, scoreObject);
            back.setPosition(new Vec2(0, i * 60 + 50).plus(SCREEN_CENTER));

            playerNames.add(back);
        }

        winText.setText(players.get(0).getName() + " wins!");
        winText.setSize(new Vec2(430, 60));
    }

    @Override
    public void draw(GameCanvas canvas) {
        background.draw(canvas);
        playerNames.forEach(o -> o.draw(canvas));
        playerScores.forEach(o -> o.draw(canvas));
        playAgainButton.draw(canvas);
    }

    public boolean isFinished() {
        return finished;
    }

    @Override
    public void onMouseClick(MouseEvent me) {
        super.onMouseClick(me);
        if (playAgainButton.getComponent(ButtonComponent.class).contains(me.position)) {
            finished = true;
        }
    }
}
