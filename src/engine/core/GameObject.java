package engine.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @noinspection UnusedReturnValue, unused
 */
public class GameObject {
    protected final Map<String, Component> componentMap;
    protected final List<Component> componentList;
    protected final List<GameObject> children;
    protected Vec2 position, size;
    protected GameObject parent;

    protected boolean enabled = true;

    public GameObject(Component... components) {
        this(Vec2.zero, components);
    }

    public GameObject(Vec2 size, Component... components) {
        this(Vec2.zero, size, components);
    }

    public GameObject(Vec2 position, Vec2 size, Component... components) {
        this.children = new ArrayList<>();

        this.setPosition(position).setSize(size);
        this.componentMap = new TreeMap<>();
        this.componentList = new ArrayList<>();

        addComponents(components);
    }

    /**
     * @return position relative to top left corner of screen
     */
    public final Vec2 getAbsolutePosition() {
        if (parent == null) {
            return getPosition();
        } else {
            return parent.getAbsolutePosition().plus(position);
        }
    }

    /**
     * Sets the absolute position of the GameObject
     */
    public final GameObject setAbsolutePosition(Vec2 position) {
        return setPosition(position.minus(parent == null ? Vec2.zero : parent.getAbsolutePosition()));
    }

    /**
     * @return position of top left corner relative to the top left corner of the screen
     */
    public final Vec2 getAbsoluteTopLeft() {
        return getAbsolutePosition().plus(getTopLeftOffset());
    }

    /**
     * @return position of top right corner relative to the top left corner of the screen
     */
    public final Vec2 getAbsoluteTopRight() {
        return getAbsolutePosition().plus(getTopRightOffset());
    }

    /**
     * @return position of bottom left corner relative to the top left corner of the screen
     */
    public final Vec2 getAbsoluteBottomLeft() {
        return getAbsolutePosition().plus(getBottomLeftOffset());
    }

    /**
     * @return position of bottom left corner relative to the top left corner of the screen
     */
    public final Vec2 getAbsoluteBottomRight() {
        return getAbsolutePosition().plus(getBottomRightOffset());
    }

    /**
     * @return position relative to the parent gameObject
     */
    public final Vec2 getPosition() {
        return position;
    }

    /**
     * Set the position relative to the parent gameObject
     */
    public final GameObject setPosition(Vec2 position) {
        this.position = position;
        return this;
    }

    /**
     * @return position of top left corner relative to the parent gameObject
     */
    public final Vec2 getTopLeft() {
        return position.plus(getTopLeftOffset());
    }

    /**
     * Set the top left corner relative to the parent gameObject
     */
    public final GameObject setTopLeft(Vec2 position) {
        return setPosition(position.minus(getTopLeftOffset()));
    }

    /**
     * @return position of top right corner relative to the parent gameObject
     */
    public final Vec2 getTopRight() {
        return position.plus(getTopRightOffset());
    }

    /**
     * Set the top right corner relative to the parent gameObject
     */
    public final GameObject setTopRight(Vec2 position) {
        return setPosition(position.minus(getTopRightOffset()));
    }

    /**
     * @return position of bottom left corner relative to the parent gameObject
     */
    public final Vec2 getBottomLeft() {
        return position.plus(getBottomLeftOffset());
    }

    /**
     * Set the bottom left corner relative to the parent gameObject
     */
    public final GameObject setBottomLeft(Vec2 position) {
        return setPosition(position.minus(getBottomLeftOffset()));
    }

    /**
     * @return position of bottom left corner relative to the parent gameObject
     */
    public final Vec2 getBottomRight() {
        return position.plus(getBottomRightOffset());
    }

    /**
     * Set the bottom right corner relative to the parent gameObject
     */
    public final GameObject setBottomRight(Vec2 position) {
        return setPosition(position.minus(getBottomRightOffset()));
    }

    /**
     * Get offset of top left corner
     */
    public final Vec2 getTopLeftOffset() {
        return size.scaledBy(-.5, -.5);
    }

    /**
     * Get offset of top right corner
     */
    public final Vec2 getTopRightOffset() {
        return size.scaledBy(.5, -.5);
    }

    /**
     * Get offset of bottom left corner
     */
    public final Vec2 getBottomLeftOffset() {
        return size.scaledBy(-.5, .5);
    }

    /**
     * Get offset of bottom right corner
     */
    public final Vec2 getBottomRightOffset() {
        return size.scaledBy(.5, .5);
    }

    /**
     * Adds the given {@link Vec2} to the position
     */
    public final GameObject move(Vec2 delta) {
        return setPosition(position.plus(delta));
    }

    /**
     * @return the size of the GameObject in pixels
     */
    public final Vec2 getSize() {
        return size;
    }

    /**
     * Set the size of the GameObject
     */
    public final GameObject setSize(Vec2 size) {
        this.size = size;
        return this;
    }

    /**
     * Draws the GameObject
     *
     * @param canvas The {@link GameCanvas} on which to draw the GameObject
     */
    public GameObject draw(GameCanvas canvas) {
        if(!enabled) return this;

        for (Component component : componentList) {
            if (component.isEnabled()) {
                component.drawAndUpdate(canvas);
            }
        }

        for (GameObject child : children) {
            child.draw(canvas);
        }

        return this;
    }

    public final GameObject addComponent(Component component) {
        componentList.add(component);
        componentMap.put(component.getClass().getName(), component);
        component.setGameObject(this);
        return this;
    }

    public final GameObject addComponents(Component... components) {
        for (Component component : components) {
            addComponent(component);
        }
        return this;
    }

    /**
     * @noinspection unchecked
     */
    public final <Type extends Component> Type getComponent(Class<Type> type) {
        return (Type) componentMap.get(type.getName());
    }

    public void removeFromParent() {
        setParent(null);
    }

    public GameObject addChild(GameObject object) {
        object.setParent(this);
        children.add(object);
        return this;
    }

    public final GameObject addChildren(GameObject... children) {
        for (GameObject child : children) {
            addChild(child);
        }
        return this;
    }

    public final List<GameObject> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "\n" + getClass().getName() + "{\n  Position:"
                + getPosition()
                + "\n  Components : ["
                + componentList.stream().map(i -> i.getClass().getName()).collect(Collectors.joining(", "))
                + "]\n  Children: [" + (
                children.size() == 0 ? "" :
                        children.stream().map(
                                i -> "    " + i.toString().replaceAll("\n", "\n    ")
                        ).collect(Collectors.joining("")) + "\n  "
        )
                + "]\n}";
    }

    public final GameObject getParent() {
        return parent;
    }

    private void setParent(GameObject object) {
        if (parent != null) parent.children.remove(this);
        parent = object;
    }

    public void enable() {
        this.enabled = true;
    }
    public void disable() {
        this.enabled = false;
    }
}