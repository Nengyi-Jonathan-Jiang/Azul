package engine.components;

import engine.core.GameCanvas;
import engine.core.Vec2;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/** @noinspection UnusedReturnValue, unused */
public class TextRendererComponent extends Component {
    protected static final TextStyle DEFAULT_STYLE = new TextStyle();

    protected String text;
    protected final TextStyle style;
    private Vec2 textOffset;

    public Vec2 getRenderedSize(){
        Rectangle2D size = style.font.getStringBounds(text, new FontRenderContext(new AffineTransform(),true,true));
        return new Vec2(size.getWidth(), style.font.getSize());
    }

    public TextRendererComponent(String text){
        this(text, DEFAULT_STYLE);
    }

    public TextRendererComponent(String text, Font font){
        this(text, new TextStyle(font));
    }

    public TextRendererComponent(String text, TextStyle style){
        this.text = text;
        this.style = style;
        recalculate_text_position();
    }

    public TextRendererComponent setText(String text){
        this.text = text;
        recalculate_text_position();
        return this;
    }
    public String getText(){
        return text;
    }

    public Font getFont(){
        return style.font;
    }

    public TextStyle getStyle(){
        return style;
    }

    private void recalculate_text_position(){
        textOffset = new Vec2(
            -switch(style.getHorizontalAlignment()){
                case START -> 0;
                case CENTER -> getRenderedSize().x / 2;
                case END -> getRenderedSize().x;
            },
            switch(style.getVerticalAlignment()){
                case START -> style.font.getSize();
                case CENTER -> (int)(style.font.getSize() * .4);
                case END -> 0;
            }
        );
    }

    @Override
    public void drawAndUpdate(GameCanvas canvas) {
        Vec2 rect_start = gameObject.getAbsoluteTopLeft();
        Vec2 text_start = rect_start.plus(textOffset).plus(new Vec2(
            switch(style.getHorizontalAlignment()){
                case START -> 0;
                case CENTER -> gameObject.getSize().x() / 2.;
                case END -> gameObject.getSize().x();
            },
            switch(style.getVerticalAlignment()){
                case START -> 0;
                case CENTER -> gameObject.getSize().y() / 2.;
                case END -> gameObject.getSize().y();
            })
        );

        canvas
            .setColor(style.bg_color)
            .fillRect(rect_start, gameObject.getSize())

            .setColor(style.fg_color)
            .setDrawFont(style.font)
            .drawText(text, text_start);
    }
}
