package Engine.Core;

import java.util.Set;
import java.util.TreeSet;

public class Input {
    static Vec2 mousePosition = Vec2.zero;
    static boolean mouseDownR = false, mouseDownL = false;
    static Set<Character> keysDown = new TreeSet<>();

    public static Vec2 getMousePosition(){
        return mousePosition;
    }

    public static boolean isMouseLeftDown(){
        return mouseDownL;
    }

    public static boolean isMouseRightDown(){
        return mouseDownR;
    }

    public static boolean isKeyDown(Character key){
        return keysDown.contains(key);
    }
}
