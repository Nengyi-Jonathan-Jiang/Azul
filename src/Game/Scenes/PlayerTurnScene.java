package Game.Scenes;

import Engine.Components.PositionAnimationComponent;
import Engine.Core.AbstractScene;
import Engine.Core.GameCanvas;
import Engine.Core.Vec2;
import Game.Backend.Game;
import Game.Backend.Player;
import Game.App;

import java.util.Iterator;

// TODO
public class PlayerTurnScene extends AbstractScene {
    public Game game;
    public Player player;

    public PlayerTurnScene(Game game, Player player) {
        this.game = game;
        this.player = player;
    }


    @Override
    public void onExecutionStart() {
        game.getGameObject().getComponent(PositionAnimationComponent.class).moveTo(
                player.getGameObject().getPosition().scaledBy(-.2, -.5).plus(new Vec2(App.WIDTH, App.HEIGHT).scaledBy(.5)),
                10
        );
    }

    private int animation = 0;

    @Override
    public void update() {
        animation++;
    }

    @Override
    public void draw(GameCanvas canvas) {
        game.getGameObject().draw(canvas);
    }

    @Override
    public boolean isFinished() {
        return animation > 10;
    }

    @Override
    public Iterator<AbstractScene> getScenesAfter() {
        return PanningGameScene.makeIterator(new FactoryOfferingScene(game, player), new PatternLineScene(game, player));
    }
}
