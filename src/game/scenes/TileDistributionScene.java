package game.scenes;

import engine.core.AbstractScene;
import engine.core.GameCanvas;
import engine.core.Vec2;
import game.backend.Factory;
import game.backend.Game;
import game.backend.Tile;

import game.App;
import game.util.PositionAnimation;

import java.util.Collections;
import java.util.List;

public class TileDistributionScene extends AbstractScene {
    // We do a victor style tile distribution animation lol

    private final Game game;

    public TileDistributionScene(Game game) {
        this.game = game;
    }

    public int factory = 0;
    public int cooldown = 0;
    public int animation = 0;

    @Override
    public void onExecutionStart() {
        PositionAnimation.animate(game.getGameObject(), () -> game.getGameObject().setPosition(new Vec2(App.WIDTH, App.HEIGHT).scaledBy(.5)), 10);
    }

    @Override
    public void update() {
        List<Factory> factories = game.getMiddle().getFactories();

        animation++;

        if(factory >= 4 * factories.size()) return;

        game.getMiddle().positionFactories(animation / 20.);

        if(cooldown != 0){
            cooldown--;
            return;
        }

        Tile tile = game.getBag().popTile();
        tile.getGameObject().setAbsolutePosition(game.getGameObject().getAbsolutePosition());

        PositionAnimation.animate(tile.getGameObject(), () -> factories.get((factory++) % factories.size()).addTiles(Collections.singletonList(tile)), 10);

        cooldown = 5;
    }

    @Override
    public void draw(GameCanvas canvas) {
        game.getGameObject().draw(canvas);
    }

    @Override
    public boolean isFinished() {
        return animation > game.getMiddle().getFactories().size() * 4 * 6 + 10;
    }
}
