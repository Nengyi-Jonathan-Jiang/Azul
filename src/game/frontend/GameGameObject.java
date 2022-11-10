package game.frontend;

// Weird class name :D

import engine.components.ImageRendererComponent;
import engine.components.PositionAnimationComponent;
import engine.core.GameObject;
import engine.core.Vec2;
import game.backend.Game;

public class GameGameObject extends GameObject {
    public GameGameObject(Game game){
        super(new ImageRendererComponent("table.jpg"), new PositionAnimationComponent());

        setSize(getComponent(ImageRendererComponent.class).getImageSize());

        for(int i = 0; i < game.getPlayers().size(); i++){
            GameObject o = game.getPlayers().get(i).getGameObject();
            addChild(o);
            o.setPosition(new Vec2(
                (((i + 1) & 2) - 1) * 600,
                ((i & 2) - 1) * 280
            ));
        }
    }
}
