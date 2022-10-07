package Engine.UI;

import Engine.Core.GameCanvas;

import java.awt.*;

/**
 * An abstract UI Component class.
 */
public abstract class UIComponent {
    protected int x, y, width, height;

    /**
     * Draws the UIComponent
     * @param canvas The {@link GameCanvas} on which to draw the UIComponent
     */
    public abstract void draw(GameCanvas canvas);

    /**
     * Sets the position and size of the UIComponent then draws the UIComponent
     * @param canvas The {@link GameCanvas} on which to draw the UIComponent
     */
    public final void draw(GameCanvas canvas, int x, int y, int width, int height){
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        draw(canvas);
    }
}