import Engine.Core.AbstractScene;
import Engine.Core.GameCanvas;
import Engine.Core.SceneManager;

import javax.swing.*;
import java.util.Iterator;

public class SceneManagerTest extends JFrame {
    public SceneManagerTest(){

        GameCanvas canvas = new GameCanvas();
        add(canvas);

        SceneManager.run(new AbstractScene() {
            @Override
            public void onExecutionStart() {
                System.out.println("OnExecutionStart");
            }

            private int i = 0;

            @Override
            public void update() {
                System.out.println("update " + ++i);
            }

            @Override
            public void onExecutionEnd() {
                System.out.println("OnExecutionEnd");
            }

            @Override
            public boolean isFinished() {
                return i > 10;
            }

            @Override
            public Iterator<? extends AbstractScene> getScenesAfter() {
                return AbstractScene.makeIterator(new AbstractScene() {
                    @Override
                    public void onExecutionStart() {
                        System.out.println("~OnExecutionStart");
                    }

                    private int i = 0;

                    @Override
                    public void update() {
                        System.out.println("~update " + ++i);
                    }

                    @Override
                    public void onExecutionEnd() {
                        System.out.println("~OnExecutionEnd");
                    }

                    @Override
                    public boolean isFinished() {
                        return i > 10;
                    }
                });
            }
        }, canvas);
    }

    public static void main(String[] args){
        new SceneManagerTest();
    }
}