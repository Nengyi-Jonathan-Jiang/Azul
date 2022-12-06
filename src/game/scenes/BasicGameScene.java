package game.scenes;

import engine.components.PositionAnimationComponent;
import engine.core.*;
import game.App;
import game.backend.Game;
import game.frontend.BagCountDisplay;
import game.frontend.InfoPanelObject;

public abstract class BasicGameScene extends AbstractScene {
    private static Vec2 lastMousePos = Vec2.zero;
    protected final Game game;
    protected final InfoPanelObject infoPanel;

    private final boolean disablePanning;
    private final BagCountDisplay bagCountText;

    public BasicGameScene(Game game) {
        this(game, false);
    }

    public BasicGameScene(Game game, boolean disablePanning) {
        this.game = game;
        this.infoPanel = new InfoPanelObject();
        this.disablePanning = disablePanning;
        bagCountText = new BagCountDisplay(game);
    }

    @Override
    public void update() {
        if (Input.isMouseRightDown() && !disablePanning) {
            GameObject gObject = game.getGameObject();
            gObject.move(Input.getMousePosition().minus(lastMousePos).scaledBy(1.5));

            constrainBackground();
        }

        lastMousePos = Input.getMousePosition();
    }

    private void constrainBackground(){
        GameObject gObject = game.getGameObject();
        PositionAnimationComponent a = gObject.getComponent(PositionAnimationComponent.class);
        Vec2 d = App.canvas.get_size().scaledBy(.5);

        if (gObject.getTopLeft().x > -d.x) {
            gObject.move(new Vec2(-d.x - gObject.getTopLeft().x, 0));
            a.stopAnimation();
        }
        if (gObject.getTopRight().x < d.x) {
            gObject.move(new Vec2(d.x - gObject.getTopRight().x, 0));
            a.stopAnimation();
        }
        if (gObject.getTopLeft().y > -d.y) {
            gObject.move(new Vec2(0, -d.y - gObject.getTopLeft().y));
            a.stopAnimation();
        }
        if (gObject.getBottomLeft().y < d.y) {
            gObject.move(new Vec2(0, d.y - gObject.getBottomLeft().y));
            a.stopAnimation();
        }
    }

    @Override
    public void draw(GameCanvas canvas) {
        constrainBackground();
        game.draw(canvas);
        infoPanel.draw(canvas);
        bagCountText.draw(canvas);
    }
}