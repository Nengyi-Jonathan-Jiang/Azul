package game.frontend;

import engine.components.RectRendererComponent;
import engine.core.GameCanvas;
import engine.core.GameObject;
import engine.core.Vec2;
import game.Style;
import game.backend.Game;

import java.util.concurrent.atomic.AtomicReference;

public class BagCountDisplay extends GameObject {
    private final Game game;

    private final TextObject remTiles, retTiles;

    public BagCountDisplay(Game game) {
        super(
                new Vec2(220, 120),
                new RectRendererComponent(Style.FG_COLOR, new AtomicReference<>(Style.makeTransparent(Style.DM_COLOR, 127)))
        );
        this.game = game;

        remTiles = new TextObject("");
        retTiles = new TextObject("");

        addChildren(remTiles, retTiles);

        updateText();
    }

    private void updateText(){
        remTiles.setText("Bag: " + game.getBag().getCount() + " tiles");
        retTiles.setText("Returned: " + game.getBag().getReturnedCount() + " tiles");
        retTiles.resize(new Vec2(200, 45));
        remTiles.resize(new Vec2(200, 45));
        retTiles.setBottomLeft(new Vec2(Style.PADDING,-Style.PADDING).plus(getBottomLeftOffset()));
        remTiles.setBottomLeft(new Vec2(Style.PADDING,-55-Style.PADDING).plus(getBottomLeftOffset()));
    }

    @Override
    public GameObject draw(GameCanvas canvas) {
        setBottomLeft(canvas.get_size().scaledBy(.5).scaledBy(-1, 1));
        updateText();
        return super.draw(canvas);
    }
}
