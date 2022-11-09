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
    private GameObject boardObject;

    private final float LABEL_HEIGHT = 30f;
    private final float LABEL_TEXT_SIZE = 20f;

    public PlayerGameObject(String playerName, int playerNum){
        boardObject = new GameObject(new ImageRendererComponent("Azul Board.jpg"));
        boardObject.setSize(boardObject.getComponent(ImageRendererComponent.class).getImageSize());

        textObject = new TextObject(playerName);
        textObject.setSize(new Vec2(boardObject.getSize().x, textObject.getSize().y));

        setSize(boardObject.getSize().plus(new Vec2(0, textObject.getSize().y)));

        if(playerNum < 2) {
            boardObject.setTopLeft(getTopLeftOffset());
            textObject.setBottomLeft(getBottomLeftOffset());
        }
        else{
            boardObject.setBottomLeft(getBottomLeftOffset());
            textObject.setTopLeft(getTopLeftOffset());
        }

        addChildren(boardObject, textObject);
    }

    public GameObject getBoardObject() {
        return boardObject;
    }
}