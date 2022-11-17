package game.scenes;

import engine.core.AbstractScene;
import engine.core.GameObject;
import game.backend.*;
import game.util.PositionAnimation;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

class PatternLineScoringScene extends AbstractScene {
    private final List<PatternLine> patternLines;
    private final Integer row;
    private final Wall wall;
    private final Game game;
    private final Player player;
    AtomicReference<PlaceTileResult> placeTileResult;
    AtomicReference<Tile> placedTile;

    public PatternLineScoringScene(Game game, Player player, Wall wall, Integer row, List<PatternLine> patternLines) {
        this.patternLines = patternLines;
        this.row = row;
        this.wall = wall;
        this.game = game;
        this.player = player;
    }

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
}
