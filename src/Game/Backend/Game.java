package Game.Backend;

import Engine.Components.ImageRendererComponent;
import Engine.Core.GameObject;
import Engine.Core.Vec2;
import Game.App;
import Game.Frontend.PlayerGameObject;

public class Game {
    private final GameObject gObject;

    // Todo: add players argument, must wait for Player class
    public Game(){
        gObject = new GameObject(
                new Vec2(App.WIDTH / 2f, App.HEIGHT / 2f),
                Vec2.zero,
                new ImageRendererComponent("table.jpg")
        );
        gObject.setSize(gObject.getComponent(ImageRendererComponent.class).getImageSize());

        // Temporary, just testing the player board display and correctness of the gameObject hierarchy
        gObject.addChild(new PlayerGameObject("Player 1").setPosition(new Vec2(-App.WIDTH / 3., -App.HEIGHT / 3.)));
        gObject.addChild(new PlayerGameObject("Player 2").setPosition(new Vec2(App.WIDTH / 3., -App.HEIGHT / 3.)));
    }

    public GameObject getGameObject(){
        return gObject;
    }
}
