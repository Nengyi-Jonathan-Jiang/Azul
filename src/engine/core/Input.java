package engine.core;

import java.util.Set;
import java.util.TreeSet;

/**
 * @noinspection unused
 */
public class Input {
    static final Set<Character> keysDown = new TreeSet<>();
    static Vec2 mousePosition = Vec2.zero;
    static boolean mouseDownR = false, mouseDownL = false;

    public static Vec2 getMousePosition() {
        return mousePosition;
    }

    public static boolean isMouseLeftDown() {
        return mouseDownL;
    }

    public static boolean isMouseRightDown() {
        return mouseDownR;
    }

    public static boolean isKeyDown(Character key) {
        return keysDown.contains(key);
    }
}
