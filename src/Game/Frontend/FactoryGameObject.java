package Game.Frontend;

import Engine.Components.ImageRendererComponent;
import Engine.Core.GameObject;
import Engine.Core.Vec2;

public class FactoryGameObject extends GameObject {
    public FactoryGameObject(){
        super(new Vec2(144), new ImageRendererComponent("factory.png"));
    }
}
