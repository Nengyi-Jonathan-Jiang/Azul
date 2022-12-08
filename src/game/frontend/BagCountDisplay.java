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

        remTiles = new TextObject("");
        retTiles = new TextObject("");

        addChildren(remTiles, retTiles);

        updateText();
    }

    private void updateText(){
        remTiles.setText("Bag: " + game.getBag().getCount() + " tiles");
        retTiles.setText("Returned: " + game.getBag().getReturnedCount() + " tiles");
        retTiles.setBottomLeft(new Vec2(0,0));
        remTiles.setBottomLeft(new Vec2(0,-55));
    }

    @Override
    public GameObject draw(GameCanvas canvas) {
        setPosition(canvas.get_size().scaledBy(.5).minus(new Vec2(Style.PADDING)).scaledBy(-1, 1));
        updateText();
        return super.draw(canvas);
    }
}
