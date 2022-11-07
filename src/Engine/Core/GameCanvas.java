package Engine.Core;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A double-buffered canvas on which to draw stuff.
 */
public class GameCanvas extends JPanel {
    private AbstractScene currScene = null;
    public Graphics2D graphics = null;
    public int width = -1, height = -1;

    /**
     * Repaints the canvas, using the {@link AbstractScene#draw} method of the {@link AbstractScene} supplied
     * @param a The function to call to paint the canvas
     */
    public void repaint(AbstractScene a) {
        currScene = a;
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
        if(currScene != null) {
            graphics.setRenderingHint(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON
            );
            graphics.setRenderingHint(
                    RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR
            );
            graphics.setStroke(new BasicStroke(2));
            currScene.draw(this);
        }
    }

    // Canvas functions

    public GameCanvas setColor(int r, int g, int b){
        return setColor(r, g, b, 1.0);
    }
    public GameCanvas setColor(int r, int g, int b, float a){
        return setColor(new Color(r, g, b, a * 255));
    }
    public GameCanvas setColor(float r, float g, float b){
        return setColor(r, g, b, 1.0);
    }
    public GameCanvas setColor(double r, double g, double b, double a){
        return setColor(new Color((float)r, (float)g, (float)b, (float)a));
    }
    public GameCanvas setColor(Color color){
        graphics.setColor(color);
        return this;
    }

    public GameCanvas drawImage(BufferedImage img, Vec2 position, Vec2 size){
        return drawImage(img, position, size.x(), size.y());
    }
    public GameCanvas drawImage(BufferedImage img, Vec2 position, double width, double height){
        return drawImage(img, position.x(), position.y(), width, height);
    }
    public GameCanvas drawImage(BufferedImage img, double x, double y, Vec2 size){
        return drawImage(img, x, y, size.x(), size.y());
    }
    public GameCanvas drawImage(BufferedImage img, double x, double y, double width, double height){
        graphics.drawImage(img, (int)x, (int)y, (int)width, (int) height, null);
        return this;
    }

    public GameCanvas drawRect(Vec2 position, Vec2 size){
        return drawRect(position, size.x(), size.y());
    }
    public GameCanvas drawRect(Vec2 position, double width, double height){
        return drawRect(position.x(), position.y(), width, height);
    }
    public GameCanvas drawRect(double x, double y, Vec2 size){
        return drawRect(x, y, size.x(), size.y());
    }
    public GameCanvas drawRect(double x, double y, double width, double height){
        graphics.drawRect((int)x, (int)y, (int)width, (int) height);
        return this;
    }

    public GameCanvas fillRect(Vec2 position, Vec2 size){
        return fillRect(position, size.x(), size.y());
    }
    public GameCanvas fillRect(Vec2 position, double width, double height){
        return fillRect(position.x(), position.y(), width, height);
    }
    public GameCanvas fillRect(double x, double y, Vec2 size){
        return fillRect(x, y, size.x(), size.y());
    }
    public GameCanvas fillRect(double x, double y, double width, double height){
        graphics.fillRect((int)x, (int)y, (int)width, (int) height);
        return this;
    }

    public GameCanvas setDrawFont(Font font){
        graphics.setFont(font);
        return this;
    }


    public GameCanvas drawText(String text, Vec2 position){
        return drawText(text, position.x(), position.y());
    }
    public GameCanvas drawText(String text, double x, double y){
        graphics.drawString(text, (int)x, (int)y);
        return this;
    }
}
