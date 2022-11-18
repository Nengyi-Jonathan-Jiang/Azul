package game.util;

import engine.components.PositionAnimationComponent;
import engine.core.GameObject;
import engine.core.Vec2;

import java.util.function.Consumer;

public class PositionAnimation {
    private PositionAnimation() {
    }


    public static void animate(GameObject gameObject, Runnable action, int duration) {
        animate(gameObject, $ -> action.run(), duration);
    }

    public static void animate(GameObject gameObject, Consumer<GameObject> action, int duration) {
        Vec2 originalPos = gameObject.getAbsolutePosition();

        action.accept(gameObject);

        Vec2 targetPos = gameObject.getPosition();

        gameObject.setAbsolutePosition(originalPos);
        gameObject.getComponent(PositionAnimationComponent.class).moveTo(targetPos, duration);
    }
}
