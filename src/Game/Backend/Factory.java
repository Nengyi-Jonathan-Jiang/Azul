package Game.Backend;

import Engine.Core.GameObject;
import Game.Frontend.FactoryGameObject;

public class Factory extends AbstractTileSet {
    @Override
    protected GameObject createGameObject() {
        return new FactoryGameObject();
    }
}
