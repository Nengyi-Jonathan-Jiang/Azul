package Engine.Core;

import java.awt.event.*;
import java.util.Arrays;
import java.util.Iterator;

/**
 * A "scene" in an app.
 */
public abstract class Scene {
    /**
     * @return An {@code Iterator} of {@code Actions} that should be run before this actions executes, may return an
     * indefinite number of Actions. This is queried immediately after the {@link Scene#onStart()} method is called
     */
    public Iterator<? extends Scene> getPreActions(){return null;}
    
    /**
     * @return An {@code Iterator} of {@code Action}s that should be run after this actions executes, may
     * return an indefinite number of Actions. This is queried after the {@link Scene#onFinish} method is called
     */
    public Iterator<? extends Scene> getPostActions(){return null;}
    
    /**
     * Called every frame after the {@link Scene#draw} method is called. This method should  handle mouse and key clicks
     * @param me The last mouse click that occurred during the frame (can be null if no mouse click occurred)
     * @param ke The last key press that occurred during the frame (can be null if no key press occurred)
     */
    public void processEvents(MouseEvent me, KeyEvent ke){}
    
    /**
     * Called every frame after the {@link Scene#processEvents} method is called. This method should handle any logic
     * that does not belong in the {@link Scene#processEvents} or {@link Scene#draw} methods
     */
    public void update(){}
    
    /**
     * Called at the beginning of each frame after {@link Scene#isFinished} is queried. This method should handle all
     * drawing logic
     * @param canvas The {@link GameCanvas} on which to draw things
     */
    public void draw(GameCanvas canvas){}

    /**
     * Called as soon as the Action is scheduled. Any initialization up not done in the constructor should happen here.
     */
    public void onStart(){}

    /**
     * Called when all of the pre-actions are finished executing
     */
    public void onExecute(){}

    /**
     * Called as soon as the Action finishes executing
     */
    public void onFinish(){}

    /**
     * @return Whether the Action has finished executing
     */
    public boolean isFinished(){return true;}

    /**
     * Chains together multiple Actions
     * @param actions Action to chain together
     * @return A single Action formed by chaining together the actions passed in
     */
    public static Scene chain(Scene... actions){
        return new Scene() {
            @Override
            public Iterator<? extends Scene> getPreActions() {
                return Arrays.stream(actions).iterator();
            }
        };
    }
}
