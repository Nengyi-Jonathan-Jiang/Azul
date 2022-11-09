package Game.Scenes;

import Engine.Components.PositionAnimationComponent;
import Engine.Core.AbstractScene;
import Engine.Core.GameCanvas;
import Engine.Core.Vec2;
import Game.Backend.AbstractTileSet;
import Game.Backend.Factory;
import Game.Backend.Game;

import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public class RoundScene extends AbstractScene {
    private final Game game;

    public RoundScene(Game game){
        this.game = game;
    }

    @Override
    public Iterator<? extends AbstractScene> getScenesAfter() {
        return concatIterators(
            // Distribute tiles
            makeIterator(new TileDistributionScene(game)),
            // Player turns
            makeLoopIterator(
                new Supplier<>() {
                    int player = 0;
                    @Override
                    public AbstractScene get() {
                        return groupScenes(
                            new PlayerTurnScene(game, game.getPlayers().get(player++ % game.getPlayers().size())),
                            new WaitScene(game, 50)
                        );
                    }
                },

                () -> !(
                        game.getMiddle().getFactories().stream().map(AbstractTileSet::getAllTiles).allMatch(List::isEmpty)
                        && game.getMiddle().getCenter().getAllTiles().isEmpty()
                )
            ),
            // Scoring

            makeLoopIterator(game.getPlayers(), p -> new ScoringScene(game, p))
        );
    }
}
