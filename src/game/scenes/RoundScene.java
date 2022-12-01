package game.scenes;

import engine.core.AbstractScene;
import engine.core.InstantaneousScene;
import engine.core.SceneGroup;
import game.backend.board.AbstractTileSet;
import game.backend.Game;
import game.backend.player.Player;
import game.backend.Tile;
import game.util.PositionAnimation;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class RoundScene extends SceneGroup {
    private final Game game;

    public RoundScene(Game game) {
        this.game = game;
    }

    @Override
    public Iterator<? extends AbstractScene> getScenesAfter() {
        AtomicInteger startingPlayer = new AtomicInteger(0);

        return concatIterators(
                makeIterator(
                        // Figure out first player
                        new InstantaneousScene() {
                            @Override
                            public void execute() {
                                List<Player> players = game.getPlayers();
                                for (int i = 0; i < players.size(); i++) {
                                    if (players.get(i).hasFirstPlayerTile()) {
                                        startingPlayer.set(i);
                                        Tile t = players.get(i).removeFirstPlayerTile();
                                        PositionAnimation.animate(t.getGameObject(), () -> game.getMiddle().getCenter().addFirstPlayerTile(t), 10);
                                        return;
                                    }
                                }
                                throw new Error("Could not find first player tile");
                            }
                        },
                        // Distribute tiles
                        new TileDistributionScene(game)
                ),
                // Player turns
                makeLoopIterator(
                        new Supplier<>() {
                            int player = 0;
                            @Override
                            public AbstractScene get() {
                                Player p = game.getPlayers().get((player++ + startingPlayer.get()) % game.getPlayers().size());
                                return p.isHumanPlayer() ? new PlayerTurnScene(game, p) : new ComputerTurnScene(game, p);
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
