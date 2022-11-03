package Engine.Core;

import Engine.Components.Component;

import java.awt.Dimension;
import java.util.*;

public class GameObject {
    private final Map<String, Component> componentMap;
    private final List<Component> componentList;

    protected Point2D position;
    protected Dimension size;
    protected GameObject parent;
    protected final List<GameObject> children;


    public GameObject(Point2D position, Dimension size, Component... components){
        this.children = new ArrayList<>();

        this.setRelativePosition(position).setSize(size);
        this.componentMap = new TreeMap<>();
        this.componentList = new ArrayList<>();

        addComponents(components);
    }

    public final Point2D getAbsolutePosition(){
        if(parent == null){
            return getRelativePosition();
        }
        else{
            return parent.getAbsolutePosition().plus(position);
        }
    }

    /**
     * @return position of top left corner
     */
    public final Point2D getRelativePosition(){
        return position;
    }

    /**
     * Set the position of the UIObject
     * @param position position of top left corner
     */
    public final GameObject setRelativePosition(Point2D position){
        this.position = position;
        return this;
    }

    /**
     * @return the size of the UIObject in pixels
     */
    public final Dimension getSize(){
        return size;
    }

    /**
     * Set the size of the UIObject
     */
    public final GameObject setSize(Dimension size){
        this.size = size;
        return this;
    }

    /**
     * Draws the UIObject
     * @param canvas The {@link GameCanvas} on which to draw the UIObject
     */
    public final GameObject draw(GameCanvas canvas){
        for(Component component : componentList) component.drawAndUpdate(canvas, getAbsolutePosition(), getSize());

        for(GameObject child : children){
            child.draw(canvas);
        }

        return this;
    }
    
    public final GameObject addComponent(Component component){
        componentList.add(component);
        componentMap.put(component.getClass().getName(), component);
        return this;
    }

    public final GameObject addComponents(Component... components){
        for(Component component : components){
            addComponent(component);
        }
        return this;
    }

    /**
     * @noinspection unchecked
     */
    public final <Type extends Component> Type getComponent(Class<Type> type){
        return (Type) componentMap.get(type.getName());
    }


    private void setParent(GameObject object){
        parent = object;
    }

    public GameObject addChild(GameObject object){
        children.add(object);
        object.setParent(this);
        return this;
    }

    public List<GameObject> getChildren(){
        return children;
    }
}