package engine.components;

import engine.core.Component;
import engine.core.GameCanvas;
import engine.core.Vec2;
import engine.util.ImageLoader;

import java.awt.*;

/**
 * A Button that displays an image
 *
 * @noinspection unused
 */
public class ImageRendererComponent extends Component {
    protected final String imageName;
    //protected final BufferedImage image;
    protected final Object imageScaling, renderSpeed;

    public ImageRendererComponent(String imgName) {
        this(imgName, RenderSpeed.BALANCED);
    }

    public ImageRendererComponent(String imageName, RenderSpeed renderSpeed) {
        //image = ImageLoader.get(imgName);
        this.imageName = imageName;
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
        canvas.drawImage(ImageLoader.get(imageName), gameObject.getAbsoluteTopLeft(), gameObject.size());
    }

    public Vec2 getImageSize() {
        return new Vec2(ImageLoader.get(imageName).getWidth(), ImageLoader.get(imageName).getHeight());
    }

    public double getAspectRatio() {
        return 1.0 * ImageLoader.get(imageName).getWidth() / ImageLoader.get(imageName).getHeight();
    }

    public enum RenderSpeed {
        FAST,
        SLOW,
        BALANCED,
    }
}