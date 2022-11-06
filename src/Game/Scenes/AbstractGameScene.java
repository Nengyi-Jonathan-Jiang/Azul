package Game.Scenes;

import Engine.Core.*;
import Game.Backend.Game;
import Game.App;

public abstract class AbstractGameScene extends AbstractScene {
    protected final Game game;

    public AbstractGameScene(Game game){
        this.game = game;
    }

    private static Vec2 lastMousePos = Vec2.zero;
    @Override
    public void update() {
        if(Input.isMouseLeftDown()){
            GameObject gObject = game.getGameObject();
            gObject.move(Input.getMousePosition().minus(lastMousePos).scaledBy(3));

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
}
