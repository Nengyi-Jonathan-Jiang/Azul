package Game.Frontend;

import Engine.Components.RectRendererComponent;
import Engine.Core.GameObject;
import Engine.Core.Vec2;
import Game.Backend.FloorLine;

import java.awt.Color;

public class FloorLineGameObject extends GameObject {
    public FloorLineGameObject(){
        super(new Vec2(7 * FloorLine.TILE_SPACING, 40));
    }

    @Override
    public GameObject addChild(GameObject object) {
        return super.addChild(object.setTopLeft(new Vec2(FloorLine.TILE_SPACING * children.size(), 0).plus(getTopLeftOffset())));
    }
}
