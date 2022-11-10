package game.backend;

import engine.core.GameObject;
import game.frontend.TilePileGameObject;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private final List<Tile> tiles;
    private final GameObject gameObject;

    public Hand(){
        gameObject = new TilePileGameObject();
        tiles = new ArrayList<>();
    }

    public List<Tile> getTiles(){
        return tiles;
    }

    public void addTile(Tile tile){
        this.tiles.add(tile);
        gameObject.addChild(tile.getGameObject());
    }

    public void clear(){
        this.tiles.clear();
    }

    public GameObject getGameObject(){
        return gameObject;
    }
}
