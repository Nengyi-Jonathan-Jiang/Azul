package game.scenes;

import engine.components.ButtonComponent;
import engine.core.Vec2;
import game.backend.*;
import game.util.PositionAnimation;

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

        //noinspection OptionalGetWithoutIsPresent
        availableLines = player.getAvailablePatternLinesForColor(
            hand.getTiles().stream().filter(Tile::isColorTile).findFirst().get().getColor()
        );

        availableLines.forEach(PatternLine::highlight);

        if(availableLines.isEmpty()){
            for(Tile tile : player.getHand().getTiles()){
                if(player.getFloorLine().isFull()){
                    game.getBag().returnTile(tile);
                    tile.getGameObject().removeFromParent();
                }
                else {
                    PositionAnimation.animate(tile.getGameObject(), ()-> player.getFloorLine().push(tile), 10);
                }
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
                        if(player.getFloorLine().isFull()){
                            game.getBag().returnTile(tile);
                            tile.getGameObject().removeFromParent();
                        }
                        else {
                            PositionAnimation.animate(tile.getGameObject(), () -> player.getFloorLine().push(tile), 10);
                        }
                    }
                    else{
                        PositionAnimation.animate(tile.getGameObject(), () -> line.addTile(tile), 10);
                    }
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
