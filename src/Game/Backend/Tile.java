package Game.Backend;
import Engine.Components.ImageRendererComponent;
import Engine.Core.GameObject;
import Engine.Core.Vec2;

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
        gameObj = new GameObject(new ImageRendererComponent("Tile " + getTileColorName(color) + ".png"));
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
}