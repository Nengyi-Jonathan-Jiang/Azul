package game.frontend;

import engine.components.ButtonComponent;
import engine.components.RectRendererComponent;
import engine.components.TextRendererComponent;
import engine.components.TextStyle;
import engine.core.GameObject;
import engine.core.Vec2;
import game.Style;

public class TextObject extends GameObject {
    public TextObject(String text){
        super(
                new RectRendererComponent(Style.FG_COLOR, Style.BG_COLOR),
                new TextRendererComponent(text, new TextStyle(Style.font_medium, Style.FG_COLOR, TextStyle.ALIGN_CENTER)),
                new ButtonComponent()
        );
        setText(text);
    }

    public void setText(String text){
        getComponent(TextRendererComponent.class).setText(text);
        Vec2 TEXT_SIZE = getComponent(TextRendererComponent.class)
                .getRenderedSize()
                .plus(new Vec2(Style.TEXT_PADDING * 2.5));
        setSize(TEXT_SIZE);
    }
}