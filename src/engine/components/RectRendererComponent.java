package engine.components;

import engine.core.Component;
import engine.core.GameCanvas;

import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @noinspection UnusedReturnValue, unused
 */
public class RectRendererComponent extends Component {
    protected final static AtomicReference<Color> DEFAULT_BORDER_COLOR = new AtomicReference<>(Color.BLACK);
    protected final static AtomicReference<Color> DEFAULT_FILL_COLOR = new AtomicReference<>(new Color(255, 255, 255, 0));
    protected AtomicReference<Color> border_color;
    protected AtomicReference<Color> fill_color;

    public RectRendererComponent() {
        this(DEFAULT_BORDER_COLOR);
    }

    public RectRendererComponent(AtomicReference<Color> border_color) {
        this(border_color, DEFAULT_FILL_COLOR);
    }

    public RectRendererComponent(AtomicReference<Color> border_color, AtomicReference<Color> fill_color) {
        this.border_color = border_color;
        this.fill_color = fill_color;
    }

    @Override
    public void drawAndUpdate(GameCanvas canvas) {
        canvas
                .setColor(fill_color.get())
                .fillRect(
                        gameObject.getAbsoluteTopLeft(),
                        gameObject.size()
                )
                .setColor(border_color.get())
                .drawRect(
                        gameObject.getAbsoluteTopLeft(),
                        gameObject.size()
                );
    }

    public RectRendererComponent setBorderColor(AtomicReference<Color> color) {
        border_color = color;
        return this;
    }

    public RectRendererComponent setFillColor(AtomicReference<Color> color) {
        fill_color = color;
        return this;
    }
}