package Game.Scenes;

import Engine.Core.*;
import Engine.Components.*;
import Engine.Components.TextRendererComponent;
import Game.Backend.Game;
import Game.Backend.Player;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.List;

public class TestScene extends Scene {
    GameObject play_button;
    Game game;

    public TestScene(){
        game = new Game(List.of(
            new Player("Player 1"),
            new Player("Player 2")
        ));
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


    private Vec2 lastMousePos;
    @Override
    public void update() {
        if(Input.isMouseLeftDown()){
            Vec2 oldPosition = game.getGameObject().getPosition();
            Vec2 newPosition = oldPosition.plus(Input.getMousePosition()).minus(lastMousePos);
            game.getGameObject().setPosition(newPosition);
        }

        lastMousePos = Input.getMousePosition();
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