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
import java.util.concurrent.atomic.AtomicReference;

import game.App;
import game.frontend.TextObject;
import game.util.PositionAnimation;

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
            makeLoopIterator(filledRows, row -> new AbstractScene(){
                AtomicReference<PlaceTileResult> placeTileResult;
                AtomicReference<Tile> placedTile;
                @Override
                public Iterator<? extends AbstractScene> getScenesAfter() {
                    return makeIterator(
                        new ActionScene(() -> {
                            PatternLine line = patternLines.get(row);
                            placedTile = new AtomicReference<>(line.popFirstTile());
                            List<Tile> rest = line.popAllTiles();
                            rest.stream().map(Tile::getGameObject).forEach(GameObject::removeFromParent);
                            rest.forEach(game.getBag()::returnTile);

                            placeTileResult = new AtomicReference<>();
                            PositionAnimation.animate(
                                    placedTile.get().getGameObject(),
                                    () -> placeTileResult.set(wall.placeTile(row, placedTile.get())),
                                    10
                            );

                        }),
                        new WaitScene(game, 10),
                        new ActionScene(() -> {
                            int score = player.getScoreMarker().getScore();
                            int newScore = score + placeTileResult.get().getScoreAdded();

                            player.getScoreMarker().setScore(newScore);

                            placeTileResult.get().getTiles().forEach(Tile::highlight);
                            placedTile.get().highlight();
                        }),
                        new WaitScene(game, 40),
                        new ActionScene(() -> {
                            placeTileResult.get().getTiles().forEach(Tile::unHighlight);
                            placedTile.get().unHighlight();
                        })
                    );
                }
            }),
            makeIterator(
                new ActionScene(() -> {
                    int scoreDeduction = player.getFloorLine().getDeduction();

                    int score = player.getScoreMarker().getScore();
                    int newScore = score - scoreDeduction;

                    player.getScoreMarker().setScore(newScore);

                    List<Tile> removedTiles = player.getFloorLine().removeAll();

                    Optional<Tile> optionalFirstPlayerMarker = removedTiles.stream().filter(Tile::isFirstPlayerMarker).findAny();
                    if(optionalFirstPlayerMarker.isPresent()){
                        Tile firstPlayerMarker = optionalFirstPlayerMarker.get();
                        removedTiles.remove(firstPlayerMarker);

                        PositionAnimation.animate(firstPlayerMarker.getGameObject(), () -> player.setFirstPlayerTile(firstPlayerMarker), 10);
                    }

                    removedTiles.stream().map(Tile::getGameObject).forEach(GameObject::removeFromParent);
                    removedTiles.forEach(game.getBag()::returnTile);
                }),
                new WaitScene(game, 10),
                new AbstractScene(){
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
                }
            )
        );
    }
}
