package game.scenes;

import engine.components.*;
import engine.core.*;
import engine.input.MouseEvent;
import game.Style;
import game.backend.Game;
import game.backend.ai.styles.GreedyComputer;
import game.backend.player.Player;
import game.frontend.TextObject;

import java.awt.*;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TitleScene extends AbstractScene {
    private final GameObject[] playerSelectButtons;
    private final GameObject startButton;
    private final GameObject gObject;
    private int numPlayers;
    private boolean finished = false;

    private boolean enableJeremy = false;
    private final GameObject jeremyButton;

    private final GameObject help, help2;

    private boolean gotoRules1 = false;
    private boolean gotoRules2 = false;

    public TitleScene() {
        // The base game object
        gObject = new GameObject();

        // Background image
        GameObject background = new GameObject(new ImageRendererComponent("table.jpg"));
        background.setSize(background.getComponent(ImageRendererComponent.class).getImageSize());

        // Logo image
        GameObject logo = new GameObject(new Vec2(0, -170), Vec2.zero, new ImageRendererComponent("logo.png"));
        logo.setSize(new Vec2(575, 400).scaledBy(.6));

        // Player select text
        GameObject playerSelectText = new GameObject(
                new RectRendererComponent(Style.FG_COLOR, Style.BG_COLOR),
                new TextRendererComponent("Select number of players", new TextStyle(Style.font_large, Style.FG_COLOR, TextStyle.ALIGN_CENTER))
        );
        final Vec2 TEXT_SIZE = playerSelectText.getComponent(TextRendererComponent.class).getRenderedSize()
                .plus(new Vec2(Style.TEXT_PADDING * 2.5));
        playerSelectText.setSize(TEXT_SIZE);

        // Player select buttons - the player will click on these
        playerSelectButtons = new GameObject[3];
        for (int i = 0; i < 3; i++) {
            playerSelectButtons[i] = new GameObject(
                    TEXT_SIZE.scaledBy(.35 * (i - 1), 1.5),
                    TEXT_SIZE.scaledBy(.3, 1.),
                    new RectRendererComponent(Style.FG_COLOR, Style.BG_COLOR),
                    new TextRendererComponent(i + 2 + "", new TextStyle(Style.font_large, Style.FG_COLOR, TextStyle.ALIGN_CENTER)),
                    new ButtonComponent()
            );
        }

        jeremyButton = new GameObject(
                TEXT_SIZE.scaledBy(0, 3),
                TEXT_SIZE,
                new RectRendererComponent(Style.FG_COLOR, Style.DM_COLOR),
                new TextRendererComponent("Jeremy Bot (Disabled)", new TextStyle(Style.font_large, Style.FG_COLOR, TextStyle.ALIGN_CENTER)),
                new ButtonComponent()
        );

        startButton = new GameObject(
                TEXT_SIZE.scaledBy(0, 4.5),
                TEXT_SIZE,
                new RectRendererComponent(Style.FG_COLOR, Style.BG_COLOR),
                new TextRendererComponent("Start Game", new TextStyle(Style.font_large, Style.FG_COLOR, TextStyle.ALIGN_CENTER)),
                new ButtonComponent()
        );

        gObject.addChildren(background, logo, playerSelectText);
        gObject.addChildren(playerSelectButtons);
        gObject.addChildren(jeremyButton, startButton);
        selectNumPlayers(2);

        help = new TextObject("Rulebook");
        help2 = new TextObject("How To Play");
    }

    private void selectNumPlayers(int idx) {
        for (int i = 0; i < 3; i++) {
            RectRendererComponent r = playerSelectButtons[i].getComponent(RectRendererComponent.class);
            Color c = i == idx ? Style.BG_COLOR : Style.DM_COLOR;
            r.setFillColor(c);
        }
        numPlayers = idx + 2;
    }

    @Override
    public void onMouseClick(MouseEvent me) {
        if(finished = startButton.getComponent(ButtonComponent.class).contains(me.position)) return;
        if(finished = help.getComponent(ButtonComponent.class).contains(me.position) && (gotoRules1 = true)) return;
        if(finished = help2.getComponent(ButtonComponent.class).contains(me.position) && (gotoRules2 = true)) return;

        if(jeremyButton.getComponent(ButtonComponent.class).contains(me.position)){
            Color c = (enableJeremy ^= true) ? Style.BG_COLOR : Style.DM_COLOR;
            jeremyButton.getComponent(RectRendererComponent.class).setFillColor(c);
            jeremyButton.getComponent(TextRendererComponent.class).setText(
                    enableJeremy
                        ? "Jeremy Bot (Enabled)"
                        : "Jeremy Bot (Disabled)"
            );
        }

        for (int i = 0; i < 3; i++) {
            if (playerSelectButtons[i].getComponent(ButtonComponent.class).contains(me.position)) {
                selectNumPlayers(i);
                return;
            }
        }
    }

    @Override
    public void draw(GameCanvas canvas) {
        help2.setBottomRight(canvas.get_size().scaledBy(.5).minus(new Vec2(10)));
        help.setBottomRight(help2.getBottomRight().minus(new Vec2(0, help2.getSize().y + 10)));

        gObject.draw(canvas);

        help.draw(canvas);
        help2.draw(canvas);
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public Iterator<? extends AbstractScene> getScenesAfter() {
        return gotoRules1
            ? makeIterator(new RulesScene(), new InstantaneousScene() {
            @Override
                public void execute() {
                    finished = gotoRules1 = gotoRules2 = false;
                }
            }, this)
            : gotoRules2
            ? makeIterator(new HowToPlayScene(), new InstantaneousScene() {
                @Override
                public void execute() {
                    finished = gotoRules1 = gotoRules2 = false;
                }
            }, this)
            : makeIterator(new GameScene(new Game(
                (IntStream.range(0, numPlayers)).mapToObj(
                        // Max player name length: 14
                        i -> {
                            if(enableJeremy && i + 1 == numPlayers){
                                return new Player("Jeremy (Bot)", i, new GreedyComputer());
                            }
                            return new Player("Player " + (i + 1), i, null);
                        }
                ).collect(Collectors.toList())
            )));
    }
}
