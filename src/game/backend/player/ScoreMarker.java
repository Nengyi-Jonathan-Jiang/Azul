package game.backend.player;

import engine.components.PositionAnimationComponent;
import engine.components.RectRendererComponent;
import engine.core.GameObject;
import engine.core.Vec2;

import java.awt.*;

public class ScoreMarker {
    private static final double SIZE = 20;
    private static final double H_SPACING = 22.9;
    private static final double V_SPACING = 28.2;

    private final Player p;
    private final GameObject gameObject;
    private int score = 0;

    public ScoreMarker(Player p) {
        this.p = p;
        gameObject = new GameObject(new Vec2(SIZE), new RectRendererComponent(Color.BLACK, Color.BLACK), new PositionAnimationComponent());
        gameObject.setTopLeft(new Vec2(22, 1).plus(p.getBoardObject().getTopLeftOffset()));
    }

    public int getScore() {
        return score;
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    public void setScore(int s) {
        if (s < 0) s = 0;
        score = s;

        Vec2 newPos = s == 0
                ? new Vec2(22, 1).plus(p.getBoardObject().getTopLeftOffset()).minus(gameObject.getTopLeftOffset())
                : new Vec2(22, 28).plus(p.getBoardObject().getTopLeftOffset()).plus(new Vec2(
                H_SPACING * ((score - 1) % 20),
                V_SPACING * ((score - 1) / 20)
        )).minus(gameObject.getTopLeftOffset());

        gameObject.getComponent(PositionAnimationComponent.class).moveTo(newPos, 10);
    }

    public GameObject getGameObject() {
        return gameObject;
    }
}