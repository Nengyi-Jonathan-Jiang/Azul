package Game.Frontend;

import Engine.Core.GameObject;
import Engine.Core.Vec2;
import Game.Backend.PatternLines;

public class PatternLinesGameObject extends GameObject {
    public PatternLinesGameObject(){
        super(new Vec2(5 * PatternLines.TILE_SPACING));
    }
}
