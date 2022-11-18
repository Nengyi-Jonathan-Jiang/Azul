package engine.components;

import engine.core.Component;
import engine.core.GameCanvas;
import engine.core.Vec2;

/**
 * An abstract Button class.
 */
public class ButtonComponent extends Component {
    /**
     * @param v The position in screen space of the mouse
     * @return Whether the mouse event occurred over the GameObject
     */
    public final boolean contains(Vec2 v) {
        Vec2 difference = v.minus(gameObject.getAbsolutePosition());
        return difference.map(Math::abs).scaledBy(2).compareCoordinates(gameObject.getSize());
    }

    @Override
    public void drawAndUpdate(GameCanvas canvas) {
    }
}