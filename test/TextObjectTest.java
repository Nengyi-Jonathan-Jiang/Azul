import engine.core.AbstractScene;
import engine.core.GameCanvas;
import engine.core.SceneManager;
import engine.core.Vec2;
import game.frontend.TextObject;

import javax.swing.*;
import java.awt.*;

public class TextObjectTest extends JFrame {
    public TextObjectTest(){

        GameCanvas canvas = new GameCanvas(200, 200);
        canvas.setPreferredSize(new Dimension(200, 200));
        add(canvas);
        pack();

        SceneManager.run(new AbstractScene() {
            private TextObject TL, TR, BL, BR;
            @Override
            public void onExecutionStart() {
                TL = new TextObject("TL");
                TR = new TextObject("TR");
                BL = new TextObject("BL");
                BR = new TextObject("BR");

                TL.setTopLeft(new Vec2(0, 0));
                TR.setTopRight(new Vec2(200, 0));
                BL.setBottomLeft(new Vec2(0, 200));
                BR.setBottomRight(new Vec2(200, 200));
            }

            @Override
            public void draw(GameCanvas canvas) {
                TL.draw(canvas);
                TR.draw(canvas);
                BL.draw(canvas);
                BR.draw(canvas);
            }

            @Override
            public boolean isFinished() {
                return false;
            }
        }, canvas);

        setVisible(true);
    }

    public static void main(String[] args) {
        new TextObjectTest();
    }
}
