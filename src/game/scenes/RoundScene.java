package game.scenes;

import engine.core.AbstractScene;
import game.backend.AbstractTileSet;
import game.backend.Game;
import game.backend.Player;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class RoundScene extends AbstractScene {
    private final Game game;

    public RoundScene(Game game){
        this.game = game;
    }

    @Override
    public Iterator<? extends AbstractScene> getScenesAfter() {
        AtomicInteger startingPlayer = new AtomicInteger(0);

        return concatIterators(
            makeIterator(
                // Figure out first player
                new ActionScene(() -> {
                    List<Player> players = game.getPlayers();
                    for(int i = 0; i < players.size(); i++){
                        if(players.get(i).hasFirstPlayerTile()){
                            startingPlayer.set(i);
                            return;
                        }
                    }
                }),
                // Distribute tiles
                new TileDistributionScene(game)
            ),
            // Player turns
            makeLoopIterator(
                new Supplier<>() {
                    int player = startingPlayer.get();
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
