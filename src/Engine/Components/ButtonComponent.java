package Engine.Components;

import Engine.Core.GameCanvas;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * An abstract Button class.
 */
public class ButtonComponent extends Component {
    public ButtonComponent(){}

    /**
     * @param e A mouse event.
     * @return Whether the mouse event occurred over the button
     */
    public final boolean contains(MouseEvent e){
        return !(e.getX() < x || e.getX() > x + width || e.getY() < y || e.getY() > y + height);
    }

    //Button is invisible
    @Override
    public void draw(GameCanvas canvas) {}
}
