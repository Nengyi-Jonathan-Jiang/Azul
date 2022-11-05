package Game.Backend;

import Engine.Core.GameObject;
import Engine.Core.Vec2;
import Game.Frontend.FloorLineGameObject;

import java.util.ArrayList;
import java.util.List;

public class FloorLine {
    public static final double TILE_SPACING = 48.5;

    private static final int[] deductions = {1, 1, 2, 2, 2, 3, 3};

    private final List<Tile> tiles;

    private final GameObject gameObject;

    public FloorLine(){
        tiles = new ArrayList<>();
        gameObject = new FloorLineGameObject();
    }

    public void push(Tile t){
        tiles.add(t);
        gameObject.addChild(t.getGameObject());
    }

    public Tile pop(){
        return tiles.remove(tiles.size() - 1);
    }

    public boolean isEmpty(){
        return tiles.isEmpty();
    }

    public boolean isFull(){
        return tiles.size() == deductions.length;
    }

    public int getDeduction(){
        int res = 0;
        for(int i = 0; i < tiles.size(); i++){
            res += deductions[i];
        }
        return res;
    }

    public GameObject getGameObject(){
        return gameObject;
    }
}
