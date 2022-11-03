package Engine.Components;

import Engine.Core.GameCanvas;

import java.awt.*;

public class RectComponent extends Component{
    protected final static Color DEFAULT_BORDER_COLOR = Color.BLACK,
        DEFAULT_FILL_COLOR = new Color(255, 255, 255, 0);
    protected final Color border_color, fill_color;

    public RectComponent(){
        this(DEFAULT_BORDER_COLOR);
    }

    public RectComponent(Color border_color){
        this(border_color, DEFAULT_FILL_COLOR);
    }

    public RectComponent(Color border_color, Color fill_color){
        this.border_color = border_color;
        this.fill_color = fill_color;
    }

    @Override
    public void drawAndUpdate(GameCanvas canvas) {
        canvas.graphics.setColor(fill_color);
        canvas.graphics.fillRect(x - width / 2, y - height / 2, width, height);
        canvas.graphics.setColor(border_color);
        canvas.graphics.drawRect(x - width / 2, y - height / 2, width, height);
    }
}