package Game.Scenes;

import Engine.Components.RectRendererComponent;
import Engine.Components.TextRendererComponent;
import Engine.Components.TextStyle;
import Engine.Core.AbstractScene;
import Engine.Core.GameCanvas;
import Engine.Core.GameObject;
import Engine.Core.Vec2;
import Game.Backend.Game;
import Game.Backend.Player;
import Game.Style;
import Game.App;

public class FactoryOfferingScene extends AbstractGameScene{
    private final GameObject playerTurnIndicator;
    private GameObject instructions;

    public FactoryOfferingScene(Game game, Player player) {
        super(game);

        playerTurnIndicator = new GameObject(
            new RectRendererComponent(Style.FG_COLOR, Style.BG_COLOR),
            new TextRendererComponent(player.getName() + "'s Turn", new TextStyle(Style.font_medium, Style.FG_COLOR, TextStyle.ALIGN_CENTER))
        );
        Vec2 TEXT_SIZE = playerTurnIndicator.getComponent(TextRendererComponent.class).getRenderedSize()
                .plus(new Vec2(Style.TEXT_PADDING * 2.5));
        playerTurnIndicator.setSize(TEXT_SIZE);

        playerTurnIndicator.setTopLeft(Vec2.zero);

        instructions = new GameObject(
                new RectRendererComponent(Style.FG_COLOR, Style.BG_COLOR),
                new TextRendererComponent("", new TextStyle(Style.font_medium, Style.FG_COLOR, TextStyle.ALIGN_CENTER))
        );

        setInstructions("Click on a tile in the factories or the center to select it");
    }

    public void setInstructions(String text){
        instructions.getComponent(TextRendererComponent.class).setText(text);
        Vec2 TEXT_SIZE = instructions.getComponent(TextRendererComponent.class)
                .getRenderedSize()
                .plus(new Vec2(Style.TEXT_PADDING * 2.5));
        instructions.setSize(TEXT_SIZE);
        instructions.setTopRight(new Vec2(App.WIDTH, 0));
    }

    @Override
    public void draw(GameCanvas canvas) {
        super.draw(canvas);
        playerTurnIndicator.draw(canvas);
        instructions.draw(canvas);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
