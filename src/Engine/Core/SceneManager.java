package Engine.Core;

import java.util.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * A class that runs an {@link Scene} in an event loop. An action may schedule <i>pre-actions</i> to be run
 * before the action executes and <i>post-actions</i> to be run after the action executes. Post-actions are queried
 * after the action finishes execution.
 */
public class SceneManager {

    public static void run(Scene a, GameCanvas component){run(a, component, 16);}
    public static void run(Scene a, GameCanvas component, int delay){new SceneManager(a, delay, component);}

    protected final Deque<Iterator<? extends Scene>> scheduleStack;
    protected final Deque<Scene> actionStack;

    protected final Queue<MouseEvent> mouseEvents = new ArrayDeque<>();
    protected final Queue<KeyEvent> keyEvents = new ArrayDeque<>();

    protected final GameCanvas canvas;

    protected final int delay;

    protected SceneManager(Scene a, int delay, GameCanvas canvas){
        actionStack = new ArrayDeque<>();
        scheduleStack = new ArrayDeque<>();

        this.delay = delay;

        this.canvas = canvas;

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseEvents.add(e);
            }
        });

        SwingUtilities.getWindowAncestor(canvas).addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keyEvents.add(e);
            }
        });

        scheduleAction(new Scene(){
            @Override public Iterator<Scene> getScenesBefore() {
                return Collections.singletonList(a).iterator();
            }
        });

        run();
    }

    protected void run(){
        new Thread(() -> {
            while(true){
                if(actionStack.isEmpty()) break;

                Scene currentScene = actionStack.peekFirst();

                if(currentScene.isFinished()){
                    // Finish this action
                    currentScene.onExecutionEnd();
                    // Remove action from stack
                    actionStack.pop();

                    Iterator<? extends Scene> postActions = currentScene.getScenesAfter();
                    if(postActions != null && postActions.hasNext()){
                        scheduleAction(new Scene() {
                            @Override public Iterator<? extends Scene> getScenesBefore() {
                                return postActions;
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

                    try{Thread.sleep(delay);}
                    catch(Exception e){/*Nothing*/}
                }
            }
        }).start();
    }

    protected void scheduleAction(Scene a){

        actionStack.push(a);

        a.onSchedule();

        Iterator<? extends Scene> preActions = a.getScenesBefore();
        if(preActions != null && preActions.hasNext()) {
            scheduleStack.push(preActions);
            scheduleAction(preActions.next());
        }
    }

    protected void scheduleNextAction(){
        if(scheduleStack.isEmpty() || actionStack.isEmpty()) throw new Error("Pop off schedule stack when empty");   //hopefully never happens

        Iterator<? extends Scene> schedule = scheduleStack.peekFirst();

        if(schedule != null && schedule.hasNext()){
            Scene nextAction = schedule.next();
            scheduleAction(nextAction);
        }
        else{
            scheduleStack.pop();
            actionStack.peekFirst().onExecutionStart();
        }
    }

    protected void executeAction(Scene action){
        canvas.repaint(action);
        while(!mouseEvents.isEmpty()){
            action.onMouseClick(mouseEvents.remove());
        }
        while(!keyEvents.isEmpty()){
            action.onKeyPress(keyEvents.remove());
        }
        action.update();
    }
}
