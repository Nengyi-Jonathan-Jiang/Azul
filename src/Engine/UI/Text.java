package Engine.UI;

import Engine.Core.GameCanvas;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class Text extends UIComponent {
    protected static final TextStyle DEFAULT_STYLE = new TextStyle();

    protected String text;
    protected final TextStyle style;
    protected int offsetX, offsetY;

    public static int getRenderedWidth(TextStyle style, String text){
        Rectangle2D size = style.font.getStringBounds(text, new FontRenderContext(new AffineTransform(),true,true));
        return (int) size.getWidth();
    }

    public Text(String text){
        this(text, DEFAULT_STYLE);
    }

    public Text(String text, Font font){
        this(text, new TextStyle(font));
    }

    public Text(String text, TextStyle style){
        this(text, style, getRenderedWidth(style, text));
    }

    public Text(String text, int width){
        this(text, DEFAULT_STYLE, width);
    }

    public Text(String text, TextStyle style, int width){
        this(text, style, width, style.font.getSize());
    }

    public Text(String text, int width, int height){
        this(text, DEFAULT_STYLE, width, height, 0, 0);
    }

    public Text(String text, TextStyle style, int width, int height){
        this(text, style, width, height, 0, 0);
    }

    public Text(String text, int width, int height, int x, int y){
        this(text, DEFAULT_STYLE, width, height, x, y);
    }

    public Text(String text, TextStyle style, int width, int height, int x, int y){
        super(width, height, x, y);
        this.text = text;
        this.style = style;
        recalculate_text_position();
    }

    public Text setText(String text){
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
            case CENTER -> getRenderedWidth(style, text) / 2;
            case END -> getRenderedWidth(style, text);
        };
        offsetY = -switch(style.getVerticalAlignment()){
            case START -> 0;
            case CENTER -> (int)(style.font.getSize() * .4);
            case END -> style.font.getSize();
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

        int text_start_x = switch(style.getHorizontalAlignment()){
            case START -> rect_start_y;
            case CENTER -> rect_start_y + offsetY;
            case END -> rect_start_y + height + offsetY;
        };

        int text_start_y = switch(style.getHorizontalAlignment()){
            case START -> rect_start_y;
            case CENTER -> rect_start_y + offsetY;
            case END -> rect_start_y + height + offsetY;
        };
    }
}
