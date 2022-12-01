package game.scenes;

import engine.components.*;
import engine.core.AbstractScene;
import engine.core.GameCanvas;
import engine.core.GameObject;
import engine.core.Vec2;
import engine.input.MouseEvent;
import game.App;
import game.Style;
import game.backend.Game;
import game.backend.player.Player;
import game.frontend.ButtonObject;
import game.frontend.TextObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RankingScene extends AbstractScene {
    private final GameObject gObject = new GameObject();
    private final GameObject winText;
    private final GameObject playAgainButton;
    private final List<Player> players = new ArrayList<>();
    private final List<GameObject> playerNames = new ArrayList<>();
    private boolean finished;

    private static final int TEXT_WIDTH = 560;

    public RankingScene(Game game) {
        GameObject background = new GameObject(new ImageRendererComponent("table.jpg"));
        background.setSize(background.getComponent(ImageRendererComponent.class).getImageSize());

        // Logo image
        GameObject logo = new GameObject(new Vec2(0, -350), Vec2.zero, new ImageRendererComponent("logo.png"));
        logo.setSize(new Vec2(575, 408).scaledBy(.7));
        background.addChild(logo);

        players.addAll(game.getPlayers());

        winText = new GameObject(
                Vec2.zero, new Vec2(TEXT_WIDTH, 60),
                new RoundedRectRendererComponent(30, Style.FG_COLOR, Style.BG_COLOR),
                new TextRendererComponent(
                        "",
                        new TextStyle(Style.font_huge, new Color(152, 0, 0), TextStyle.ALIGN_CENTER)
                )
        );

        GameObject text1 = new GameObject(
                new Vec2(0, -150),
                new Vec2(TEXT_WIDTH, 80),
                new RoundedRectRendererComponent(30, Style.FG_COLOR, Style.BG_COLOR),
                new TextRendererComponent(
                        "Leaderboard",
                        new TextStyle(Style.font_huge, TextStyle.ALIGN_CENTER)
                )
        );

        playAgainButton = new ButtonObject(
                new Vec2(0, 350),
                new Vec2(240, 80),
                "Play Again?",
                new TextStyle(Style.font_large, TextStyle.ALIGN_CENTER),
                () -> new RoundedRectRendererComponent(30, Style.FG_COLOR, Style.BG_COLOR)
        );

        background.addChildren(winText, playAgainButton, text1);
        gObject.addChild(background);
    }

    @Override
    public void onExecutionStart() {
        Collections.sort(players);

        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);

            GameObject back = new GameObject(new RoundedRectRendererComponent(20, Style.FG_COLOR, Style.BG_COLOR));
            back.setSize(new Vec2(TEXT_WIDTH, 40));

            TextObject nameObject = new TextObject(
                    (i + 1) + ". " + p.getName(),
                    new TextStyle(Style.font_medium, Style.FG_COLOR, TextStyle.ALIGN_LEFT | TextStyle.ALIGN_VERTICAL))
                ;
            TextObject scoreObject = new TextObject(
                    p.getScoreMarker().getScore() + "",
                    new TextStyle(Style.font_medium, Style.FG_COLOR, TextStyle.ALIGN_RIGHT | TextStyle.ALIGN_VERTICAL)
            );

            nameObject.setSize(nameObject.getSize().scaledBy(0, 1).plus(new Vec2(TEXT_WIDTH - 40, 0)));
            scoreObject.setSize(nameObject.getSize().scaledBy(0, 1).plus(new Vec2(TEXT_WIDTH - 40, 0)));

            nameObject.getRectComponent().disable();
            scoreObject.getRectComponent().disable();

            back.addChildren(nameObject, scoreObject);
            back.setPosition(new Vec2(0, i * 50 + 100));

            playerNames.add(back);
        }

        winText.getComponent(TextRendererComponent.class).setText(players.get(0).getName() + " wins!");

        gObject.addChildren(playerNames.toArray(GameObject[]::new));
    }

    @Override
    public void draw(GameCanvas canvas) {
        double screenWidth = canvas.getWidth(), screenHeight = canvas.getHeight();
        gObject.setPosition(new Vec2(screenWidth, screenHeight).scaledBy(.5));
        gObject.draw(canvas);
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
