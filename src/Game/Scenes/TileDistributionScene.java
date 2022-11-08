package Game.Scenes;

import Engine.Components.PositionAnimationComponent;
import Engine.Core.AbstractScene;
import Engine.Core.GameCanvas;
import Engine.Core.Vec2;
import Game.Backend.Factory;
import Game.Backend.Game;
import Game.Backend.Tile;

import Game.App;

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
        game.getGameObject().getComponent(PositionAnimationComponent.class).moveTo(new Vec2(App.WIDTH, App.HEIGHT).scaledBy(.5),10);
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

        factories.get((factory++) % factories.size()).addTiles(Collections.singletonList(tile));

        Vec2 targetPosition = tile.getGameObject().getPosition();

        tile.getGameObject().setPosition(
            // Calculate where the center of the game table is relative to the factory
            tile.getGameObject()
                .getAbsolutePosition()
                .minus(game.getGameObject().getAbsolutePosition())
                .scaledBy(-1)
        );
        tile.getGameObject().getComponent(PositionAnimationComponent.class).moveTo(targetPosition, 10);

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
