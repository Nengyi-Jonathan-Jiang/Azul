package game.frontend;

import engine.components.ButtonComponent;
import engine.components.RectRendererComponent;
import engine.components.TextRendererComponent;
import engine.components.TextStyle;
import engine.core.GameObject;
import engine.core.Vec2;
import game.Style;

import java.util.function.Supplier;

import java.awt.Color;

public class ButtonObject extends GameObject {
    public ButtonObject(Vec2 pos, Vec2 size, String text, TextStyle style, Supplier<RectRendererComponent> c){
        super(
                c.get(),
                new TextRendererComponent(text, style),
                new ButtonComponent()
        );
        getComponent(TextRendererComponent.class).setText(text);
        setPosition(pos);
        if(size == null){
            Vec2 TEXT_SIZE = getComponent(TextRendererComponent.class)
                    .getRenderedSize()
                    .plus(new Vec2(Style.TEXT_PADDING * 2.5));
            setSize(TEXT_SIZE);
        }
        else setSize(size);
    }

    public boolean contains(Vec2 v){
        return getComponent(ButtonComponent.class).contains(v);
    }

    public void setBorder(Color c){
        getComponent(RectRendererComponent.class).setBorderColor(c);
    }

    public void setFill(Color c){
        getComponent(RectRendererComponent.class).setFillColor(c);
    }
}
