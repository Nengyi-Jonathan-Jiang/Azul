import engine.core.GameCanvas;
import game.App;

public class Main {
    public static GameCanvas canvas;

    /** @noinspection AccessStaticViaInstance*/
    public static void main(String[] args) {
        canvas = new App().canvas;
    }
}