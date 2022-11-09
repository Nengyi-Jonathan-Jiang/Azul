package Game.Backend;

import Engine.Core.GameObject;
import Engine.Core.Vec2;
import Game.Frontend.PlayerGameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Player implements Comparable<Player> {
    private String name;

    private PlayerGameObject playerObject;
    private GameObject boardObject;

    private ScoreMarker scoreMarker;
    private PatternLines patternLines;
    private FloorLine floorLine;
    private Wall wall;

    private Hand hand;
    
    public Player(String playerName, int playerNum){
        name = playerName;
        floorLine = new FloorLine();
        patternLines = new PatternLines();
        wall = new Wall();

        PlayerGameObject p = new PlayerGameObject(playerName, playerNum);
        playerObject = p;
        boardObject = p.getBoardObject();

        boardObject.addChild(floorLine.getGameObject());
        floorLine.getGameObject().setTopLeft(new Vec2(17, 437).plus(boardObject.getTopLeftOffset()));

        boardObject.addChild(patternLines.getGameObject());
        patternLines.getGameObject().setTopRight(new Vec2(-264, 178).plus(boardObject.getTopRightOffset()));

        boardObject.addChild(wall.getGameObject());
        wall.getGameObject().setPosition(new Vec2(372, 290).plus(boardObject.getTopLeftOffset()));

        scoreMarker = new ScoreMarker(this);
        boardObject.addChild(scoreMarker.getGameObject());

        hand = new Hand();
        boardObject.addChild(hand.getGameObject());

        switch (playerNum) {
            case 0 -> hand.getGameObject().setTopLeft(boardObject.getTopRightOffset().scaledBy(1.3, 0.4));
            case 1 -> hand.getGameObject().setTopRight(boardObject.getTopLeftOffset().scaledBy(1.3, 0.4));
            case 2 -> hand.getGameObject().setBottomRight(boardObject.getBottomLeftOffset().scaledBy(1.3, 0.4));
            default -> hand.getGameObject().setBottomLeft(boardObject.getBottomRightOffset().scaledBy(1.3, 0.4));
        }
    }
    
    public ScoreMarker getScoreMarker(){
        return scoreMarker;   
    }
    public GameObject getBoardObject(){
        return boardObject;
    }
    public PlayerGameObject getGameObject() {
        return playerObject;
    }

    public Wall getWall(){
        return wall;
    }

    public PatternLines getPatternLines(){
        return patternLines;
    }

    public List<PatternLine> getAvailablePatternLinesForColor(Tile.TileColor color){
        List<PatternLine> res = new ArrayList<>();
        for(int row = 0; row < 5; row++){
            PatternLine line = patternLines.getRow(row);
            if(line.canPlaceTile(color) && !wall.rowHasTileColor(row, color)){
                res.add(line);
            }
        }

        return res;
    }

    public String getName(){
        return name;
    }
    
    public int compareTo(Player o){
        return this.scoreMarker.getScore() - o.getScoreMarker().getScore();
    }

    public Hand getHand() {
        return hand;
    }

    public FloorLine getFloorLine() {
        return floorLine;
    }
}
