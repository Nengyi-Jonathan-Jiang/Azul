package game.frontend;

import engine.components.ImageRendererComponent;
import engine.core.GameObject;
import engine.core.Vec2;

public class FactoryGameObject extends GameObject {
    public FactoryGameObject() {
        super(new Vec2(144), new ImageRendererComponent("factory.png"));
    }

    @Override
    public GameObject addChild(GameObject object) {
        super.addChild(object);

        object.setPosition(new Vec2(22).scaledBy(switch (children.size()) {
            case 1, 3 -> -1;
            case 2, 4 -> 1;
            default -> 0;
        }, switch (children.size()) {
            case 1, 2 -> -1;
            case 3, 4 -> 1;
            default -> 0;
        }));

        return this;
    }
}
