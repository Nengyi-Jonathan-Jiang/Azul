package game.scenes;

import engine.components.*;
import engine.core.*;
import engine.input.MouseEvent;
import game.Style;
import game.backend.Game;
import game.backend.Tile;
import game.backend.ai.styles.GreedyComputer;
import game.backend.board.AbstractTileSet;
import game.backend.player.Player;
import game.backend.player.Wall;
import game.frontend.GameGameObject;
import game.frontend.InfoPanelObject;

import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class HowToPlayScene extends SceneGroup {
    private static abstract class StepScene extends BasicGameScene {
        protected boolean finished = false;

        public StepScene(Supplier<Game> game, boolean pan, String... text) {
            super(game.get(), !pan);
            infoPanel.setChildren(Stream.concat(Arrays.stream(text), Stream.of("Next")).toArray(String[]::new));
        }

        @Override
        public void onMouseClick(MouseEvent me) {
            if(infoPanel.getRight().getComponent(ButtonComponent.class).contains(me.position)){
                finished = true;
            }
        }

        @Override
        public boolean isFinished() {
            return finished;
        }
    }

    @Override
    public Iterator<? extends AbstractScene> getScenesAfter() {
        Game g = new Game(List.of(
            new Player("Player 1", 0, new GreedyComputer()),
            new Player("Player 2", 1, new GreedyComputer())
        ));

        return makeIterator(
            new StepScene(() -> g, false, "Welcome to Azul!") {},
            new StepScene(() -> g, false, "If you haven't already, read the Azul rulebook", "Click for rulebook") {

                boolean doRuleBook = false;

                @Override
                public void onMouseClick(MouseEvent me) {
                    if (finished = doRuleBook = infoPanel.get(1).getComponent(ButtonComponent.class).contains(me.position))
                        return;
                    super.onMouseClick(me);
                }

                @Override
                public Iterator<? extends AbstractScene> getScenesAfter() {
                    return doRuleBook ?
                            makeIterator(new RulesScene(), new InstantaneousScene() {
                                public void execute() {
                                    doRuleBook = false;
                                    finished = false;
                                }
                            }, this)
                            : makeIterator();
                }
            },
            new StepScene(() -> g, false, "Now we will go over the process of playing the game") {},
            new StepScene(() -> g, false, "Each round starts with tile distribution") {
                @Override
                public Iterator<? extends AbstractScene> getScenesAfter() {
                    return makeIterator(new TileDistributionScene(game));
                }
            },
            new StepScene(() -> g, false, "Then, players take turns filling their pattern lines") {
                @Override
                public Iterator<? extends AbstractScene> getScenesAfter() {
                    return makeLoopIterator(
                            new Supplier<>() {
                                int player = 0;

                                @Override
                                public AbstractScene get() {
                                    Player p = game.getPlayers().get(player++ % 2);
                                    return new ComputerTurnScene(game, p);
                                }
                            },
                            () -> !(
                                    game.getMiddle().getFactories().stream().map(AbstractTileSet::getAllTiles).allMatch(List::isEmpty)
                                            && game.getMiddle().getCenter().getAllTiles().isEmpty()
                            )
                    );
                }
            },
            new StepScene(() -> g, true, "You can move around the scene by dragging while right clicking"){},
            new StepScene(() -> g, false, "When the factories are empty, the scoring will start.") {},
            new StepScene(() -> g, false, "The tiles will automatically be scored as they move from the line to the wall") {
                @Override
                public Iterator<? extends AbstractScene> getScenesAfter() {
                    return makeIterator(
                            new ScoringScene(game, game.getPlayers().get(0)),
                            new ScoringScene(game, game.getPlayers().get(1))
                    );
                }
            },
            new StepScene(() -> g, false, "See the rulebook for more details on scoring", "Click for rulebook") {

                boolean doRuleBook = false;

                @Override
                public void onMouseClick(MouseEvent me) {
                    if (finished = doRuleBook = infoPanel.get(1).getComponent(ButtonComponent.class).contains(me.position))
                        return;
                    super.onMouseClick(me);
                }

                @Override
                public Iterator<? extends AbstractScene> getScenesAfter() {
                    return doRuleBook ?
                            makeIterator(new RulesScene(), new InstantaneousScene() {
                                public void execute() {
                                    doRuleBook = false;
                                    finished = false;
                                }
                            }, this)
                            : makeIterator();
                }
            },
            new StepScene(() -> g, false, "When a player has completed a row, the game ends.") {
                @Override
                public void onExecutionStart() {
                    super.onExecutionStart();
                    {
                        Wall wall = g.getPlayers().get(1).getWall();
                        for (Tile.TileColor color : Tile.allColors) {
                            if (wall.rowHasTileColor(0, color)) continue;
                            Tile t = new Tile(color);
                            t.highlight();
                            wall.placeTile(0, t);
                        }
                        for (int r = 0; r < 5; r++) {
                            if (wall.rowHasTileColor(r, Tile.TileColor.BLACK)) continue;
                            wall.placeTile(r, new Tile(Tile.TileColor.BLACK));
                        }
                    }
                    {
                        Wall wall = g.getPlayers().get(0).getWall();
                        for (Tile.TileColor color : Tile.allColors) {
                            if (wall.rowHasTileColor(2, color)) continue;
                            wall.placeTile(2, new Tile(color));
                        }
                        for (int r = 0; r < 5; r++) {
                            Tile.TileColor color = Tile.TileColor.BLACK;
                            for(Tile.TileColor c : Tile.allColors){
                                if(wall.getCol(r, c) == 1){
                                    color = c;
                                    break;
                                }
                            }
                            if (wall.rowHasTileColor(r, color)) continue;
                            wall.placeTile(r, new Tile(color));
                        }
                    }
                }

                @Override
                public void onExecutionEnd() {
                    Arrays.stream(game.getPlayers().get(1).getWall().getGrid()).flatMap(Arrays::stream).filter(Objects::nonNull).forEach(Tile::unHighlight);
                }
            },
            new StepScene(() -> g, false, "Row, Column, and Color bonuses are calculated."){
                @Override
                public Iterator<? extends AbstractScene> getScenesAfter() {
                    return makeLoopIterator(g.getPlayers(), player -> new BonusCalculationScene(player, g));
                }
            },
            new StepScene(() -> g, false, "The player with the highest score wins."){},
            new StepScene(() -> g, false, "Have fun playing!"){}
        );
    }
}
