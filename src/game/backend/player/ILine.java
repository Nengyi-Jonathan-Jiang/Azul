package game.backend.player;

import engine.core.GameObject;
import game.backend.Tile;

public interface ILine {
    void addTile(Tile t);

    void highlight();

    void highlight2();

    void unHighlight();

    GameObject getGameObject();

    boolean canAddTile();
}
