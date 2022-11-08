package Game.Scenes;

import Engine.Core.AbstractScene;
import Engine.Core.GameCanvas;
import Game.Backend.Game;
import Game.Backend.PatternLine;
import Game.Backend.Player;
import Game.Backend.Wall;

import java.util.*;

// TODO
public class ScoringScene extends AbstractScene {
    private final Game game;
    private final Player player;

    public ScoringScene(Game g, Player p) {
        game = g;
        player = p;
    }

    @Override
    public Iterator<? extends AbstractScene> getScenesAfter() {
        List<PatternLine> patternLines = player.getPatternLines().getLines();
        Wall wall = player.getWall();

        List<Integer> filledRows = new ArrayList<>();

        return AbstractScene.makeLoopIterator(filledRows, row -> AbstractScene.groupScenes(
                new AbstractGameScene(game){
                    private int animationTime = 0;

                    @Override
                    public void onExecutionStart() {
                        PatternLine line = patternLines.get(row);

                    }

                    @Override
                    public void update() {
                        animationTime++;
                    }

                    @Override
                    public boolean isFinished() {
                        return animationTime > 10;
                    }
                }, new AbstractGameScene(game){

                }
        ));
    }
}
