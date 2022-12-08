package game;

import engine.util.FontLoader;

import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

public class Style {
    public static AtomicReference<Color> FG_COLOR = new AtomicReference<>(new Color(0, 0, 0));
    public static AtomicReference<Color> BG_COLOR = new AtomicReference<>(new Color(252, 245, 229));
    public static AtomicReference<Color> DM_COLOR = new AtomicReference<>(new Color(173, 169, 158));
    public static AtomicReference<Color> HL_COLOR = new AtomicReference<>(new Color(255, 215, 0));
    public static AtomicReference<Color> HL2_COLOR = new AtomicReference<>(new Color(25, 215, 0));

    public static Color makeTransparent(AtomicReference<Color> c, int alpha){
        return new Color(c.get().getRed(), c.get().getGreen(), c.get().getBlue(), alpha);
    }

    public final static double PADDING = 10.;
    public static final Font font = FontLoader.load("Algerian Regular.ttf");
    public static final Font font_d = FontLoader.load("AbrilFatface-Regular.ttf");
    public static final Font font_medium = font.deriveFont(20f);
    public static final Font font_large = font.deriveFont(30f);
    public static final Font font_huge = font.deriveFont(40f);
}
