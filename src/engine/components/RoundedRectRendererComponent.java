package engine.components;

import engine.core.GameCanvas;
import engine.core.Vec2;

import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @noinspection UnusedReturnValue, unused
 */
public class RoundedRectRendererComponent extends RectRendererComponent {
    protected final double radius;
    public RoundedRectRendererComponent(double radius, AtomicReference<Color> border_color, AtomicReference<Color> fill_color) {
        super(border_color, fill_color);
        this.radius = radius;
    }

    @Override
    public void drawAndUpdate(GameCanvas canvas) {
        Vec2 tl = gameObject.getAbsoluteTopLeft();
        Vec2 size = gameObject.size();

        canvas.setColor(fill_color.get());
        canvas.graphics.fillRoundRect(
            (int)tl.x, (int)tl.y, (int)size.x, (int)size.y, (int)radius, (int)radius
        );
        canvas.setColor(border_color.get());
        canvas.graphics.drawRoundRect(
                (int)tl.x, (int)tl.y, (int)size.x, (int)size.y, (int)radius, (int)radius
        );
    }
}