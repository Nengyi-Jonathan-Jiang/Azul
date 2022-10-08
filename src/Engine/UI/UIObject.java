package Engine.UI;

import Engine.Core.GameCanvas;

import java.awt.Dimension;
import java.util.*;

public class UIObject {
    private final Map<String, UIComponent> componentMap;
    private final List<UIComponent> componentList;

    protected int x, y, width, height;

    public UIObject(int x, int y, int width, int height, UIComponent... components){
        this.setPosition(x, y).setSize(width, height);
        this.componentMap = new TreeMap<>();
        this.componentList = new ArrayList<>();

        addComponents(components);
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
        for(UIComponent component : componentList) component.draw(canvas, x, y, width, height);
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
     * Sets the size of the UIObject then draws the UIObject
     * @param canvas The {@link GameCanvas} on which to draw the UIObject
     */
    public final void draw(GameCanvas canvas, Dimension d){
        setSize(d);
        draw(canvas);
    }

    /**
     * Sets the position and size of the UIObject then draws the UIObject
     * @param canvas The {@link GameCanvas} on which to draw the UIObject
     */
    public final void draw(GameCanvas canvas, int x, int y, int width, int height){
        setSize(width, height);
        setPosition(x, y);
        draw(canvas);
    }


    /**
     * Sets the position and size of the UIObject then draws the UIObject
     * @param canvas The {@link GameCanvas} on which to draw the UIObject
     */
    public final void draw(GameCanvas canvas, int x, int y, Dimension d){
        setSize(d);
        setPosition(x, y);
        draw(canvas);
    }
    
    public final UIObject addComponent(UIComponent component){
        componentList.add(component);
        componentMap.put(component.getClass().getName(), component);
        return this;
    }

    public final UIObject addComponents(UIComponent... components){
        for(UIComponent component : components){
            addComponent(component);
        }
        return this;
    }

    /**
     * @noinspection unchecked
     */
    public final <Type extends UIComponent> Type getComponent(Class<Type> type){
        return (Type) componentMap.get(type.getName());
    }
}