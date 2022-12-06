package game.frontend;

import engine.core.GameCanvas;
import engine.core.GameObject;
import engine.core.Vec2;
import game.Style;
import game.backend.Game;

public class BagCountDisplay extends GameObject {
    private final Game game;

    private final TextObject remTiles, retTiles;

    public BagCountDisplay(Game game) {
        this.game = game;

        remTiles = new TextObject("Remaining Tiles: " + game.getBag().getCount());
        retTiles = new TextObject("Returned Tiles: " + game.getBag().getReturnedCount());

        retTiles.setBottomLeft(new Vec2(0,0));
        remTiles.setBottomLeft(new Vec2(0,-55));

        addChildren(remTiles, retTiles);
    }

    @Override
    public GameObject draw(GameCanvas canvas) {
        remTiles.setText("Remaining Tiles: " + game.getBag().getCount());
        retTiles.setText("Returned Tiles: " + game.getBag().getReturnedCount());
        setPosition(canvas.get_size().scaledBy(.5).minus(new Vec2(Style.TEXT_PADDING)).scaledBy(-1, 1));
        return super.draw(canvas);
    }
}
