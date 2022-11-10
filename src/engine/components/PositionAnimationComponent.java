package engine.components;

import engine.core.GameCanvas;
import engine.core.Vec2;

public class PositionAnimationComponent extends Component{
    private Vec2 startPosition;
    private Vec2 targetPosition;
    private double elapsedTime;
    private double animationDuration;

    public void moveTo(Vec2 targetPosition, double animationDuration){
        startPosition = this.gameObject.getPosition();
        this.targetPosition = targetPosition;
        this.animationDuration = animationDuration;
        elapsedTime = 0;
    }

    @Override
    public void drawAndUpdate(GameCanvas canvas) {
        if(elapsedTime >= animationDuration) return;

        elapsedTime += 1;
        this.gameObject.setPosition(startPosition.plus(
            targetPosition.minus(startPosition).scaledBy(elapsedTime / animationDuration)
        ));
    }
}
