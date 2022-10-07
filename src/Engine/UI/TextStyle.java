package Engine.UI;

import Engine.Core.GameCanvas;

import java.awt.*;

public final class TextStyle {

    public static final int ALIGN_LEFT = 0;
    public static final int ALIGN_HORIZONTAL = 3;
    public static final int ALIGN_RIGHT = 2;
    public static final int ALIGN_TOP = 0;
    public static final int ALIGN_VERTICAL = 12;
    public static final int ALIGN_BOTTOM = 8;

    private static Font DEFAULT_FONT = new Font("Times New Roman", Font.PLAIN, 12);
    private static Color DEFAULT_FG_COLOR = Color.BLACK;
    private static Color DEFAULT_BG_COLOR = new Color(0, 0, 0, 0);
    private static int DEFAULT_ALIGNMENT = ALIGN_LEFT | ALIGN_TOP;

    public final Font font;
    public final int alignment;
    public final Color fg_color;
    public final Color bg_color;

    public enum Alignment {START, CENTER, END};

    public Alignment getHorizontalAlignment(){
        return (alignment & 2) == 0 ? Alignment.START : (alignment & 1) == 0 ? Alignment.CENTER : Alignment.END;
    }

    public Alignment getVerticalAlignment(){
        return (alignment & 8) == 0 ? Alignment.START : (alignment & 4) == 0 ? Alignment.CENTER : Alignment.END;
    }

    public TextStyle(){
        this(DEFAULT_FONT);
    }

    public TextStyle(Font font){
        this(font, DEFAULT_ALIGNMENT);
    }

    public TextStyle(Color fg_color){
        this(DEFAULT_FONT, fg_color);
    }

    public TextStyle(int alignment){
        this(DEFAULT_FONT, alignment);
    }

    public TextStyle(Color fg_color, int alignment){
        this(DEFAULT_FONT, fg_color, alignment);
    }

    public TextStyle(Font font, int alignment){
        this(font, DEFAULT_FG_COLOR, alignment);
    }

    public TextStyle(Font font, Color fg_color){
        this(font, fg_color, DEFAULT_ALIGNMENT);
    }

    public TextStyle(Color fg_color, Color bg_color){
        this(DEFAULT_FONT, fg_color, bg_color);
    }

    public TextStyle(Color fg_color, Color bg_color, int alignment){
        this(DEFAULT_FONT, fg_color, bg_color, alignment);
    }

    public TextStyle(Font font, Color fg_color, Color bg_color){
        this(font, fg_color, bg_color, 0);
    }

    public TextStyle(Font font, Color fg_color, int alignment){
        this(font, fg_color, DEFAULT_BG_COLOR, alignment);
    }

    public TextStyle(Font font, Color fg_color, Color bg_color, int alignment){
        this.font = font;
        this.fg_color = fg_color;
        this.bg_color = bg_color;
        this.alignment = alignment;
    }
}
