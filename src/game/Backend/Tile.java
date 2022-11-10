package game.backend;
import engine.components.ButtonComponent;
import engine.components.ImageRendererComponent;
import engine.components.PositionAnimationComponent;
import engine.components.RectRendererComponent;
import engine.core.GameObject;
import engine.core.Vec2;

import game.Style;

public class Tile implements Comparable<Tile>{
    public static final double SIZE = 40;

    @Override
    public int compareTo(Tile o) {
        return color.compareTo(o.color);
    }

    public enum TileColor{
        FIRST_PLAYER,
        RED,
        YELLOW,
        WHITE,
        BLUE,
        BLACK,
        NONE
    }
    private final TileColor color;
    private final GameObject gameObj;

    public Tile(TileColor color){
        if(color == TileColor.NONE){
            throw new Error("Cannot instantiate Tile with no color");
        }

        this.color = color;
        gameObj = new GameObject(
                new ImageRendererComponent("Tiles/Tile " + getTileColorName(color) + ".png"),
                new ButtonComponent(),
                new RectRendererComponent(Style.HL_COLOR),
                new PositionAnimationComponent()
        );
        gameObj.setSize(new Vec2(SIZE));

        unHighlight();
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

    @Override
    public String toString() {
        return "Tile{" + getTileColorName(color) + '}';
    }

    public boolean isFirstPlayerMarker(){
        return color == TileColor.FIRST_PLAYER;
    }

    public boolean isColorTile(){
        return !isFirstPlayerMarker();
    }
}
