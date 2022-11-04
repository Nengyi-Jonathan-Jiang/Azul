package Engine.Components;

import Engine.Core.GameCanvas;
import Engine.Core.GameObject;

/**
 * An abstract UI Component class.
 */
public abstract class Component {
    protected GameObject gameObject;

    /**
     * Draws the UIComponent
     * @param canvas The {@link GameCanvas} on which to draw the UIComponent
     */
    public abstract void drawAndUpdate(GameCanvas canvas);

    /**
     * Should not be called by client code
     * Sets the parent game object of this component
     * @param gameObject
     */
    public final void setGameObject(GameObject gameObject){
        this.gameObject = gameObject;
    }
}