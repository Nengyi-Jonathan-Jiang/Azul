package Engine.UI.Buttons;

import Engine.Core.GameCanvas;
import Engine.Util.ImageLoader;

import java.awt.image.BufferedImage;

/**
 * A Button that displays an image
 */
public class ImageButton extends Button {
    protected final BufferedImage image;
    public ImageButton(String imgName, int x, int y, int width, int height){
        super(x, y, width, height);
        image = ImageLoader.get(imgName);
    }
    
    @Override
    public void draw(GameCanvas canvas) {
        canvas.graphics.drawImage(image, x, y, width, height, null);
    }
}