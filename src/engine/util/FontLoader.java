package engine.util;

import game.Style;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

/**
 * Utility class to load fonts
 */
public final class FontLoader {
    public static final Font DefaultFont = new Font("Times New Roman", Font.PLAIN, 0);

    /**
     * Loads a font
     *
     * @param filename The location of the font file
     */
    public static Font load(String filename) {
        Font f;
        try {
            f = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Style.class.getResourceAsStream("/" + filename)));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            f = DefaultFont;
        }
        return f;
    }
}