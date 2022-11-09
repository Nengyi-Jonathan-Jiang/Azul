package Game.Scenes;

import Engine.Components.PositionAnimationComponent;
import Engine.Core.AbstractScene;
import Engine.Core.GameCanvas;
import Engine.Core.GameObject;
import Engine.Core.Vec2;
import Game.Backend.*;

import java.util.*;
import Game.App;

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

    private int animation = 0;

    @Override
    public void update() {
        animation++;
    }

    @Override
    public void draw(GameCanvas canvas) {
        game.getGameObject().draw(canvas);
    }

    @Override
    public boolean isFinished() {
        return animation > 10;
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

        return concatIterators(makeLoopIterator(filledRows, row -> groupScenes(
                new ActionScene(() -> {
                    PatternLine line = patternLines.get(row);
                    Tile tile = line.popFirstTile();
                    List<Tile> rest = line.popAllTiles();
                    rest.stream().map(Tile::getGameObject).forEach(GameObject::removeFromParent);


                    Vec2 originalPos = tile.getGameObject()
                            .getAbsolutePosition()
                            .minus(wall.getGameObject().getAbsolutePosition());

                    // Move t to wall
                    PlaceTileResult placeTileResult = wall.placeTile(row, tile);

                    Vec2 targetPos = tile.getGameObject().getPosition();

                    tile.getGameObject().setPosition(originalPos);
                    tile.getGameObject().getComponent(PositionAnimationComponent.class).moveTo(targetPos, 10);

                    System.out.println("animate");
                }),
                new WaitScene(game, 50),
                new AbstractScene(){

                }
        )),makeIterator(new AbstractScene(){
            private GameObject continueButton;

            @Override
            public void onExecutionStart() {

            }
        }));
    }
}
