package game.frontend;

import engine.components.ButtonComponent;
import engine.components.RectRendererComponent;
import engine.core.GameObject;
import engine.core.Vec2;
import game.Style;
import game.backend.player.PatternLine;
import game.backend.Tile;

public class PatternLineGameObject extends GameObject {
    public PatternLineGameObject(int row) {
        super(
                new Vec2(
                        (row + 1) * PatternLine.TILE_SPACING,
                        Tile.SIZE
                ),
                new RectRendererComponent(Style.HL_COLOR),
                new ButtonComponent()
        );
        getComponent(RectRendererComponent.class).disable();
    }

    @Override
    public GameObject addChild(GameObject object) {
        return super.addChild(object.setTopRight(new Vec2(-PatternLine.TILE_SPACING * children.size(), 0).plus(getTopRightOffset())));
    }
}
