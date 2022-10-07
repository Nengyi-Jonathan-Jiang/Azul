package Game.Scenes;

import Engine.Core.*;
import Engine.UI.*;
import Engine.UI.TextComponent;

import java.awt.*;

public class TestScene extends Scene {
    UIObject play_button;

    public TestScene(){
        play_button = new UIObject(150, 150, 100, 100);
        play_button.addComponent(new TextComponent("Play", new TextStyle(Color.RED, Color.WHITE)));
    }

    @Override
    public void draw(GameCanvas canvas) {
        play_button.draw(canvas);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
