package game.scenes;

import engine.components.PositionAnimationComponent;
import engine.core.AbstractScene;
import engine.core.Vec2;
import game.backend.*;

import java.util.*;

import game.App;

public class ScoringScene extends AbstractScene {
    private final Game game;
    private final Player player;

    public ScoringScene(Game g, Player p) {
        game = g;
        player = p;
    }

    @Override
    public void onExecutionStart() {
        game.getGameObject().getComponent(PositionAnimationComponent.class).moveTo(
            player.getGameObject().getPosition().scaledBy(-1).plus(new Vec2(App.WIDTH, App.HEIGHT).scaledBy(.5)),
            10
        );
    }

    @Override
    public Iterator<? extends AbstractScene> getScenesAfter() {
        List<PatternLine> patternLines = player.getPatternLines().getLines();
        List<Integer> filledRows = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            if(!patternLines.get(i).canAddTile()){
                filledRows.add(i);
            }
        }

        return concatIterators(
            makeIterator(new WaitScene(game, 10)),
            makeLoopIterator(filledRows, row -> new PatternLineScoringScene(game, player, row)),
            makeIterator(
                new FloorLineDeductionScene(game, player),
                new WaitScene(game, 10),
                new ScoringConfirmationScene(game)
            )
        );
    }

}
