package game.frontend;

import engine.components.ImageRendererComponent;
import engine.components.RectRendererComponent;
import engine.core.GameObject;
import engine.core.Vec2;
import game.Style;

import java.awt.*;

public class PlayerGameObject extends GameObject {
    private final GameObject boardObject;

    public PlayerGameObject(String playerName, int playerNum) {
        boardObject = new GameObject(
                new ImageRendererComponent(
                        "Azul Board.jpg",
                        ImageRendererComponent.RenderSpeed.FAST
                ),
                new RectRendererComponent(Style.FG_COLOR, new Color(0, 0, 0, 0))
        );
        boardObject.setSize(boardObject.getComponent(ImageRendererComponent.class).getImageSize());

        GameObject textObject = new TextObject(playerName);
        textObject.setSize(new Vec2(boardObject.getSize().x, textObject.getSize().y));

        setSize(boardObject.getSize().plus(new Vec2(0, textObject.getSize().y)));

        if (playerNum < 2) {
            boardObject.setTopLeft(getTopLeftOffset());
            textObject.setBottomLeft(getBottomLeftOffset());
        } else {
            boardObject.setBottomLeft(getBottomLeftOffset());
            textObject.setTopLeft(getTopLeftOffset());
        }

        addChildren(textObject, boardObject);
    }

    public GameObject getBoardObject() {
        return boardObject;
    }

    public void highlight(){
        children.forEach(c -> c.getComponent(RectRendererComponent.class).setBorderColor(Style.HL_COLOR));
    }

    public void unHighlight(){
        children.forEach(c -> c.getComponent(RectRendererComponent.class).setBorderColor(Style.FG_COLOR));
    }
}