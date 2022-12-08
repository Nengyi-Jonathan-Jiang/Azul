package engine.components;

import engine.core.Component;
import engine.core.GameCanvas;
import engine.core.Vec2;
import engine.util.ImageLoader;

import java.awt.image.BufferedImage;

/**
 * A Button that displays an image
 *
 * @noinspection unused
 */
public class ScaledImageRendererComponent extends Component {
    protected final BufferedImage image;

    public ScaledImageRendererComponent(String fileName) {
        image = ImageLoader.get(fileName);
    }

    @Override
    public void drawAndUpdate(GameCanvas canvas) {
        canvas.drawImage(
                image,
                gameObject.getAbsoluteTopLeft(),
                gameObject.size()
        );
    }

    public Vec2 getImageSize() {
        return new Vec2(image.getWidth(), image.getHeight());
    }

    public double getAspectRatio() {
        return 1.0 * image.getWidth() / image.getHeight();
    }
}