package Engine.UI;

import Engine.Core.GameCanvas;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class TextComponent extends UIComponent {
    protected static final TextStyle DEFAULT_STYLE = new TextStyle();

    protected String text;
    protected final TextStyle style;
    protected int offsetX, offsetY;

    public int getRenderedWidth(){
        Rectangle2D size = style.font.getStringBounds(text, new FontRenderContext(new AffineTransform(),true,true));
        return (int) size.getWidth();
    }

    public TextComponent(String text){
        this(text, DEFAULT_STYLE);
    }

    public TextComponent(String text, Font font){
        this(text, new TextStyle(font));
    }

    public TextComponent(String text, TextStyle style){
        this.text = text;
        this.style = style;
        recalculate_text_position();
    }

    public TextComponent setText(String text){
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
        offsetX = -switch(style.getHorizontalAlignment()){
            case START -> 0;
            case CENTER -> getRenderedWidth() / 2;
            case END -> getRenderedWidth();
        };
        offsetY = switch(style.getVerticalAlignment()){
            case START -> style.font.getSize();
            case CENTER -> (int)(style.font.getSize() * .4);
            case END -> 0;
        };
    }

    @Override
    public void draw(GameCanvas canvas) {
        int rect_start_x = x - width / 2;
        int rect_start_y = y - height / 2;

        canvas.graphics.setColor(style.bg_color);
        canvas.graphics.fillRect(rect_start_x, rect_start_y, width, height);

        canvas.graphics.setColor(style.fg_color);
        canvas.graphics.setFont(style.font);

        int text_start_x = rect_start_x + offsetX + switch(style.getHorizontalAlignment()){
            case START -> 0;
            case CENTER -> width / 2;
            case END -> width;
        };

        int text_start_y = rect_start_y + offsetY + switch(style.getVerticalAlignment()){
            case START -> 0;
            case CENTER -> height / 2;
            case END -> height;
        };

        canvas.graphics.drawString(text, text_start_x, text_start_y);
    }
}
