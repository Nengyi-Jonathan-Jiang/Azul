package game.scenes;

import engine.core.*;
import game.App;
import game.backend.Game;

public class PanningGameScene extends AbstractScene {
    private static Vec2 lastMousePos = Vec2.zero;
    protected final Game game;

    public PanningGameScene(Game game) {
        this.game = game;
    }

    @Override
    public void update() {
        if (Input.isMouseRightDown()) {
            GameObject gObject = game.getGameObject();
            gObject.move(Input.getMousePosition().minus(lastMousePos).scaledBy(1.5));

            if (gObject.getTopLeft().x >= 0) {
                gObject.move(new Vec2(-gObject.getTopLeft().x, 0));
            }
            if (gObject.getTopRight().x <= App.WIDTH) {
                gObject.move(new Vec2(App.WIDTH - gObject.getTopRight().x, 0));
            }
            if (gObject.getTopLeft().y >= 0) {
                gObject.move(new Vec2(0, -gObject.getTopLeft().y));
            }
            if (gObject.getBottomLeft().y <= App.HEIGHT) {
                gObject.move(new Vec2(0, App.HEIGHT - gObject.getBottomLeft().y));
            }
        }

        lastMousePos = Input.getMousePosition();
    }


    @Override
    public void draw(GameCanvas canvas) {
        game.draw(canvas);
    }
}
