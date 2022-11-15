package game.scenes;

import engine.components.*;
import engine.core.*;
import game.*;
import game.backend.*;

import java.awt.Color;
import engine.input.MouseEvent;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TitleScene extends AbstractScene {
    private final GameObject[] playerSelectButtons;
    private final GameObject startButton;
    private int numPlayers;
    private final GameObject gObject;
    private boolean finished = false;

    public TitleScene(){
        // The base game object
        gObject = new GameObject(new Vec2(App.WIDTH, App.HEIGHT));
        gObject.setTopLeft(Vec2.zero);

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
        for(int i = 0; i < 3; i++){
            playerSelectButtons[i] = new GameObject(
                TEXT_SIZE.scaledBy(.35 * (i - 1), 1.5),
                TEXT_SIZE.scaledBy(.3, 1.),
                new RectRendererComponent(Style.FG_COLOR, Style.BG_COLOR),
                new TextRendererComponent(i + 2 + "", new TextStyle(Style.font_large, Style.FG_COLOR, TextStyle.ALIGN_CENTER)),
                new ButtonComponent()
            );
        }

        startButton = new GameObject(
            TEXT_SIZE.scaledBy(0, 3),
            TEXT_SIZE,
            new RectRendererComponent(Style.FG_COLOR, Style.BG_COLOR),
            new TextRendererComponent("Start Game", new TextStyle(Style.font_large, Style.FG_COLOR, TextStyle.ALIGN_CENTER)),
            new ButtonComponent()
        );

        gObject.addChildren(background, logo, playerSelectText);
        gObject.addChildren(playerSelectButtons);
        gObject.addChild(startButton);

        selectNumPlayers(2);
    }

    private void selectNumPlayers(int idx){
        for(int i = 0; i < 3; i++){
            RectRendererComponent r = playerSelectButtons[i].getComponent(RectRendererComponent.class);
            TextRendererComponent t = playerSelectButtons[i].getComponent(TextRendererComponent.class);
            Color c = i == idx ? Style.HL_COLOR : Style.FG_COLOR;
            r.setBorderColor(c);
        }
        numPlayers = idx + 2;
    }

    @Override
    public void onMouseClick(MouseEvent me) {
        if(startButton.getComponent(ButtonComponent.class).contains(me.position)){
            finished = true;
            return;
        }
        for(int i = 0; i < 3; i++){
            if(playerSelectButtons[i].getComponent(ButtonComponent.class).contains(me.position)){
                selectNumPlayers(i);
                return;
            }
        }
    }

    @Override
    public void draw(GameCanvas canvas) {
        gObject.draw(canvas);
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public Iterator<? extends AbstractScene> getScenesAfter() {
        // TODO: after GameScene is implemented, change to return a GameScene
        return AbstractScene.makeIterator(new GameScene(new Game(
            (IntStream.range(0, numPlayers)).mapToObj(i -> new Player("Player " + (i + 1), i)).collect(Collectors.toList())
        )));
    }
}
