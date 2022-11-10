package engine.components;

import engine.core.GameCanvas;
import engine.core.Vec2;

import java.awt.event.MouseEvent;

/**
 * An abstract Button class.
 */
public class ButtonComponent extends Component {
    /**
     * @param e A mouse event.
     * @return Whether the mouse event occurred over the GameObject
     */
    public final boolean contains(MouseEvent e){
        Vec2 difference = new Vec2(e.getX(), e.getY()).minus(gameObject.getAbsolutePosition());
        return difference.map(Math::abs).scaledBy(2).compareCoordinates(gameObject.getSize());
    }

    @Override
    public void drawAndUpdate(GameCanvas canvas) {}
}