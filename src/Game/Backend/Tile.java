package Game.Backend;
import Engine.Components.ButtonComponent;
import Engine.Components.ImageRendererComponent;
import Engine.Components.PositionAnimationComponent;
import Engine.Components.RectRendererComponent;
import Engine.Core.GameObject;
import Engine.Core.Vec2;

import Game.Style;

public class Tile{
    public static final double SIZE = 40;

    public enum TileColor{
        RED,
        YELLOW,
        WHITE,
        BLUE,
        BLACK,
        NONE,
        FIRST_PLAYER
    }
    private final TileColor color;
    private final GameObject gameObj;

    public Tile(TileColor color){
        this.color = color;
        gameObj = new GameObject(
                new ImageRendererComponent("Tile " + getTileColorName(color) + ".png"),
                new ButtonComponent(),
                new RectRendererComponent(Style.HL_COLOR),
                new PositionAnimationComponent()
        );
        gameObj.getComponent(RectRendererComponent.class).disable();
        gameObj.setSize(new Vec2(SIZE));
    }

    public TileColor getColor(){
        return color;
    }

    public GameObject getGameObject(){
        return gameObj;
    }

    public static String getTileColorName(TileColor color){
        return switch(color){
            case RED -> "Red";
            case YELLOW -> "Yellow";
            case WHITE -> "White";
            case BLUE -> "Blue";
            case BLACK -> "Black";
            case NONE -> "None";
            case FIRST_PLAYER -> "First Player";
        };
    }

    public void highlight(){
        gameObj.getComponent(RectRendererComponent.class).enable();
    }

    public void unHighlight(){
        gameObj.getComponent(RectRendererComponent.class).disable();
    }
}
