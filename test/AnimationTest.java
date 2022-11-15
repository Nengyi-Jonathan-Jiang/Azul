import engine.animation.Animation;
import engine.components.AnimationRendererComponent;
import engine.core.*;
import game.frontend.TextObject;

import javax.swing.*;
import java.awt.*;

public class AnimationTest extends JFrame {
    public AnimationTest(){

        GameCanvas canvas = new GameCanvas();
        canvas.setPreferredSize(new Dimension(200, 200));
        add(canvas);
        pack();

        SceneManager.run(new AbstractScene() {
            private GameObject object;
            @Override
            public void onExecutionStart() {
                object = new GameObject(
                    new Vec2(30, 30),
                    new Vec2(40, 40),
                    new AnimationRendererComponent(
                        Animation.getAnimationFromFile("Animations/TestAnimation.animation")
                    )
                );

                object.getComponent(AnimationRendererComponent.class).start(true);
            }

            @Override
            public void draw(GameCanvas canvas) {
                object.draw(canvas);
            }

            @Override
            public boolean isFinished() {
                return false;
            }
        }, canvas);

        setVisible(true);
    }

    public static void main(String[] args) {
        new AnimationTest();
    }
}
