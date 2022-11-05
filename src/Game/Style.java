package Game;

import Engine.Util.FontLoader;

import java.awt.*;

public class Style {
    public final static Color FG_COLOR = new Color(0, 0, 0);
    public final static Color BG_COLOR = new Color(255, 255, 255, 95);
    public final static Color HL_COLOR = new Color(255, 215, 0);

    public final static double TEXT_PADDING = 10.;
    public final static double TEXT_SIZE_SMALL = 10.;
    public final static double TEXT_SIZE_MEDIUM = 20.;
    public final static double TEXT_SIZE_LARGE = 30.;

    public static final Font font = FontLoader.load("Algerian Regular.ttf");
    public static final Font font_small = font.deriveFont((float) TEXT_SIZE_SMALL);
    public static final Font font_medium = font.deriveFont((float) TEXT_SIZE_MEDIUM);
    public static final Font font_large = font.deriveFont((float) TEXT_SIZE_LARGE);
}