package Game.Scenes;

import Engine.Core.*;
import Engine.Components.*;
import Engine.Components.LabelComponent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class TestScene extends Scene {
    GameObject play_button;

    public TestScene(){
        play_button = new GameObject(150, 150, 100, 100,
                new ImageComponent("Img.png"),
                new LabelComponent("Play", new TextStyle(Color.RED, new Color(127, 127, 127, 127))),
                new ButtonComponent()
        );
    }

    @Override
    public void processEvents(MouseEvent me, KeyEvent ke) {
        if(me != null && play_button.getComponent(ButtonComponent.class).contains(me)){
            System.out.println("Click");
        }
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
