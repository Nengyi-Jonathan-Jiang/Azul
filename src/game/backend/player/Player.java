package game.backend.player;

import engine.core.GameObject;
import engine.core.Vec2;
import game.backend.*;
import game.backend.ai.ComputerMove;
import game.backend.ai.PlayStyle;
import game.frontend.PlayerGameObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player implements Comparable<Player> {
    private final String name;
    private final PlayerGameObject playerObject;
    private final GameObject boardObject;
    private final ScoreMarker scoreMarker;
    private final PatternLines patternLines;
    private final FloorLine floorLine;
    private final Wall wall;
    private Tile firstPlayerTile = null;
    private final PlayStyle playStyle;

    public Player(String playerName, int playerNum, PlayStyle playStyle) {
        name = playerName;
        this.playStyle = playStyle;
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

        if (playerNum == 0) {
            setFirstPlayerTile(new Tile(Tile.TileColor.FIRST_PLAYER));
        }
    }

    public ScoreMarker getScoreMarker() {
        return scoreMarker;
    }

    public GameObject getBoardObject() {
        return boardObject;
    }

    public PlayerGameObject getGameObject() {
        return playerObject;
    }

    public Wall getWall() {
        return wall;
    }

    public PatternLines getPatternLines() {
        return patternLines;
    }

    public List<ILine> getAvailableLinesForColor(Tile.TileColor color) {
        List<ILine> res = new ArrayList<>();
        for (int row = 0; row < 5; row++) {
            PatternLine line = patternLines.getLine(row);
            if (line.canAddTileOfColor(color) && !wall.rowHasTileColor(row, color)) {
                res.add(line);
            }
        }

        if (res.isEmpty()) return Collections.singletonList(floorLine);

        return res;
    }

    public String getName() {
        return name;
    }

    public String toString(){
        return name;
    }

    public int compareTo(Player o) {
        return o.getScoreMarker().getScore() - this.scoreMarker.getScore();
    }

    public FloorLine getFloorLine() {
        return floorLine;
    }

    public void setFirstPlayerTile(Tile tile) {
        firstPlayerTile = tile;
        boardObject.addChild(tile.getGameObject());
        tile.getGameObject().setPosition(new Vec2(52, 278).plus(boardObject.getTopLeftOffset()));
    }

    public boolean hasFirstPlayerTile() {
        return firstPlayerTile != null;
    }

    public Tile removeFirstPlayerTile() {
        Tile res = firstPlayerTile;
        firstPlayerTile = null;
        return res;
    }

    public boolean isHumanPlayer(){
        return playStyle == null;
    }

    public ComputerMove getMove(Game game){
        if(isHumanPlayer()){
            throw new Error("Cannot get computed move for physical player");
        }
        return playStyle.getMove(this, game);
    }

    public double whatIfScore(){
        double max = 0;

        for(int i = 0; i <= 0b11111; i++){
            int rawScore = 0;
            double multiplier = 1.0;

            List<Vec2> temporaryPositions = new ArrayList<>();

            for(int r = 0; r < 5; r++){
                PatternLine line = patternLines.getLine(r);
                // DO NOT MERGE THESE IF STATEMENTS THEY DO DIFFERENT THINGS
                if((i & (1 << r)) == 0) continue;
                if(line.getNumTiles() == 0) break;

                Tile.TileColor lineColor = line.getCurrentTileColor();

                multiplier *= 1. * line.getNumTiles() / line.getCapacity();

                int col = wall.getCol(r, lineColor);
                temporaryPositions.add(new Vec2(r, col));
                rawScore += wall.placeTile(r, new Tile(lineColor)).getScoreAdded();
            }

            for(Vec2 v : temporaryPositions) wall.removeTile((int)v.x, (int)v.y);

            max = Math.max(max, rawScore * multiplier);
        }

        return max;
    }

    public double whatIfScore(ILine line, int num, Tile.TileColor color){
        if(line instanceof FloorLine){
            return this.whatIfScore()
                - FloorLine.getDeduction(floorLine.getNumTiles() + num)
                + floorLine.getDeduction();
        }
        PatternLine l = (PatternLine) line;

        int n = Math.min(l.getNumTiles() + num, l.getCapacity());
        int floor = Math.max(l.getNumTiles() + num - l.getCapacity(), 0);

        return l.runWithNumTiles(n, color, this::whatIfScore)
                - FloorLine.getDeduction(floorLine.getNumTiles() + floor)
                + floorLine.getDeduction();
    }

    public void highlight(){
        playerObject.highlight();
    }

    public void unhighlight(){
        playerObject.unHighlight();
    }
}
