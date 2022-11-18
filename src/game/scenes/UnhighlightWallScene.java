package game.scenes;

import engine.core.AbstractScene;
import game.backend.Player;
import game.backend.Tile;
import game.backend.Wall;

import java.util.Arrays;
import java.util.Objects;

public class UnhighlightWallScene extends AbstractScene {
    private final Player player;

    public UnhighlightWallScene(Player player) {
        this.player = player;
    }

    @Override
    public void onExecutionStart() {
        Wall wall = player.getWall();
        Arrays.stream(wall.getGrid()).flatMap(Arrays::stream).filter(Objects::nonNull).forEach(Tile::unHighlight);
    }
}
