package Game.Frontend;

// Weird class name :D

import Engine.Components.ImageRendererComponent;
import Engine.Core.GameObject;
import Engine.Core.Vec2;
import Game.App;
import Game.Backend.Game;
import Game.Backend.Player;

public class GameGameObject extends GameObject {
    private Game game;
    public GameGameObject(Game game){
        super(new ImageRendererComponent("table.jpg"));

        this.game = game;

        setSize(getComponent(ImageRendererComponent.class).getImageSize());

        for(int i = 0; i < game.getPlayers().size(); i++){
            GameObject o = game.getPlayers().get(i).getGameObject();
            addChild(o);
            o.setPosition(new Vec2(500).scaledBy(
                ((i & 1) * 2 - 1),
                ((i & 2) - 1)
            ));
        }
    }
}
