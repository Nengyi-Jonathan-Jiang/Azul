package Game.Frontend;

import Engine.Components.RectRendererComponent;
import Engine.Components.TextRendererComponent;
import Engine.Components.TextStyle;
import Engine.Core.GameObject;
import Engine.Core.Vec2;
import Game.Style;

public class TextObject extends GameObject {

    public TextObject(){
        this("");
    }

    public TextObject(String text){
        super(
                new RectRendererComponent(Style.FG_COLOR, Style.BG_COLOR),
                new TextRendererComponent(text, new TextStyle(Style.font_medium, Style.FG_COLOR, TextStyle.ALIGN_CENTER))
        );
    }

    public void setText(String text){
        getComponent(TextRendererComponent.class).setText(text);
        Vec2 TEXT_SIZE = getComponent(TextRendererComponent.class)
                .getRenderedSize()
                .plus(new Vec2(Style.TEXT_PADDING * 2.5));
        setSize(TEXT_SIZE);
    }
}
