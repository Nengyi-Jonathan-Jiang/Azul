package Game;

import Engine.Core.SceneManager;
import Engine.Core.Scene;
import Engine.Core.GameCanvas;
import Game.Scenes.TestScene;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class App extends JFrame {
    public App(){
        super();
        setTitle("Azul");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(1600, 1000));

        GameCanvas canvas = new GameCanvas();

        add(canvas);

        SceneManager.run(new Scene() {
            @Override
            public Iterator<? extends Scene> getPreActions() {
                return new Iterator<>() {
                    @Override
                    public boolean hasNext() {
                        return true;
                    }

                    @Override
                    public Scene next() {
                        return new TestScene();
                    }
                };
            }
        }, canvas);

        setVisible(true);
    }
}
