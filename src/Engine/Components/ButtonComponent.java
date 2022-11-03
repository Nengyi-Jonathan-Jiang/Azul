package Engine.Components;

import Engine.Core.GameCanvas;

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
        return !(e.getX() < x - width / 2 || e.getX() > x + width / 2 || e.getY() < y - height / 2 || e.getY() > y + height / 2);
    }

    //Button is invisible
    @Override
    public void drawAndUpdate(GameCanvas canvas) {}
}