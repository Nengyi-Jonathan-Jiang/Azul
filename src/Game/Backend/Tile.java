import Engine.Core.GameObject;

public class Tile{
    enum TileColor{
        RED,
        YELLOW,
        TEAL,
        BLUE,
        BLACK,
        NONE,
        FIRST_PLAYER
    }
    private TileColor color;
    private GameObject gameObj;
    public Tile(TileColor color){
        this.color = color;
        gameObj = new GameObject(null, null, null); // idk what to put for this part
    }
    public GameObject getGameObject(){
        return gameObj;
    }
    public TileColor getColor(){
        return color;
    }


}
