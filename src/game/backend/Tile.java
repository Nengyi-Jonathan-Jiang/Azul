package game.backend;

import engine.components.ButtonComponent;
import engine.components.ImageRendererComponent;
import engine.components.PositionAnimationComponent;
import engine.components.RectRendererComponent;
import engine.core.GameObject;
import engine.core.Vec2;
import game.Style;

public class Tile implements Comparable<Tile> {
    public static final double SIZE = 40;
    public final static TileColor[] allColors = {TileColor.RED, TileColor.ORANGE, TileColor.SNOW, TileColor.BLUE, TileColor.BLACK};
    private final TileColor color;
    private final GameObject gameObj;

    public Tile(TileColor color) {
        this.color = color;
        gameObj = new GameObject(
                new ImageRendererComponent(getTileColorName(color)),
                new ButtonComponent(),
                new RectRendererComponent(Style.HL2_COLOR),
                new PositionAnimationComponent()
        );
        gameObj.resize(new Vec2(SIZE));

        unHighlight();
    }

    public static String getTileColorName(TileColor color) {
        return switch (color) {
            case RED -> "red";
            case ORANGE -> "orange";
            case SNOW -> "snow";
            case BLUE -> "blue";
            case BLACK -> "black";
            case NONE -> "none";
            case FIRST -> "first";
        };
    }

    @Override
    public int compareTo(Tile o) {
        return color.compareTo(o.color);
    }

    public TileColor getColor() {
        return color;
    }

    public GameObject getGameObject() {
        return gameObj;
    }

    public void highlight() {
        gameObj.getComponent(RectRendererComponent.class).enable();
    }

    public void unHighlight() {
        gameObj.getComponent(RectRendererComponent.class).disable();
    }

    @Override
    public String toString() {
        return "Tile{" + getTileColorName(color) + '}';
    }

    public boolean isFirstPlayerMarker() {
        return color == TileColor.FIRST;
    }

    public boolean isColorTile() {
        return !isFirstPlayerMarker();
    }

    public enum TileColor {
        FIRST,
        RED,
        ORANGE,
        SNOW,
        BLUE,
        BLACK,
        NONE
    }
}
