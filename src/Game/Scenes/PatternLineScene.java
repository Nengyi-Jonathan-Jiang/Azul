package Game.Scenes;

import Engine.Components.ButtonComponent;
import Engine.Components.PositionAnimationComponent;
import Engine.Core.Vec2;
import Game.Backend.*;

import java.awt.event.MouseEvent;
import java.util.List;

public class PatternLineScene extends PanningGameScene {
    private final Player player;
    private boolean finished = false;

    private List<PatternLine> availableLines;

    public PatternLineScene(Game game, Player player) {
        super(game);
        this.player = player;
    }

    @Override
    public void onExecutionStart() {
        Hand hand = player.getHand();

        System.out.println(hand.getTiles());

        availableLines = player.getAvailablePatternLinesForColor(
            hand.getTiles().stream().filter(Tile::isColorTile).findFirst().get().getColor()
        );

        availableLines.forEach(PatternLine::highlight);

        if(availableLines.isEmpty()){
            for(Tile tile : player.getHand().getTiles()){

                Vec2 originalPos = tile.getGameObject()
                        .getAbsolutePosition()
                        .minus(player.getFloorLine().getGameObject().getAbsolutePosition());

                player.getFloorLine().push(tile);

                Vec2 targetPos = tile.getGameObject().getPosition();

                tile.getGameObject().setPosition(originalPos);
                tile.getGameObject().getComponent(PositionAnimationComponent.class).moveTo(targetPos, 10);
            }

            finished = true;
        }
    }

    @Override
    public void onMouseClick(MouseEvent me) {
        for(PatternLine line : availableLines){
            if(line.getGameObject().getComponent(ButtonComponent.class).contains(me)){

                for(Tile tile : player.getHand().getTiles()){

                    Vec2 originalPos = tile.getGameObject()
                            .getAbsolutePosition();

                    if(line.isFilled() || tile.isFirstPlayerMarker()){
                        player.getFloorLine().push(tile);
                        originalPos = originalPos.minus(player.getFloorLine().getGameObject().getAbsolutePosition());
                    }
                    else{
                        line.addTile(tile);
                        originalPos = originalPos.minus(line.getGameObject().getAbsolutePosition());
                    }

                    Vec2 targetPos = tile.getGameObject().getPosition();

                    tile.getGameObject().setPosition(originalPos);
                    tile.getGameObject().getComponent(PositionAnimationComponent.class).moveTo(targetPos, 10);
                }

                finished = true;
                availableLines.forEach(PatternLine::unhighlight);
                break;
            }
        }
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
