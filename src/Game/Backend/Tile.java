package Game.Backend;
import Engine.Components.ButtonComponent;
import Engine.Components.ImageRendererComponent;
import Engine.Components.PositionAnimationComponent;
import Engine.Components.RectRendererComponent;
import Engine.Core.GameObject;
import Engine.Core.Vec2;

import Game.Style;

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

    public boolean isHighlighted(){
        return gameObj.getComponent(RectRendererComponent.class).isEnabled();
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
