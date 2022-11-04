package Game.Frontend;

import Engine.Components.ImageRendererComponent;
import Engine.Components.RectRendererComponent;
import Engine.Components.TextRendererComponent;
import Engine.Components.TextStyle;
import Engine.Core.GameObject;
import Engine.Core.Vec2;
import Game.Style;

import java.awt.*;

// For testing purposes
public class PlayerGameObject extends GameObject {
    private GameObject textObject;
    private GameObject imageObject;

    private final float LABEL_HEIGHT = 30f;
    private final float LABEL_TEXT_SIZE = 20f;

    public PlayerGameObject(){
        imageObject = new GameObject(new ImageRendererComponent("Azul Board.jpg"));
        imageObject.setSize(imageObject.getComponent(ImageRendererComponent.class).getImageSize());

        textObject = new GameObject(
            new RectRendererComponent(Color.BLACK, Color.WHITE),
            new TextRendererComponent("Player 1",
                new TextStyle(Style.font_medium.deriveFont(LABEL_TEXT_SIZE), TextStyle.ALIGN_CENTER)
            )
        );

        textObject.setSize(new Vec2(imageObject.getSize().x, LABEL_HEIGHT));

        setSize(imageObject.getSize().plus(new Vec2(0, textObject.getSize().y)));
        imageObject.setTopLeft(getTopLeftOffset());
        textObject.setBottomLeft(getBottomLeftOffset());

        addChildren(imageObject, textObject);
    }
}