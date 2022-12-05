package game;

import engine.core.AbstractScene;
import engine.core.GameCanvas;
import engine.core.SceneManager;
import engine.util.ImageLoader;
import game.scenes.TitleScene;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.Scanner;

public class App extends JFrame {
    public static GameCanvas canvas;

    public App() {
        super();
        setTitle("Azul");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ImageLoader.load("logo", "logo.png");
        ImageLoader.load("board", "board.jpg");
        ImageLoader.load("factory", "factory.png");
        for(String color : "red orange blue black snow first".split(" "))
            ImageLoader.load(color, "Tiles/" + color + ".png");

        loadTheme("Classic");

        canvas = new GameCanvas(1000, 600);
        add(canvas);
        pack();

        setMinimumSize(getSize());

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        SceneManager.run(new AbstractScene() {
            @Override
            public Iterator<? extends AbstractScene> getScenesBefore() {
                return AbstractScene.makeLoopIterator(TitleScene::new, () -> true);
            }
        }, canvas);

        setVisible(true);
    }

    public static void loadTheme(String theme){
        ImageLoader.load("background", "Themes/" + theme + "/background.jpg");

        // Read style
        Scanner scan = new Scanner(App.class.getResourceAsStream("/Themes/" + theme + "/style.txt"));
        scan.next();scan.next();
        Style.FG_COLOR.set(new Color(scan.nextInt(), scan.nextInt(), scan.nextInt()));
        scan.next();scan.next();
        Style.BG_COLOR.set(new Color(scan.nextInt(), scan.nextInt(), scan.nextInt()));
        scan.next();scan.next();
        Style.DM_COLOR.set(new Color(scan.nextInt(), scan.nextInt(), scan.nextInt()));
        scan.next();scan.next();
        Style.HL_COLOR.set(new Color(scan.nextInt(), scan.nextInt(), scan.nextInt()));
        scan.next();scan.next();
        Style.HL2_COLOR.set(new Color(scan.nextInt(), scan.nextInt(), scan.nextInt()));
    }
}