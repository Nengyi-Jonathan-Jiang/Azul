package game;

import engine.util.FontLoader;

import java.awt.*;

public class Style {
    public final static Color FG_COLOR = new Color(0, 0, 0);
    public final static Color BG_COLOR = new Color(252, 245, 229);
    public final static Color SH_COLOR = new Color(231, 225, 211);
    public final static Color DM_COLOR = new Color(173, 169, 158);
    public final static Color HL_COLOR = new Color(255, 215, 0);
    public final static Color HL2_COLOR = new Color(25, 215, 0);

    public final static double TEXT_PADDING = 10.;

    public static final Font font = FontLoader.load("Algerian Regular.ttf");
    public static final Font font_medium = font.deriveFont(20f);
    public static final Font font_large = font.deriveFont(30f);
    public static final Font font_huge = font.deriveFont(40f);
}
