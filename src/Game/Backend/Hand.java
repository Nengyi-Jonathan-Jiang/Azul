package Game.Backend;

import Engine.Components.RectRendererComponent;
import Engine.Core.GameObject;
import Game.Frontend.TilePileGameObject;

import java.util.ArrayList;
import java.util.List;
import Game.Style;

public class Hand {
    private List<Tile> tiles;
    private final GameObject gameObject;

    public Hand(){
        gameObject = new TilePileGameObject();
        tiles = new ArrayList<>();
    }

    public boolean hasTiles(){
        return tiles != null;
    }

    public List<Tile> getTiles(){
        return tiles;
    }

    public void setTiles(List<Tile> tiles){
        this.tiles = tiles;
        tiles.stream().map(Tile::getGameObject).forEach(gameObject::addChild);
    }

    public void addTile(Tile tile){
        this.tiles.add(tile);
        gameObject.addChild(tile.getGameObject());
    }

    public void clear(){
        this.tiles = null;
    }

    public GameObject getGameObject(){
        return gameObject;
    }
}
