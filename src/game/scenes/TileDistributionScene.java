package game.scenes;

import engine.core.Vec2;
import game.App;
import game.backend.board.Factory;
import game.backend.Game;
import game.backend.Tile;
import game.util.PositionAnimation;

import java.util.Collections;
import java.util.List;

public class TileDistributionScene extends BasicGameScene {
    public int currentFactory = 0;
    public int tileDistributionCooldown = 0;
    public int elapsedTime = 0;

    public TileDistributionScene(Game game) {
        super(game, false);
        infoPanel.setChildren("Distributing tiles to factories", null);
    }

    @Override
    public void onExecutionStart() {
        PositionAnimation.animate(game.getGameObject(),
                () -> game.getGameObject().setPosition(Vec2.zero),
            10);
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
    public boolean isFinished() {
        return elapsedTime > game.getMiddle().getFactories().size() * 4 * 6 + 10;
    }
}