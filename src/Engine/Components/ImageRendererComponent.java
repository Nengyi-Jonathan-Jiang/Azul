package Engine.Components;

import Engine.Core.GameCanvas;
import Engine.Core.Vec2;
import Engine.Util.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A Button that displays an image
 */
public class ImageRendererComponent extends Component {
    protected final BufferedImage image;
    public ImageRendererComponent(String fileName){
        image = ImageLoader.get(fileName);
    }
    
    @Override
    public void drawAndUpdate(GameCanvas canvas) {
        canvas.drawImage(
            image,
            gameObject.getAbsoluteTopLeft(),
            gameObject.getSize()
        );
    }

    public Vec2 getImageSize(){
        return new Vec2(image.getWidth(), image.getHeight());
    }

    public double getAspectRatio(){
        return 1.0 * image.getWidth() / image.getHeight();
    }
}