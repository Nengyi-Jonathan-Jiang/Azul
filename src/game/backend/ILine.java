package game.backend;

import engine.core.GameObject;

public interface ILine {
    void addTile(Tile t);
    void highlight();
    void highlight2();
    void unHighlight();
    GameObject getGameObject();

    boolean canAddTile();
}
