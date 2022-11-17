package game.scenes;

import engine.core.AbstractScene;
import game.backend.WallScoreResult;
import game.backend.Player;
import game.backend.Tile;

class ScoreMarkerMovementScene extends AbstractScene {
    private final Player player;
    private final Tile placedTile;
    private final WallScoreResult result;

    ScoreMarkerMovementScene(Player player, Tile placedTile, WallScoreResult result) {
        this.player = player;
        this.placedTile = placedTile;
        this.result = result;
    }

    @Override
    public void onExecutionStart() {
        int score = player.getScoreMarker().getScore();
        int newScore = score + result.getScoreAdded();

        player.getScoreMarker().setScore(newScore);

        result.getTiles().forEach(Tile::highlight);

        if(placedTile != null) placedTile.highlight();
    }
}
