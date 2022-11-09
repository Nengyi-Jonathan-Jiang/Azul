package Game;

import Engine.Core.SceneManager;
import Engine.Core.AbstractScene;
import Engine.Core.GameCanvas;
import Game.Scenes.TitleScene;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class App extends JFrame {
    public static final int WIDTH = 1800;
    public static final int HEIGHT = 1000;

    public App(){
        super();
        setTitle("Azul");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        GameCanvas canvas = new GameCanvas();
        canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        add(canvas);
        pack();

        SceneManager.run(new AbstractScene() {
            @Override
            public Iterator<? extends AbstractScene> getScenesBefore() {
                return AbstractScene.makeLoopIterator(TitleScene::new, () -> true);
            }
        }, canvas);

        setVisible(true);
    }
}
