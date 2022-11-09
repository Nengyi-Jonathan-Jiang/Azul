package Game.Backend;

import Engine.Components.RectRendererComponent;
import Engine.Core.GameObject;
import Engine.Core.Vec2;

import java.awt.Color;

public class ScoreMarker {
    private static final double SIZE = 20;
    private static final double H_SPACING = 22.9;
    private static final double V_SPACING = 28.2;

    private final Player p;

    private int score;
    private final GameObject gameObject;
    public ScoreMarker(Player p){
        this.p = p;
        gameObject = new GameObject(new Vec2(SIZE), new RectRendererComponent(Color.BLACK, Color.BLACK));
        setScore(0);
    }

    public void setScore(int s){
        if(s < 0) s = 0;
        score = s;
        if(s == 0){
            gameObject.setTopLeft(new Vec2(22, 1).plus(p.getBoardObject().getTopLeftOffset()));
        }
        else{
            //noinspection IntegerDivisionInFloatingPointContext
            gameObject.setTopLeft(new Vec2(22, 28).plus(p.getBoardObject().getTopLeftOffset()).plus(new Vec2(
                H_SPACING * ((score - 1) % 20),
                V_SPACING * ((score - 1) / 20)
            )));
        }
    }

    public int getScore(){
        return score;
    }

    public GameObject getGameObject(){
        return gameObject;
    }
}
