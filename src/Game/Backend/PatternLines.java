package Game.Backend;

import Engine.Core.GameObject;
import Engine.Core.Vec2;
import Game.Frontend.PatternLinesGameObject;

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
}
