package engine.core;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import engine.input.MouseEvent;

/**
 * A class that runs an {@link AbstractScene} in an event loop. An action may schedule <i>pre-actions</i> to be run
 * before the action executes and <i>post-actions</i> to be run after the action executes. Post-actions are queried
 * after the action finishes execution.
 */
public class SceneManager {

    public static void run(AbstractScene a, GameCanvas component){run(a, component, 16);}
    public static void run(AbstractScene a, GameCanvas component, int delay){new SceneManager(a, delay, component);}

    protected final Deque<Iterator<? extends AbstractScene>> scheduleStack;
    protected final Deque<AbstractScene> sceneStack;

    protected final Queue<MouseEvent> mouseEvents = new ArrayDeque<>();
    protected final Queue<KeyEvent> keyEvents = new ArrayDeque<>();

    protected final GameCanvas canvas;

    protected final int delay;

    protected AbstractScene lastExecutedScene;

    protected SceneManager(AbstractScene a, int delay, GameCanvas canvas){
        sceneStack = new ArrayDeque<>();
        scheduleStack = new ArrayDeque<>();

        this.delay = delay;

        this.canvas = canvas;

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                MouseEvent.MouseButton button = MouseEvent.MouseButton.NONE;

                if(SwingUtilities.isLeftMouseButton(e)){
                    button = MouseEvent.MouseButton.LEFT;
                    Input.mouseDownL = true;
                }
                if(SwingUtilities.isRightMouseButton(e)){
                    button = MouseEvent.MouseButton.RIGHT;
                    Input.mouseDownR = true;
                }
                Vec2 pos = new Vec2(e.getPoint().x, e.getPoint().y);
                mouseEvents.add(new MouseEvent(pos, button));
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e)) Input.mouseDownL = false;
                if(SwingUtilities.isRightMouseButton(e)) Input.mouseDownR = false;
            }
        });

        SwingUtilities.getWindowAncestor(canvas).addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                Input.keysDown.add(e.getKeyChar());
                keyEvents.add(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                Input.keysDown.remove(e.getKeyChar());
            }
        });

        scheduleAction(new AbstractScene(){
            @Override public Iterator<AbstractScene> getScenesBefore() {
                return Collections.singletonList(a).iterator();
            }
        });

        run();
    }

    protected void run(){
        new Thread(() -> {
            while(true){
                if(sceneStack.isEmpty()) break;

                AbstractScene currentScene = sceneStack.peekFirst();
                checkExecutionStart(currentScene);

                if(currentScene.isFinished()){
                    // Finish this action
                    currentScene.onExecutionEnd();
                    // Remove action from stack
                    sceneStack.pop();

                    Iterator<? extends AbstractScene> scenesAfter = currentScene.getScenesAfter();
                    if(scenesAfter != null && scenesAfter.hasNext()){
                        scheduleAction(new AbstractScene() {
                            @Override
                            public Iterator<? extends AbstractScene> getScenesBefore() {
                                return scenesAfter;
                            }
                        });
                    }
                    else {
                        // No more actions to execute, quit the event loop
                        if (scheduleStack.isEmpty()) break;
                            // Get the next action
                        else scheduleNextAction();
                    }
                }
                else{
                    executeAction(currentScene);

                    try{
                        //noinspection BusyWait
                        Thread.sleep(delay);
                    }
                    catch(Exception e){/*Nothing*/}
                }
            }
        }).start();
    }

    protected void scheduleAction(AbstractScene s){

        System.out.println("Scheduled scene " + s.getClass().getName());

        sceneStack.push(s);

        s.onSchedule();

        Iterator<? extends AbstractScene> scenesBefore = s.getScenesBefore();
        if(scenesBefore != null && scenesBefore.hasNext()) {
            scheduleStack.push(scenesBefore);
            scheduleAction(scenesBefore.next());
        }
    }

    protected void scheduleNextAction(){
        if(scheduleStack.isEmpty() || sceneStack.isEmpty()) throw new Error("Pop off schedule stack when empty");   //hopefully never happens

        Iterator<? extends AbstractScene> schedule = scheduleStack.peekFirst();

        if(schedule != null && schedule.hasNext()){
            AbstractScene nextScene = schedule.next();
            scheduleAction(nextScene);
        }
        else{
            scheduleStack.pop();
        }
    }

    protected void checkExecutionStart(AbstractScene scene){
        if(scene != lastExecutedScene){
            scene.onExecutionStart();
            lastExecutedScene = scene;
        }
    }

    protected void executeAction(AbstractScene scene){
        Point mousePosition = canvas.getMousePosition(true);
        if(mousePosition != null) {
            Input.mousePosition = new Vec2(mousePosition.x, mousePosition.y);
        }

        while(!mouseEvents.isEmpty()){
            scene.onMouseClick(mouseEvents.remove());
        }
        while(!keyEvents.isEmpty()){
            scene.onKeyPress(keyEvents.remove());
        }
        scene.update();
        canvas.repaint(scene);
    }
}
