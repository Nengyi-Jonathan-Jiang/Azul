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

    protected final MouseEvent[] lastMouseEvent = new MouseEvent[1];
    protected final KeyEvent[] lastKeyEvent = new KeyEvent[1];

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
                lastMouseEvent[0] = e;
            }
        });

        SwingUtilities.getWindowAncestor(canvas).addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                lastKeyEvent[0] = e;
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

                Scene currentAction = actionStack.peekFirst();

                if(currentAction.isFinished()){
                    // Finish this action
                    currentAction.onFinish();
                    // Remove action from stack
                    actionStack.pop();

                    Iterator<? extends Scene> postActions = currentAction.getScenesAfter();
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
                    executeAction(currentAction);

                    try{Thread.sleep(delay);}
                    catch(Exception e){/*Nothing*/}
                }
            }
        }).start();
    }

    protected void scheduleAction(Scene a){

        actionStack.push(a);

        a.onStart();

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
            //noinspection ConstantConditions
            actionStack.peekFirst().onExecute();
        }
    }

    protected void executeAction(Scene action){
        canvas.repaint(action);
        action.processEvents(lastMouseEvent[0], lastKeyEvent[0]);
        lastMouseEvent[0] = null;
        lastKeyEvent[0] = null;
        action.update();
    }
}
