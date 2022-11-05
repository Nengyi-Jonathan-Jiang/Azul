package Game.Frontend;

import Engine.Components.RectRendererComponent;
import Engine.Core.GameObject;
import Engine.Core.Vec2;
import Game.Backend.PatternLine;
import Game.Backend.Tile;

import java.awt.Color;

public class PatternLineGameObject extends GameObject {
    public PatternLineGameObject(int size){
        super(new Vec2(size * PatternLine.TILE_SPACING, Tile.SIZE));
    }

    @Override
    public GameObject addChild(GameObject object) {
        return super.addChild(object.setTopRight(new Vec2(-PatternLine.TILE_SPACING * children.size(), 0).plus(getTopRightOffset())));
    }
}
