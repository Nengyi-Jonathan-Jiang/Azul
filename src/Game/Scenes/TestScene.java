package Game.Scenes;

import Engine.Core.*;
import Engine.Components.*;
import Engine.Components.TextRendererComponent;
import Game.Backend.Game;
import Game.Backend.Player;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.List;

import Game.App;

public class TestScene extends Scene {
    Game game;

    public TestScene(){
        game = new Game(List.of(
            new Player("Player 1"),
            new Player("Player 2")
        ));
    }

    @Override
    public void onMouseClick(MouseEvent me) {
        // IDK
    }


    private Vec2 lastMousePos;
    @Override
    public void update() {
        if(Input.isMouseLeftDown()){
            GameObject gObject = game.getGameObject();
            gObject.move(Input.getMousePosition().minus(lastMousePos));

            if(gObject.getTopLeft().x >= 0){
                gObject.move(new Vec2(-gObject.getTopLeft().x, 0));
            }
            if(gObject.getTopRight().x <= App.WIDTH){
                gObject.move(new Vec2(App.WIDTH - gObject.getTopRight().x, 0));
            }
            if(gObject.getTopLeft().y >= 0){
                gObject.move(new Vec2(0, -gObject.getTopLeft().y));
            }
            if(gObject.getBottomLeft().y <= App.HEIGHT){
                gObject.move(new Vec2(0, App.HEIGHT - gObject.getBottomLeft().y));
            }
        }

        lastMousePos = Input.getMousePosition();
    }


    @Override
    public void draw(GameCanvas canvas) {
        game.getGameObject().draw(canvas);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}