package game.backend;

import engine.components.RectRendererComponent;
import engine.core.GameObject;
import game.Style;
import game.frontend.FloorLineGameObject;

import java.util.ArrayList;
import java.util.List;

public class FloorLine implements ILine {
    public static final double TILE_SPACING = 48.5;

    private static final int[] deductions = {1, 1, 2, 2, 2, 3, 3};

    private final List<Tile> tiles;

    private final GameObject gameObject;

    public FloorLine() {
        tiles = new ArrayList<>();
        gameObject = new FloorLineGameObject();
        unHighlight();
    }

    public void addTile(Tile t) {
        tiles.add(t);
        gameObject.addChild(t.getGameObject());
    }

    public void highlight() {
        gameObject.getComponent(RectRendererComponent.class).setBorderColor(Style.HL_COLOR);
        gameObject.getComponent(RectRendererComponent.class).enable();
    }

    public void highlight2() {
        gameObject.getComponent(RectRendererComponent.class).setBorderColor(Style.HL2_COLOR);
        gameObject.getComponent(RectRendererComponent.class).enable();
    }

    public void unHighlight() {
        gameObject.getComponent(RectRendererComponent.class).disable();
    }

    public List<Tile> removeAll() {
        List<Tile> res = new ArrayList<>(tiles);
        tiles.clear();
        return res;
    }

    public boolean isFull() {
        return tiles.size() == deductions.length;
    }

    @Override
    public boolean canAddTile() {
        return true;
    }

    public int getDeduction() {
        int res = 0;
        for (int i = 0; i < tiles.size(); i++) {
            res += deductions[i];
        }
        return res;
    }

    public GameObject getGameObject() {
        return gameObject;
    }
}
