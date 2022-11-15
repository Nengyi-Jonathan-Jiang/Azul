package engine.input;

import engine.core.Vec2;

public class MouseEvent {
    public final Vec2 position;
    public final MouseButton button;

    public enum MouseButton{
        LEFT, RIGHT, MIDDLE, NONE
    }

    public MouseEvent(Vec2 position, MouseButton button){
        this.position = position;
        this.button = button;
    }
}
