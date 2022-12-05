import game.backend.player.Player;
import game.backend.Tile;

public class WallCompleteRowTest {
    public static void main(String[] args){
        Player p = new Player("Player1", 1, null);
        p.getWall().placeTile(0, new Tile(Tile.TileColor.BLUE));
        p.getWall().placeTile(0, new Tile(Tile.TileColor.BLACK));

        System.out.println(p.getWall());

        System.out.println(p.getWall().hasCompletedRow());

        p.getWall().placeTile(0, new Tile(Tile.TileColor.SNOW));
        p.getWall().placeTile(0, new Tile(Tile.TileColor.RED));
        p.getWall().placeTile(0, new Tile(Tile.TileColor.ORANGE));

        System.out.println(p.getWall());

        System.out.println(p.getWall().hasCompletedRow());
    }
}
