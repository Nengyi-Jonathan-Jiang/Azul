package Engine.UI.Buttons;

import Engine.Core.GameCanvas;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * An abstract Button class.
 */
public abstract class Button {
    protected int x, y, width, height;

    /**
     * @return x-coordinate of top left corner
     */
    public final int getX(){
        return x;
    }
    /**
     * @return y-coordinate of top left corner
     */
    public final int getY(){
        return y;
    }
    /**
     * @param x x-coordinate of top left corner
     */
    public final Button setX(int x){
        this.x = x;
        return this;
    }
    /**
     * @param y y-coordinate of top left corner
     */
    public final Button setY(int y){
        this.y = y;
        return this;
    }

    /**
     * Set the position of the button
     * @param x x-coordinate of top left corner
     * @param y y-coordinate of top left corner
     */
    public final Button setPosition(int x, int y){
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * @return the size of the button in pixels
     */
    public final Dimension getSize(){
        return new Dimension(width, height);
    }

    /**
     * Set the size of the button
     * @param width width of button in pixels
     * @param height height of button in pixels
     */
    public final void setSize(int width, int height){
        this.width = width;
        this.height = height;
    }
    /**
     * Set the size of the button
     * @param size size of button in pixels
     */
    public final void setSize(Dimension size){
        setSize(size.width, size.height);
    }
    
    /**
     * @param e A mouse event.
     * @return Whether the mouse event occurred over the button
     */
    public final boolean contains(MouseEvent e){
        return !(e.getX() < x || e.getX() > x + width || e.getY() < y || e.getY() > y + height);
    }


    /**
     * Draws the button
     * @param canvas The {@link GameCanvas} on which to draw the button
     */
    public abstract void draw(GameCanvas canvas);

    /**
     * Sets the position of the Button then draws the button
     * @param canvas The {@link GameCanvas} on which to draw the button
     */
    public final void draw(GameCanvas canvas, int x, int y){
        setPosition(x, y);
        draw(canvas);
    }

    /**
     * Sets the position and size of the Button then draws the button
     * @param canvas The {@link GameCanvas} on which to draw the button
     */
    public final void draw(GameCanvas canvas, int x, int y, int width, int height){
        setSize(width, height);
        draw(canvas, x, y);
    }
}