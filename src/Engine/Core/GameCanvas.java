package Engine.Core;

import javax.swing.*;
import java.awt.*;

/**
 * A double-buffered canvas on which to draw stuff.
 */
public class GameCanvas extends JPanel {
    private Action currAction = null;
    public Graphics2D graphics = null;
    public int width = -1, height = -1;

    /**
     * Repaints the canvas, using the {@link Action#draw} method of the {@link Action} supplied
     * @param a The function to call to paint the canvas
     */
    public void repaint(Action a) {
        currAction = a;
        super.repaint();
    }

    /**
     * Paints the canvas with the given graphics context, should never be called directly
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        graphics = (Graphics2D) g;
        width = getWidth();
        height = getHeight();
        if(currAction != null && !currAction.isFinished()) {
            graphics.setRenderingHint(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON
            );
            graphics.setRenderingHint(
                    RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR
            );
            currAction.draw(this);
        }
    }
}
