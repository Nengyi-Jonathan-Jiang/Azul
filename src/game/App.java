package game;

import engine.core.AbstractScene;
import engine.core.GameCanvas;
import engine.core.SceneManager;
import game.scenes.RulesScene;
import game.scenes.TitleScene;

import javax.swing.*;
import java.util.Iterator;

public class App extends JFrame {
    public static GameCanvas canvas;

    public App() {
        super();
        setTitle("Azul");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        canvas = new GameCanvas(1000, 600);
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