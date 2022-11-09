package Game.Frontend;

// Weird class name :D

import Engine.Components.ImageRendererComponent;
import Engine.Components.PositionAnimationComponent;
import Engine.Core.GameObject;
import Engine.Core.Vec2;
import Game.Backend.Game;

public class GameGameObject extends GameObject {
    private Game game;
    public GameGameObject(Game game){
        super(new ImageRendererComponent("table.jpg"), new PositionAnimationComponent());

        this.game = game;

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
