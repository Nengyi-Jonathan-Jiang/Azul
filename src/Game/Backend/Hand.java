package Game.Backend;

import Engine.Core.GameObject;

import java.util.List;

public class Hand {
    private List<Tile> tiles;
    private final GameObject gameObject;

    public Hand(){
        // TODO: change to specific hand object
        gameObject = new GameObject();
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

    public void clear(){
        this.tiles = null;
    }
}
