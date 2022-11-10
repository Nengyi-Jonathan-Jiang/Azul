package game.scenes;

import engine.components.ButtonComponent;
import engine.components.PositionAnimationComponent;
import engine.core.AbstractScene;
import engine.core.GameCanvas;
import engine.core.GameObject;
import engine.core.Vec2;
import game.backend.*;

import java.awt.event.MouseEvent;
import java.util.*;
import game.App;
import game.frontend.TextObject;

// TODO
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
        Wall wall = player.getWall();
        List<Integer> filledRows = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            if(patternLines.get(i).isFilled()){
                filledRows.add(i);
            }
        }

        return concatIterators(
                makeIterator(new WaitScene(game, 10)),
                makeLoopIterator(filledRows, row -> groupScenes(
                    new ActionScene(() -> {
                        PatternLine line = patternLines.get(row);
                        Tile tile = line.popFirstTile();
                        List<Tile> rest = line.popAllTiles();
                        rest.stream().map(Tile::getGameObject).forEach(GameObject::removeFromParent);
                        rest.forEach(game.getBag()::returnTile);

                        Vec2 originalPos = tile.getGameObject()
                                .getAbsolutePosition()
                                .minus(wall.getGameObject().getAbsolutePosition());

                        // Move t to wall
                        PlaceTileResult placeTileResult = wall.placeTile(row, tile);

                        Vec2 targetPos = tile.getGameObject().getPosition();

                        tile.getGameObject().setPosition(originalPos);
                        tile.getGameObject().getComponent(PositionAnimationComponent.class).moveTo(targetPos, 10);

                        int score = player.getScoreMarker().getScore();
                        int newScore = score + placeTileResult.getScoreAdded();

                        System.out.println(placeTileResult.getScoreAdded());

                        player.getScoreMarker().setScore(newScore);
                    }),
                    new WaitScene(game, 50),
                    new AbstractScene(){

                    }
            )), makeIterator(new AbstractScene(){
                private TextObject continueButton;
                private boolean finished = false;

                @Override
                public void onExecutionStart() {
                    continueButton = new TextObject("Continue");
                    continueButton.setBottomRight(new Vec2(App.WIDTH, App.HEIGHT));
                }

                @Override
                public void onMouseClick(MouseEvent me) {
                    if(continueButton.getComponent(ButtonComponent.class).contains(me)){
                        finished = true;
                    }
                }

                @Override
                public void draw(GameCanvas canvas) {
                    game.getGameObject().draw(canvas);
                    continueButton.draw(canvas);
                }

                @Override
                public boolean isFinished() {
                    return finished;
                }
            })
        );
    }
}
