package game.scenes;

import engine.core.AbstractScene;
import engine.core.GameCanvas;
import engine.core.Vec2;
import game.App;
import game.backend.board.Factory;
import game.backend.Game;
import game.backend.Tile;
import game.util.PositionAnimation;

import java.util.Collections;
import java.util.List;

public class TileDistributionScene extends AbstractScene {
    private final Game game;
    public int currentFactory = 0;
    public int tileDistributionCooldown = 0;
    public int elapsedTime = 0;

    public TileDistributionScene(Game game) {
        this.game = game;
    }

    @Override
    public void onExecutionStart() {
        PositionAnimation.animate(game.getGameObject(), () -> game.getGameObject().setPosition(new Vec2(App.WIDTH, App.HEIGHT).scaledBy(.5)), 10);
    }

    @Override
    public void update() {
        List<Factory> factories = game.getMiddle().getFactories();

        elapsedTime++;

        if (currentFactory >= 4 * factories.size()) return;

        game.getMiddle().positionFactories(elapsedTime * .006);

        if (tileDistributionCooldown != 0) {
            tileDistributionCooldown--;
            return;
        }

        Tile tile = game.getBag().popTile();
        tile.getGameObject().setAbsolutePosition(game.getGameObject().getAbsolutePosition());

        PositionAnimation.animate(tile.getGameObject(), () -> factories.get((currentFactory++) % factories.size()).addTiles(Collections.singletonList(tile)), 10);

        tileDistributionCooldown = 5;
    }

    @Override
    public void draw(GameCanvas canvas) {
        game.draw(canvas);
    }

    @Override
    public boolean isFinished() {
        return elapsedTime > game.getMiddle().getFactories().size() * 4 * 6 + 10;
    }
}