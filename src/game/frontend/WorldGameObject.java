package game.frontend;

import engine.core.GameCanvas;
import engine.core.GameObject;
import engine.core.Vec2;
import game.App;

public class WorldGameObject extends GameObject {
    @Override
    public GameObject draw(GameCanvas canvas) {
        double screenWidth = canvas.getWidth(), screenHeight = canvas.getHeight();
        double ox = (screenWidth - canvas.getWidth()) / 2, oy = (screenHeight - canvas.getHeight()) / 2;
        setPosition(new Vec2(ox, oy));

        return super.draw(canvas);
    }
}
