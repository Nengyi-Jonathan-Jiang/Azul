package Engine.Core;

import java.awt.event.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.*;

/**
 * A "scene" in an app.
 */
public abstract class Scene {
    /**
     * @return An {@code Iterator} of {@code Actions} that should be run before this actions executes, may return an
     * indefinite number of Actions. This is queried immediately after the {@link Scene#onSchedule()} method is called
     */
    public Iterator<? extends Scene> getScenesBefore(){return null;}
    
    /**
     * @return An {@code Iterator} of {@code Action}s that should be run after this actions executes, may
     * return an indefinite number of Actions. This is queried after the {@link Scene#onExecutionEnd} method is called
     */
    public Iterator<? extends Scene> getScenesAfter(){return null;}
    
    /**
     * Called whenever the scene is active and the mouse is clicked. Guaranteed to be called before update is called
     * @param me The mouse event
     *
     */
    public void onMouseClick(MouseEvent me){}

    /**
     * Called whenever the scene is active and a key is pressed. Guaranteed to be called before update is called
     * @param ke The key event
     *
     */
    public void onKeyPress(KeyEvent ke){}
    
    /**
     * Called every frame after event listeners and {@link Scene#onKeyPress} methods are called. This
     * method should handle any logic that does not belong in event listeners or {@link Scene#draw}
     * methods
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
    public void onSchedule(){}

    /**
     * Called when all of the pre-actions are finished executing
     */
    public void onExecutionStart(){}

    /**
     * Called as soon as the Action finishes executing
     */
    public void onExecutionEnd(){}

    /**
     * @return Whether the Action has finished executing
     */
    public boolean isFinished(){return true;}

    /**
     * Makes an iterator through the scenes provided
     */
    public static Iterator<Scene> makeIterator(Scene... scenes){
        return Arrays.stream(scenes).iterator();
    }

    /**
     * Make a while-loop-like scene iterator
     * @param supplier A function that returns a Scene, is called repeatedly in the loop
     * @param condition Returns true as long as the loop should execute
     */
    public static Iterator<Scene> makeLoopIterator(Supplier<Scene> supplier, Supplier<Boolean> condition){
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return condition.get();
            }

            @Override
            public Scene next() {
                return supplier.get();
            }
        };
    }
}
