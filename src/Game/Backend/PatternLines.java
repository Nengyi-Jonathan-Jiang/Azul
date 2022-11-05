package Game.Backend;

import Engine.Core.GameObject;
import Engine.Core.Vec2;
import Game.Frontend.PatternLinesGameObject;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PatternLines {
    public static final double TILE_SPACING = 45.5;

    private PatternLine[] lines;
    private GameObject gameObject;

    public PatternLines(){
        lines = new PatternLine[5];

        for(int i = 0; i < 5; i++){
            lines[i] = new PatternLine(i + 1);
        }

        gameObject = new PatternLinesGameObject();

        for(int i = 0; i < 5; i++){
            gameObject.addChild(lines[i].getGameObject());
            lines[i].getGameObject().setTopRight(new Vec2(0, i * TILE_SPACING).plus(gameObject.getTopRightOffset()));
        }
    }

    public PatternLine getRow(int row){
        return lines[row];
    }

    public GameObject getGameObject(){
        return gameObject;
    }

    public List<PatternLine> getAvailableLines(Tile.TileColor color){
        return Arrays.stream(lines).filter(l -> l.canPlaceTile(color)).collect(Collectors.toList());
    }
}
