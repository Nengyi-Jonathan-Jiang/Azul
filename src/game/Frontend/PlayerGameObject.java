package game.frontend;

import engine.components.ImageRendererComponent;
import engine.core.GameObject;
import engine.core.Vec2;

// For testing purposes
public class PlayerGameObject extends GameObject {
    private final GameObject boardObject;

    public PlayerGameObject(String playerName, int playerNum){
        boardObject = new GameObject(new ImageRendererComponent("Azul Board.jpg"));
        boardObject.setSize(boardObject.getComponent(ImageRendererComponent.class).getImageSize());

        GameObject textObject = new TextObject(playerName);
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