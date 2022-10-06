package Engine.UI.Buttons;

import Engine.UI.UIComponent;

import java.awt.event.MouseEvent;

/**
 * An abstract Button class.
 */
public abstract class Button extends UIComponent {
    public Button(int x, int y, int width, int height){
        super(x, y, width, height);
    }

    /**
     * @param e A mouse event.
     * @return Whether the mouse event occurred over the button
     */
    public final boolean contains(MouseEvent e){
        return !(e.getX() < x || e.getX() > x + width || e.getY() < y || e.getY() > y + height);
    }
}