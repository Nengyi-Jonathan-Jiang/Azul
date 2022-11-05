package Game.Backend;

import Engine.Core.GameObject;
import Engine.Core.Vec2;
import Game.Frontend.PlayerGameObject;

import java.awt.*;

public class Player {
    private String name;

    private GameObject playerObject;

    private ScoreMarker scoreMarker;
    private PatternLines patternLines;
    private FloorLine floorLine;
    private Wall wall;

    public Player(String playerName){
        name = playerName;
        floorLine = new FloorLine();
        patternLines = new PatternLines();


        playerObject = new PlayerGameObject(playerName);

        playerObject.addChild(floorLine.getGameObject());
        floorLine.getGameObject().setTopLeft(new Vec2(17, 437).plus(playerObject.getTopLeftOffset()));

        playerObject.addChild(patternLines.getGameObject());
        patternLines.getGameObject().setTopRight(new Vec2(-264, 178).plus(playerObject.getTopRightOffset()));


        scoreMarker = new ScoreMarker(this);
        playerObject.addChild(scoreMarker.getGameObject());

        // TESTING FLOOR LINE
        floorLine.push(new Tile(Tile.TileColor.BLUE));
        floorLine.push(new Tile(Tile.TileColor.FIRST_PLAYER));
        floorLine.push(new Tile(Tile.TileColor.YELLOW));
        floorLine.push(new Tile(Tile.TileColor.YELLOW));

        // TESTING PATTERN LINES
        patternLines.getRow(0).addTile(new Tile(Tile.TileColor.YELLOW));
        patternLines.getRow(2).addTile(new Tile(Tile.TileColor.BLUE));
        patternLines.getRow(3).addTile(new Tile(Tile.TileColor.BLACK));
        patternLines.getRow(3).addTile(new Tile(Tile.TileColor.BLACK));
        patternLines.getRow(3).addTile(new Tile(Tile.TileColor.BLACK));
        patternLines.getRow(4).addTile(new Tile(Tile.TileColor.BLACK));
        patternLines.getRow(4).addTile(new Tile(Tile.TileColor.BLACK));
        patternLines.getRow(4).addTile(new Tile(Tile.TileColor.BLACK));
        patternLines.getRow(4).addTile(new Tile(Tile.TileColor.BLACK));
        patternLines.getRow(4).addTile(new Tile(Tile.TileColor.BLACK));
    }

    public GameObject getGameObject(){
        return playerObject;
    }

    public Wall getWall(){
        return wall;
    }

    public PatternLines getPatternLines(){
        return patternLines;
    }
}
