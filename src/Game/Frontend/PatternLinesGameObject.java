package Game.Frontend;

import Engine.Components.RectRendererComponent;
import Engine.Core.GameObject;
import Engine.Core.Vec2;
import Game.Backend.PatternLine;
import Game.Backend.PatternLines;

import java.awt.*;

public class PatternLinesGameObject extends GameObject {
    public PatternLinesGameObject(){
        super(new Vec2(5 * PatternLines.TILE_SPACING));
    }
}
