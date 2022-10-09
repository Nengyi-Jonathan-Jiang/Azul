package Engine.Components;

import Engine.Core.GameCanvas;
import Engine.Util.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A Button that displays an image
 */
public class ImageComponent extends Component {
    protected final BufferedImage image;
    public ImageComponent(String imgName){
        image = ImageLoader.load(imgName);
    }
    
    @Override
    public void draw(GameCanvas canvas) {
        canvas.graphics.drawImage(image, x - width / 2, y - height / 2, width, height, null);
    }

    public Dimension getImageSize(){
        return new Dimension(image.getWidth(), image.getHeight());
    }

    public double getAspectRatio(){
        return 1.0 * image.getWidth() / image.getHeight();
    }
}