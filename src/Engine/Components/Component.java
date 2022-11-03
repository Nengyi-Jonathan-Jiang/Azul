package Engine.Components;

import Engine.Core.GameCanvas;
import Engine.Core.Point2D;

import java.awt.*;

/**
 * An abstract UI Component class.
 */
public abstract class Component {
    protected int x, y, width, height;

    /**
     * Draws the UIComponent
     * @param canvas The {@link GameCanvas} on which to draw the UIComponent
     */
    public abstract void drawAndUpdate(GameCanvas canvas);

    /**
     * Sets the position and size of the UIComponent then draws the UIComponent
     * @param canvas The {@link GameCanvas} on which to draw the UIComponent
     */
    public final void drawAndUpdate(GameCanvas canvas, Point2D position, Dimension size){
        this.width = size.width;
        this.height = size.height;
        this.x = (int) position.x();
        this.y = (int) position.y();
        drawAndUpdate(canvas);
    }
}