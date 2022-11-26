package game.scenes;

import engine.components.PositionAnimationComponent;
import engine.core.AbstractScene;
import engine.core.GameObject;
import engine.core.Vec2;
import game.backend.Game;
import game.backend.Tile;
import game.backend.ai.ComputerMove;
import game.backend.player.FloorLine;
import game.backend.player.Player;
import game.util.PositionAnimation;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ComputerFactoryOfferingScene extends AbstractScene {
    private final Game game;
    private final Player player;
    public ComputerFactoryOfferingScene(Game game, Player player) {
        this.game = game;
        this.player = player;
    }

    @Override
    public void onExecutionStart() {
        ComputerMove move = this.player.getMove(game);

        System.out.println("Move Tiles: " + move.selectedTiles);

        // Remove selected tiles from tileset
        move.selectedTileSet.removeTilesOfColor(move.color);

        // Animate tile movement to pattern line
        for (Tile tile : move.selectedTiles) {
            if (move.selectedLine instanceof FloorLine || !move.selectedLine.canAddTile() || tile.isFirstPlayerMarker()) {
                if (player.getFloorLine().isFull()) {
                    game.getBag().returnTile(tile);
                    tile.getGameObject().removeFromParent();

                    System.out.println("Floor line overflow on tile " + tile);
                } else {
                    PositionAnimation.animate(tile.getGameObject(), () -> player.getFloorLine().addTile(tile), 10);

                    System.out.println("Pattern line overflow on tile " + tile);
                }
            } else {
                PositionAnimation.animate(tile.getGameObject(), () -> move.selectedLine.addTile(tile), 10);

                System.out.println("Moved " + tile + " to pattern line");
            }
        }

        // Animate tile movement to center
        List<Tile> removed =
                Stream.concat(
                        move.selectedTileSet.removeAllTiles().stream(),
                        game.getMiddle().getCenter().removeAllTiles().stream()
                )
                        .sorted(Tile::compareTo)
                        .collect(Collectors.toList());
        List<Vec2> originalPositions = removed.stream().map(Tile::getGameObject).map(GameObject::getAbsolutePosition).collect(Collectors.toList());
        removed.stream().map(Tile::getGameObject).forEach(GameObject::removeFromParent);

        for (int i = 0; i < removed.size(); i++) {
            Tile tile = removed.get(i);

            Vec2 originalPos = originalPositions.get(i);

            game.getMiddle().getCenter().addTile(tile);

            Vec2 targetPos = tile.getGameObject().getPosition();

            Vec2 newPosition = tile.getGameObject().getAbsolutePosition();
            Vec2 delta = newPosition.minus(originalPos);

            tile.getGameObject().setPosition(targetPos.minus(delta));
            tile.getGameObject().getComponent(PositionAnimationComponent.class).moveTo(targetPos, 10);
        }
    }
}
