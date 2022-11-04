package Engine.Components;

import Engine.Core.GameCanvas;
import Engine.Core.Vec2;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * An abstract Button class.
 */
public class ButtonComponent extends Component {
    protected static final Color DEFAULT_BORDER_COLOR = Color.BLACK;
    protected final Color borderColor;

    public ButtonComponent(){
        this(DEFAULT_BORDER_COLOR);
    }

    public ButtonComponent(Color borderColor){
        this.borderColor = borderColor;
    }

    /**
     * @param e A mouse event.
     * @return Whether the mouse event occurred over the GameObject
     */
    public final boolean contains(MouseEvent e){
        Vec2 difference = new Vec2(e.getX(), e.getY()).minus(gameObject.getAbsolutePosition());
        return difference.map(Math::abs).scaledBy(2).compareCoordinates(gameObject.getSize());
    }

    //Button is invisible
    @Override
    public void drawAndUpdate(GameCanvas canvas) {}
}