package Game.Scenes;

import Game.Backend.Game;

// TODO
public class TileDistributionScene extends AbstractGameScene {
    // We do a victor style distribution animation lol

    public TileDistributionScene(Game game) {
        super(game);
    }

    @Override
    public void update() {
        // Must call to allow dragging of table
        super.update();


    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
