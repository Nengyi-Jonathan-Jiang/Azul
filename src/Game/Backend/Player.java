package Game.Backend;

import Engine.Core.GameObject;
import Engine.Core.Vec2;
import Game.Frontend.PlayerGameObject;

import java.awt.*;

public class Player implements Comparable<Player> {
    private String name;

    private GameObject playerObject;

    private ScoreMarker scoreMarker;
    private PatternLines patternLines;
    private FloorLine floorLine;
    private Wall wall;

    private Hand hand;
    
    public Player(String playerName){
        name = playerName;
        floorLine = new FloorLine();
        patternLines = new PatternLines();
        wall = new Wall();

        playerObject = new PlayerGameObject(playerName);

        playerObject.addChild(floorLine.getGameObject());
        floorLine.getGameObject().setTopLeft(new Vec2(17, 437).plus(playerObject.getTopLeftOffset()));

        playerObject.addChild(patternLines.getGameObject());
        patternLines.getGameObject().setTopRight(new Vec2(-264, 178).plus(playerObject.getTopRightOffset()));

        scoreMarker = new ScoreMarker(this);
        playerObject.addChild(scoreMarker.getGameObject());

        hand = new Hand();
        playerObject.addChild(hand.getGameObject());
        hand.getGameObject().setPosition(playerObject.getBottomLeft().scaledBy(0, 1.25));
    }
    
    public ScoreMarker getScoreMarker(){
        return scoreMarker;   
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

    public String getName(){
        return name;
    }
    
    public int compareTo(Player o){
        return this.scoreMarker.getScore() - o.getScoreMarker().getScore();
    }

    public Hand getHand() {
        return hand;
    }
}
