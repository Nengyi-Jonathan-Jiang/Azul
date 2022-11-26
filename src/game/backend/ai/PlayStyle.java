package game.backend.ai;

import game.backend.Game;
import game.backend.player.Player;

public abstract class PlayStyle {
    public abstract ComputerMove getMove(Player p, Game game);
}
