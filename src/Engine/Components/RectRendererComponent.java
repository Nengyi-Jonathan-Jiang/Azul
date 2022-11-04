package Engine.Components;

import Engine.Core.GameCanvas;

import java.awt.*;

public class RectRendererComponent extends Component{
    protected final static Color DEFAULT_BORDER_COLOR = Color.BLACK,
        DEFAULT_FILL_COLOR = new Color(255, 255, 255, 0);
    protected Color border_color, fill_color;

    public RectRendererComponent(){
        this(DEFAULT_BORDER_COLOR);
    }

    public RectRendererComponent(Color border_color){
        this(border_color, DEFAULT_FILL_COLOR);
    }

    public RectRendererComponent(Color border_color, Color fill_color){
        this.border_color = border_color;
        this.fill_color = fill_color;
    }

    @Override
    public void drawAndUpdate(GameCanvas canvas) {
        canvas
            .setColor(fill_color)
            .fillRect(
                gameObject.getAbsoluteTopLeft(),
                gameObject.getSize()
            )
            .setColor(border_color)
            .drawRect(
                gameObject.getAbsoluteTopLeft(),
                gameObject.getSize()
            );
    }

    public RectRendererComponent setBorderColor(Color color){
        border_color = color;
        return this;
    }

    public RectRendererComponent setFillColor(Color color){
        fill_color = color;
        return this;
    }
}