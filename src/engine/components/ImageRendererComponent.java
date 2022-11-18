package engine.components;

import engine.core.Component;
import engine.core.GameCanvas;
import engine.core.Vec2;
import engine.util.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A Button that displays an image
 *
 * @noinspection unused
 */
public class ImageRendererComponent extends Component {
    protected final BufferedImage image;
    protected final Object imageScaling, renderSpeed;

    public ImageRendererComponent(String fileName) {
        this(fileName, RenderSpeed.BALANCED);
    }

    public ImageRendererComponent(String fileName, RenderSpeed renderSpeed) {
        image = ImageLoader.get(fileName);
        this.imageScaling = switch (renderSpeed) {
            case FAST -> RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
            case SLOW -> RenderingHints.VALUE_INTERPOLATION_BICUBIC;
            case BALANCED -> RenderingHints.VALUE_INTERPOLATION_BILINEAR;
        };
        this.renderSpeed = switch (renderSpeed) {
            case FAST -> RenderingHints.VALUE_RENDER_SPEED;
            case SLOW -> RenderingHints.VALUE_RENDER_QUALITY;
            case BALANCED -> RenderingHints.VALUE_RENDER_DEFAULT;
        };
    }

    @Override
    public void drawAndUpdate(GameCanvas canvas) {
        canvas.graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, imageScaling);
        canvas.drawImage(image, gameObject.getAbsoluteTopLeft(), gameObject.getSize());
    }

    public Vec2 getImageSize() {
        return new Vec2(image.getWidth(), image.getHeight());
    }

    public double getAspectRatio() {
        return 1.0 * image.getWidth() / image.getHeight();
    }

    public enum RenderSpeed {
        FAST,
        SLOW,
        BALANCED,
    }
}