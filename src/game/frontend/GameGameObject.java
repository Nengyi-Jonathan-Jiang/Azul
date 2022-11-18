package game.frontend;

// Weird class name :D

import engine.components.ImageRendererComponent;
import engine.components.PositionAnimationComponent;
import engine.core.GameObject;
import engine.core.Vec2;
import game.backend.Game;

public class GameGameObject extends GameObject {
    public GameGameObject(Game game){
        super(new ImageRendererComponent("table.jpg", ImageRendererComponent.RenderSpeed.FAST), new PositionAnimationComponent());

        setSize(new Vec2(3000, 1600));

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
