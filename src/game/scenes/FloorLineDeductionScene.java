package game.scenes;

import engine.core.GameObject;
import engine.core.InstantaneousScene;
import game.backend.Game;
import game.backend.player.Player;
import game.backend.Tile;
import game.util.PositionAnimation;

import java.util.List;
import java.util.Optional;

class FloorLineDeductionScene extends InstantaneousScene {
    private final Game game;
    private final Player player;

    FloorLineDeductionScene(Game game, Player player) {
        this.game = game;
        this.player = player;
    }

    @Override
    public void execute() {
        int scoreDeduction = player.getFloorLine().getDeduction();

        int score = player.getScoreMarker().getScore();
        int newScore = score - scoreDeduction;

        player.getScoreMarker().setScore(newScore);

        List<Tile> removedTiles = player.getFloorLine().removeAll();

        Optional<Tile> optionalFirstPlayerMarker = removedTiles.stream().filter(Tile::isFirstPlayerMarker).findAny();
        if (optionalFirstPlayerMarker.isPresent()) {
            Tile firstPlayerMarker = optionalFirstPlayerMarker.get();
            removedTiles.remove(firstPlayerMarker);

            PositionAnimation.animate(firstPlayerMarker.getGameObject(), () -> player.setFirstPlayerTile(firstPlayerMarker), 10);
        }

        removedTiles.stream().map(Tile::getGameObject).forEach(GameObject::removeFromParent);
        removedTiles.forEach(game.getBag()::returnTile);
    }
}