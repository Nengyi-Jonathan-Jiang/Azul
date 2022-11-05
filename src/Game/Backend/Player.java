package Game.Backend;

import Engine.Core.GameObject;
import Engine.Core.Vec2;
import Game.Frontend.PlayerGameObject;

public class Player {
    private String name;

    private GameObject playerObject;

    private int score;
    private PatternLines patternLines;
    private FloorLine floorLine;
    private Wall wall;

    public Player(String playerName){
        name = playerName;
        floorLine = new FloorLine();


        playerObject = new PlayerGameObject(playerName);
        playerObject.addChild(floorLine.getGameObject());
        floorLine.getGameObject().setTopLeft(new Vec2(17, 437).plus(playerObject.getTopLeftOffset()));



        floorLine.push(new Tile(Tile.TileColor.YELLOW));
        floorLine.push(new Tile(Tile.TileColor.BLACK));
        floorLine.push(new Tile(Tile.TileColor.BLUE));
        floorLine.push(new Tile(Tile.TileColor.RED));
        floorLine.push(new Tile(Tile.TileColor.FIRST_PLAYER));
        floorLine.push(new Tile(Tile.TileColor.FIRST_PLAYER));
        floorLine.push(new Tile(Tile.TileColor.FIRST_PLAYER));
    }

    public GameObject getGameObject(){
        return playerObject;
    }


}
