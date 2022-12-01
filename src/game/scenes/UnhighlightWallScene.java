package game.scenes;

import engine.core.InstantaneousScene;
import game.backend.player.Player;
import game.backend.Tile;
import game.backend.player.Wall;

import java.util.Arrays;
import java.util.Objects;

public class UnhighlightWallScene extends InstantaneousScene {
    private final Player player;

    public UnhighlightWallScene(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        Wall wall = player.getWall();
        Arrays.stream(wall.getGrid()).flatMap(Arrays::stream).filter(Objects::nonNull).forEach(Tile::unHighlight);
    }
}
