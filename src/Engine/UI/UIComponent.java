package Engine.UI;

import Engine.Core.GameCanvas;

import java.awt.*;

/**
 * An abstract UI Component class.
 */
public abstract class UIComponent {
    protected int x, y, width, height;

    public UIComponent(int x, int y, int width, int height){
        this.setPosition(x, y).setSize(width, height);
    }

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
    public final UIComponent setX(int x){
        this.x = x;
        return this;
    }
    /**
     * @param y y-coordinate of top left corner
     */
    public final UIComponent setY(int y){
        this.y = y;
        return this;
    }

    /**
     * Set the position of the UIComponent
     * @param x x-coordinate of top left corner
     * @param y y-coordinate of top left corner
     */
    public final UIComponent setPosition(int x, int y){
        this.x = x;
        this.y = y;
        return this;
    }


    /**
     * @return width of UIComponent in pixels
     */
    public final int getWidth(){
        return width;
    }
    /**
     * @return height of UIComponent in pixels
     */
    public final int getHeight(){
        return height;
    }
    /**
     * @param width width of UIComponent in pixels
     */
    public final UIComponent setWidth(int width){
        this.width = width;
        return this;
    }
    /**
     * @param height height of UIComponent in pixels
     */
    public final UIComponent setHeight(int height){
        this.height = height;
        return this;
    }

    /**
     * @return the size of the UIComponent in pixels
     */
    public final Dimension getSize(){
        return new Dimension(width, height);
    }

    /**
     * Set the size of the UIComponent
     * @param width width of UIComponent in pixels
     * @param height height of UIComponent in pixels
     */
    public final void setSize(int width, int height){
        this.width = width;
        this.height = height;
    }
    /**
     * Set the size of the UIComponent
     * @param size size of UIComponent in pixels
     */
    public final void setSize(Dimension size){
        setSize(size.width, size.height);
    }

    /**
     * Draws the UIComponent
     * @param canvas The {@link GameCanvas} on which to draw the UIComponent
     */
    public abstract void draw(GameCanvas canvas);

    /**
     * Sets the position of the UIComponent then draws the UIComponent
     * @param canvas The {@link GameCanvas} on which to draw the UIComponent
     */
    public final void draw(GameCanvas canvas, int x, int y){
        setPosition(x, y);
        draw(canvas);
    }

    /**
     * Sets the position and size of the UIComponent then draws the UIComponent
     * @param canvas The {@link GameCanvas} on which to draw the UIComponent
     */
    public final void draw(GameCanvas canvas, int x, int y, int width, int height){
        setSize(width, height);
        draw(canvas, x, y);
    }
}