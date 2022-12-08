package game.frontend;

// Weird class name :D

import engine.components.ImageRendererComponent;
import engine.components.PositionAnimationComponent;
import engine.core.Component;
import engine.core.GameCanvas;
import engine.core.GameObject;
import engine.core.Vec2;
import game.backend.Game;

public class GameGameObject extends GameObject {
    public GameGameObject(Game game) {
        super(
                new ImageRendererComponent("background", ImageRendererComponent.RenderSpeed.FAST),
                new PositionAnimationComponent()
        );

        resize(new Vec2(4200, 2240));

        for (int i = 0; i < game.getPlayers().size(); i++) {
            GameObject o = game.getPlayers().get(i).getGameObject();
            addChild(o);
            o.setPosition(new Vec2(
                    (((i + 1) & 2) - 1) * 600,
                    ((i & 2) - 1) * 280
            ));
        }
    }

    @Override
    public GameObject draw(GameCanvas canvas) {

        if(!enabled) return this;

        for (Component component : componentList) {
            if (component.isEnabled()) {
                if(component instanceof ImageRendererComponent){
                    ImageRendererComponent i = (ImageRendererComponent) component;
                    Vec2 p = position, s = size;
                    position = position.scaledBy(
                            (canvas.get_size().x - i.getImageSize().x) /
							(canvas.get_size().x - 4200.)
                    );
                    size = i.getImageSize();
                    component.drawAndUpdate(canvas);
                    position = p;
                    size = s;
                }
                else
                    component.drawAndUpdate(canvas);
            }
        }

        for (GameObject child : children) {
            child.draw(canvas);
        }

        return this;
    }
}
