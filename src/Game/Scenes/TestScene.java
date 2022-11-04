package Game.Scenes;

import Engine.Core.*;
import Engine.Components.*;
import Engine.Components.TextRendererComponent;
import Game.Backend.Game;

import java.awt.*;
import java.awt.event.MouseEvent;

public class TestScene extends Scene {
    GameObject play_button;
    Game game;

    public TestScene(){
        game = new Game();
        play_button = new GameObject(new Vec2(150, 150), new Vec2(100, 100),
                new ImageRendererComponent("Img.png"),
                new TextRendererComponent("Play", new TextStyle(Color.RED, new Color(127, 127, 127, 127))),
                new ButtonComponent()
        );
    }

    @Override
    public void onMouseClick(MouseEvent me) {
        if(me != null && play_button.getComponent(ButtonComponent.class).contains(me)){
            System.out.println("Click");
        }
    }

    @Override
    public void draw(GameCanvas canvas) {
        play_button.draw(canvas);
        game.getGameObject().draw(canvas);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}