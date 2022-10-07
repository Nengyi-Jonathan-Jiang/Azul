package Engine.UI;

import java.awt.event.MouseEvent;

/**
 * An abstract Button class.
 */
public abstract class ButtonComponent extends UIComponent {
    /**
     * @param e A mouse event.
     * @return Whether the mouse event occurred over the button
     */
    public final boolean contains(MouseEvent e){
        return !(e.getX() < x || e.getX() > x + width || e.getY() < y || e.getY() > y + height);
    }
}