package Game;

import Engine.Core.SceneManager;
import Engine.Core.Scene;
import Engine.Core.GameCanvas;
import Game.Scenes.TestScene;
import Game.Scenes.TitleScene;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class App extends JFrame {
    public static final int WIDTH = 1600;
    public static final int HEIGHT = 1000;

    public App(){
        super();
        setTitle("Azul");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(1600, 1000));

        GameCanvas canvas = new GameCanvas();

        add(canvas);

        SceneManager.run(new Scene() {
            @Override
            public Iterator<? extends Scene> getScenesBefore() {
                return Scene.makeLoopIterator(TitleScene::new, () -> true);
            }
        }, canvas);

        setVisible(true);
    }
}
