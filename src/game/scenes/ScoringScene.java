package game.scenes;

import engine.components.PositionAnimationComponent;
import engine.core.AbstractScene;
import engine.core.InstantaneousScene;
import engine.core.Vec2;
import game.App;
import game.backend.Game;
import game.backend.player.PatternLine;
import game.backend.player.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ScoringScene extends InstantaneousScene {
    private final Game game;
    private final Player player;

    public ScoringScene(Game g, Player p) {
        game = g;
        player = p;
    }

    @Override
    public void execute() {
        game.getGameObject().getComponent(PositionAnimationComponent.class).moveTo(
                player.getGameObject().getPosition().scaledBy(-.7),
                10
        );
    }

    @Override
    public Iterator<? extends AbstractScene> getScenesAfter() {
        List<PatternLine> patternLines = player.getPatternLines().getLines();
        List<Integer> filledRows = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            if (patternLines.get(i).canAddTile()) {
                filledRows.add(i);
            }
        }

        return concatIterators(
                makeIterator(new WaitScene(game, 10, "Scoring " + player, null)),
                makeLoopIterator(filledRows, row -> new PatternLineScoringScene(game, player, row)),
                makeIterator(
                        new FloorLineDeductionScene(game, player),
                        new WaitScene(game, 10, "Scoring " + player, null),
                        new ScoringConfirmationScene(game, player)
                )
        );
    }

}
