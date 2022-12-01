package game.frontend;

import engine.components.ButtonComponent;
import engine.components.RectRendererComponent;
import engine.components.TextRendererComponent;
import engine.components.TextStyle;
import engine.core.GameObject;
import game.Style;

public class TextObject extends GameObject {
    public TextObject(String text) {
        this(text, new TextStyle(Style.font_medium, Style.FG_COLOR, TextStyle.ALIGN_CENTER));
    }

    public TextObject(String text, TextStyle style) {
        super(
                new RectRendererComponent(Style.FG_COLOR, Style.BG_COLOR),
                new TextRendererComponent(text, style),
                new ButtonComponent()
        );
        setText(text);
    }

    public RectRendererComponent getRectComponent() {
        return getComponent(RectRendererComponent.class);
    }

    public void setText(String text) {
        getComponent(TextRendererComponent.class).setText(text).shrinkParentToFit(
                Style.TEXT_PADDING * 1.25
        );
    }

    public String getText(){
        return getComponent(TextRendererComponent.class).getText();
    }
}