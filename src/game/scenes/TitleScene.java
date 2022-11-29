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
import game.backend.ai.styles.GreedyComputer;
import game.backend.player.Player;

import java.awt.*;
import java.awt.event.KeyEvent;
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

    public TitleScene() {
        // The base game object
        gObject = new GameObject(new Vec2(App.WIDTH, App.HEIGHT));

        // Background image
        GameObject background = new GameObject(new ImageRendererComponent("table.jpg"));
        background.setSize(background.getComponent(ImageRendererComponent.class).getImageSize());

        // Logo image
        GameObject logo = new GameObject(new Vec2(0, App.HEIGHT * -.25), Vec2.zero, new ImageRendererComponent("logo.png"));
        logo.setSize(logo.getComponent(ImageRendererComponent.class).getImageSize());

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
                new TextRendererComponent("Enable Jeremy (Bot)", new TextStyle(Style.font_large, Style.FG_COLOR, TextStyle.ALIGN_CENTER)),
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
    }

    private void selectNumPlayers(int idx) {
        for (int i = 0; i < 3; i++) {
            RectRendererComponent r = playerSelectButtons[i].getComponent(RectRendererComponent.class);
            Color c = i == idx ? Style.BG_COLOR : Style.DM_COLOR;
//            r.setBorderColor(c);
            r.setFillColor(c);
        }
        numPlayers = idx + 2;
    }

    @Override
    public void onMouseClick(MouseEvent me) {
        if (finished |= startButton.getComponent(ButtonComponent.class).contains(me.position)) return;

        if(jeremyButton.getComponent(ButtonComponent.class).contains(me.position)){
            Color c = (enableJeremy ^= true) ? Style.BG_COLOR : Style.DM_COLOR;
            jeremyButton.getComponent(RectRendererComponent.class).setFillColor(c);
        }

        for (int i = 0; i < 3; i++) {
            if (playerSelectButtons[i].getComponent(ButtonComponent.class).contains(me.position)) {
                selectNumPlayers(i);
                return;
            }
        }
    }

    @Override
    public void onKeyPress(KeyEvent ke) {
        if(ke.getKeyChar() == '`'){
            finished = true;
            numPlayers = 1;
            enableJeremy = true;
        }
    }

    @Override
    public void draw(GameCanvas canvas) {
        double screenWidth = canvas.getWidth(), screenHeight = canvas.getHeight();
        gObject.setPosition(new Vec2(screenWidth, screenHeight).scaledBy(.5));
        gObject.draw(canvas);
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public Iterator<? extends AbstractScene> getScenesAfter() {
        return AbstractScene.makeIterator(new GameScene(new Game(
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
