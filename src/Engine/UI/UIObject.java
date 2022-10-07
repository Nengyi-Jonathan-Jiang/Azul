package Engine.UI;

import Engine.Core.GameCanvas;

import java.awt.*;
import java.util.Map;

public class UIObject {
    private Map<String, UIComponent> components;


    protected int x, y, width, height;

    public UIObject(int x, int y, int width, int height){
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
    public final UIObject setX(int x){
        this.x = x;
        return this;
    }
    /**
     * @param y y-coordinate of top left corner
     */
    public final UIObject setY(int y){
        this.y = y;
        return this;
    }

    /**
     * Set the position of the UIObject
     * @param x x-coordinate of top left corner
     * @param y y-coordinate of top left corner
     */
    public final UIObject setPosition(int x, int y){
        this.x = x;
        this.y = y;
        return this;
    }


    /**
     * @return width of UIObject in pixels
     */
    public final int getWidth(){
        return width;
    }
    /**
     * @return height of UIObject in pixels
     */
    public final int getHeight(){
        return height;
    }
    /**
     * @param width width of UIObject in pixels
     */
    public final UIObject setWidth(int width){
        this.width = width;
        return this;
    }
    /**
     * @param height height of UIObject in pixels
     */
    public final UIObject setHeight(int height){
        this.height = height;
        return this;
    }

    /**
     * @return the size of the UIObject in pixels
     */
    public final Dimension getSize(){
        return new Dimension(width, height);
    }

    /**
     * Set the size of the UIObject
     * @param width width of UIObject in pixels
     * @param height height of UIObject in pixels
     */
    public final void setSize(int width, int height){
        this.width = width;
        this.height = height;
    }
    /**
     * Set the size of the UIObject
     * @param size size of UIObject in pixels
     */
    public final void setSize(Dimension size){
        setSize(size.width, size.height);
    }

    /**
     * Draws the UIObject
     * @param canvas The {@link GameCanvas} on which to draw the UIObject
     */
    public final void draw(GameCanvas canvas){
        for(UIComponent component : components.values()) component.draw(canvas, x, y, width, height);
    }

    /**
     * Sets the position of the UIObject then draws the UIObject
     * @param canvas The {@link GameCanvas} on which to draw the UIObject
     */
    public final void draw(GameCanvas canvas, int x, int y){
        setPosition(x, y);
        draw(canvas);
    }

    /**
     * Sets the position and size of the UIObject then draws the UIObject
     * @param canvas The {@link GameCanvas} on which to draw the UIObject
     */
    public final void draw(GameCanvas canvas, int x, int y, int width, int height){
        setSize(width, height);
        draw(canvas, x, y);
    }
    
    public final <Type extends UIComponent> UIObject addComponent(Type t){
        components.put(t.getClass().getName(), t);
        return this;
    }

    public final <Type extends UIComponent> Type getComponent(Class<Type> type){
        return (Type) components.get(type.getName());
    }
}