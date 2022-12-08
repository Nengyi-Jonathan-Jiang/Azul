package game.frontend;

import engine.components.*;
import engine.core.GameObject;
import engine.core.Vec2;
import game.Style;
import game.backend.Tile;

public class TileToolTipObject extends GameObject {
    private final Tile.TileColor color;
    private final int count;
    private final boolean hasFirstPlayerTile;

    private final GameObject text;
    private final GameObject tile;
    private final GameObject firstPlayerTile;

    public TileToolTipObject(Tile.TileColor color, int count, boolean hasFirstPlayerTile){
        addComponent(new RectRendererComponent(Style.FG_COLOR, Style.BG_COLOR));

        this.color = color;
        this.count = count;
        this.hasFirstPlayerTile = hasFirstPlayerTile;

        text = new GameObject(new TextRendererComponent("x " + count,
                new TextStyle(Style.font_d.deriveFont(15f), TextStyle.ALIGN_CENTER)
        ));
        text.getComponent(TextRendererComponent.class).shrinkParentToFit(Style.PADDING);

        tile = new GameObject(new Vec2(15), new ImageRendererComponent(Tile.getTileColorName(color)));
        firstPlayerTile = new GameObject(new Vec2(15), new ImageRendererComponent("first"));

        double width = text.size().x + tile.size().x + Style.PADDING;

        if(!hasFirstPlayerTile) firstPlayerTile.disable();
        else width += firstPlayerTile.size().x + Style.PADDING;

        resize(new Vec2(width, text.size().y));
        addChildren(text, tile, firstPlayerTile);

        text.setTopRight(getTopRightOffset());
        tile.setTopRight(getTopRightOffset().plus(new Vec2(-text.size().x, Style.PADDING)));
        firstPlayerTile.setTopRight(getTopRightOffset().plus(new Vec2(-text.size().x - tile.size().x - Style.PADDING, Style.PADDING)));
    }
}
