package engine.components;

import engine.core.GameCanvas;
import engine.core.GameObject;

/**
 * An abstract UI Component class.
 * @noinspection EmptyMethod
 */
public abstract class Component {
    protected GameObject gameObject;
    private boolean disabled = false;

    /**
     * Draws the UIComponent
     * @param canvas The {@link GameCanvas} on which to draw the UIComponent
     */
    public abstract void drawAndUpdate(GameCanvas canvas);

    /**
     * Should not be called by client code
     * Sets the parent game object of this component
     */
    public final void setGameObject(GameObject gameObject){
        this.gameObject = gameObject;
    }

    /**
     * Whether the Component is disabled
     */
    public final boolean isEnabled(){
        return !disabled;
    }

    protected void onDisable(){}
    protected void onEnable(){}

    /**
     * Enable the component
     */
    public final void enable(){
        disabled = false;
        onEnable();
    }

    /**
     * Disable the component
     */
    public final void disable(){
        disabled = true;
        onDisable();
    }
}