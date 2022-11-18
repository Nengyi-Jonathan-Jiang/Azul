package game.backend;

import engine.core.GameObject;
import engine.core.Vec2;
import game.frontend.PatternLinesGameObject;

import java.util.List;

public class PatternLines {
    public static final double TILE_SPACING = 45.5;

    private final PatternLine[] lines;
    private final GameObject gameObject;

    public PatternLines() {
        lines = new PatternLine[5];

        for (int i = 0; i < 5; i++) {
            lines[i] = new PatternLine(i);
        }

        gameObject = new PatternLinesGameObject();

        for (int i = 0; i < 5; i++) {
            gameObject.addChild(lines[i].getGameObject());
            lines[i].getGameObject().setTopRight(new Vec2(0, i * TILE_SPACING).plus(gameObject.getTopRightOffset()));
        }
    }

    public PatternLine getLine(int row) {
        return lines[row];
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public List<PatternLine> getLines() {
        return List.of(lines);
    }
}
