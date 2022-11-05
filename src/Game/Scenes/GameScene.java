package Game.Scenes;

import Engine.Core.GameCanvas;
import Engine.Core.Input;
import Engine.Core.Scene;
import Engine.Core.Vec2;
import Game.Backend.Game;

import java.awt.event.MouseEvent;

public class GameScene extends Scene {
    private Game game;

    public GameScene(Game game){

    }

    @Override
    public void draw(GameCanvas canvas) {
        game.getGameObject().draw(canvas);
    }

    private Vec2 lastMousePos;
    @Override
    public void update() {
        if(Input.isMouseLeftDown()){
            Vec2 p = game.getGameObject().getPosition();
            game.getGameObject().setPosition(p.plus(Input.getMousePosition()).minus(lastMousePos));
        }

        lastMousePos = Input.getMousePosition();
    }

    public boolean isFinished(){
        // TODO: once player implements hasCompletedRow, return if any player has completed a row
        return false;
    }
}
