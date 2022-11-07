package Game.Frontend;

import Engine.Components.Component;
import Engine.Components.ImageRendererComponent;
import Engine.Core.GameCanvas;
import Engine.Core.GameObject;
import Engine.Core.Vec2;

import java.util.Collections;
import java.util.stream.Collectors;

public class TilePileGameObject extends GameObject {
    @Override
    public GameObject addChild(GameObject object) {
        super.addChild(object);

        double sqrt_t = Math.sqrt(children.size() - 1.);

        Vec2 angle = new Vec2(Math.sin(1.5 * Math.PI * sqrt_t), Math.cos(1.5 * Math.PI * sqrt_t));

        object.setPosition(angle.scaledBy(sqrt_t * 20));

        return this;
    }

    @Override
    public GameObject draw(GameCanvas canvas){
        for(Component component : componentList){
            if(component.isEnabled()) {
                component.drawAndUpdate(canvas);
            }
        }

        // Weird magic to reverse list without actually modifying the list
        for(GameObject child : children.stream().collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
            Collections.reverse(list);
            return list;
        }))){
            child.draw(canvas);
        }

        return this;
    }
}
