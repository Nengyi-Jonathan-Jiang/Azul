package Game.Scenes;

import Engine.Components.PositionAnimationComponent;
import Engine.Core.Vec2;
import Game.Backend.Factory;
import Game.Backend.Game;
import Game.Backend.Tile;

import java.util.Collections;
import java.util.List;

// TODO
public class TileDistributionScene extends AbstractGameScene {
    // We do a victor style distribution animation lol

    public TileDistributionScene(Game game) {
        super(game);
    }

    public double rot = 0;
    public int factory = 0;

    public boolean state = true;   // true -> rotating, false -> adding tile
    public double animationTime;

    @Override
    public void update() {
        // Must call to allow dragging of table
        super.update();

        if(factory >= game.getMiddle().getFactories().size() * 4 && state) return;

        if(state) {
            if(animationTime >= 1) {
                state = false;
                animationTime = 0;

                List<Factory> factories = game.getMiddle().getFactories();
                Tile tile = game.getBag().popTile();

                factories.get((factory++) % factories.size()).addTiles(Collections.singletonList(tile));

                tile.getGameObject().setPosition(tile.getGameObject().getAbsolutePosition().scaledBy(-1));
                tile.getGameObject().getComponent(PositionAnimationComponent.class).moveTo(Vec2.zero, 10);
            }

            rot += 0.1;
            animationTime += 0.1;
            game.getMiddle().positionFactories(-rot);
        }
        else{
            if(animationTime >= 1){
                state = true;
                animationTime = 0;
            }
            animationTime += 0.1;
        }
    }

    @Override
    public boolean isFinished() {
        // return factory >= game.getMiddle().getFactories().size() * 4 && state;
    }
}
