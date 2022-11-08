package Engine.Core;

import java.awt.event.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.*;

/**
 * A "scene" in an app.
 */
public abstract class AbstractScene {
    /**
     * @return An {@code Iterator} of {@code Actions} that should be run before this actions executes, may return an
     * indefinite number of Actions. This is queried immediately after the {@link AbstractScene#onSchedule()} method is called
     */
    public Iterator<? extends AbstractScene> getScenesBefore(){return null;}
    
    /**
     * @return An {@code Iterator} of {@code Action}s that should be run after this actions executes, may
     * return an indefinite number of Actions. This is queried after the {@link AbstractScene#onExecutionEnd} method is called
     */
    public Iterator<? extends AbstractScene> getScenesAfter(){return null;}
    
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
     * Called every frame after event listeners and {@link AbstractScene#onKeyPress} methods are called. This
     * method should handle any logic that does not belong in event listeners or {@link AbstractScene#draw}
     * methods
     */
    public void update(){}
    
    /**
     * Called at the beginning of each frame after {@link AbstractScene#isFinished} is queried. This method should handle all
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
    public static Iterator<AbstractScene> makeIterator(AbstractScene... scenes){
        return Arrays.stream(scenes).iterator();
    }

    /**
     * Make a while-loop-like scene iterator
     * @param supplier A function that returns a Scene, is called repeatedly in the loop
     * @param condition Returns true as long as the loop should execute
     */
    public static Iterator<AbstractScene> makeLoopIterator(Supplier<AbstractScene> supplier, Supplier<Boolean> condition){
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return condition.get();
            }

            @Override
            public AbstractScene next() {
                return supplier.get();
            }
        };
    }

    /**
     * Make a foreach-loop-like scene iterator
     * @param lst The input list to iterate through
     * @param map How to convert the input into a Scene
     */
    public static <T> Iterator<AbstractScene> makeLoopIterator(List<T> lst, Function<T, AbstractScene> map){
        return lst.stream().map(map).iterator();
    }

    @SafeVarargs
    public static Iterator<AbstractScene> concatIterators(Iterator<? extends AbstractScene>... its){
        return new Iterator<>() {
            private final Iterator<Iterator<? extends AbstractScene>> ii = List.of(its).iterator();
            private Iterator<? extends AbstractScene> current = null;

            @Override
            public boolean hasNext() {
                while(current == null || !current.hasNext()){
                    if(!ii.hasNext()) return false;
                    current = ii.next();
                }
                return true;
            }

            @Override
            public AbstractScene next() {
                return current.next();
            }
        };
    }

    public static AbstractScene groupScenes(AbstractScene... scenes){
        return new AbstractScene() {
            @Override
            public Iterator<? extends AbstractScene> getScenesBefore() {
                return makeIterator(scenes);
            }
        };
    }
}
