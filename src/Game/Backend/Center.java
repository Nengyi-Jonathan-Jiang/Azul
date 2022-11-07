package Game.Backend;

import Engine.Core.GameObject;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Center extends AbstractTileSet{
    private Tile firstPlayerTile;
    protected final GameObject gameObject;

    public Center(){
        super();
        firstPlayerTile =  new Tile();
        gameObject = createGameObject();
    }

    public boolean hasFirstPlayerTile(){
        if(this.getTilesOfColor(Tile.TileColor.FIRST_PLAYER) != null){
            return true;
        }
    }
}






















