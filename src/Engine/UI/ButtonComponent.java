package Engine.UI;

import Engine.Core.GameCanvas;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * An abstract Button class.
 */
public class ButtonComponent extends UIComponent {
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
     * @return Whether the mouse event occurred over the button
     */
    public final boolean contains(MouseEvent e){
        return !(e.getX() < x || e.getX() > x + width || e.getY() < y || e.getY() > y + height);
    }


    @Override
    public void draw(GameCanvas canvas) {
        canvas.graphics.setColor(borderColor);
        canvas.graphics.drawRect(x - width / 2, y - height / 2, width, height);
    }
}