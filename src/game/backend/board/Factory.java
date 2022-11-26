package game.backend.board;

import engine.core.GameObject;
import game.frontend.FactoryGameObject;

public class Factory extends AbstractTileSet {
    @Override
    protected GameObject createGameObject() {
        return new FactoryGameObject();
    }
}
