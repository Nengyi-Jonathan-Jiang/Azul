package game.frontend;

import engine.core.GameObject;
import engine.core.Vec2;
import game.backend.player.PatternLines;

public class PatternLinesGameObject extends GameObject {
    public PatternLinesGameObject() {
        super(new Vec2(5 * PatternLines.TILE_SPACING));
    }
}
