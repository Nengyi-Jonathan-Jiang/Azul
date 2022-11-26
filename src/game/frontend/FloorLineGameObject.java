package game.frontend;

import engine.components.ButtonComponent;
import engine.components.RectRendererComponent;
import engine.core.GameObject;
import engine.core.Vec2;
import game.backend.player.FloorLine;

public class FloorLineGameObject extends GameObject {
    public FloorLineGameObject() {
        super(new Vec2(7 * FloorLine.TILE_SPACING, 40), new RectRendererComponent(), new ButtonComponent());
    }

    @Override
    public GameObject addChild(GameObject object) {
        return super.addChild(object.setTopLeft(new Vec2(FloorLine.TILE_SPACING * children.size(), 0).plus(getTopLeftOffset())));
    }
}
