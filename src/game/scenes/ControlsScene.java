package game.scenes;

import engine.components.RectRendererComponent;
import engine.components.TextRendererComponent;
import engine.components.TextStyle;
import engine.core.*;
import game.Style;

import java.util.Arrays;

public class ControlsScene extends AbstractScene {
    private final GameObject g;
    private double scroll = 0;
    private boolean finished = false;

    public ControlsScene(){
        GameObject[] entries = {
                makeEntry("Player turn", "Select a tile from the factories or", "factory floor, then click on one of", "the pattern lines, then click the next button"),
                makeEntry("Select tiles / pattern line", "Left click on the target object"),
                makeEntry("Interact with button", "Left click on the button"),
                makeEntry("Pan game scene", "Right click "),
                makeEntry("Scoring", "When the game is done scoring, press the continue button")
        };

        g = new GameObject();
        double top = 0;
        for(GameObject o : entries){
            o.setPosition(new Vec2(0, top));
            top += o.getSize().y + Style.TEXT_PADDING * 2;
        }
        g.addChildren(entries);
    }

    @Override
    public void draw(GameCanvas canvas) {
        g.draw(canvas);
    }

    @Override
    public void scroll(int distance) {
        scroll += distance * 50;
    }

    private GameObject makeEntry(String left, String...right){
        GameObject l = makeParagraph(new TextStyle(Style.font_medium, TextStyle.ALIGN_LEFT), left);
        GameObject r = makeParagraph(new TextStyle(Style.font_medium, TextStyle.ALIGN_RIGHT), right);

        double height = Math.max(l.getSize().y, r.getSize().y) + 2 * Style.TEXT_PADDING;

        return new GameObject(
            new Component() {
                @Override
                public void drawAndUpdate(GameCanvas canvas) {
                    gameObject.setSize(new Vec2(canvas.getWidth() * .8, height));
                    l.setTopLeft(gameObject.getTopLeftOffset().plus(new Vec2(Style.TEXT_PADDING)));
                    r.setBottomRight(gameObject.getBottomRightOffset().minus(new Vec2(Style.TEXT_PADDING)));
                }
            },
            new RectRendererComponent(Style.FG_COLOR, Style.BG_COLOR)
        ).addChildren(l, r);
    }

    private GameObject makeParagraph(TextStyle style, String... lines){
        GameObject res = new GameObject();

        GameObject[] text = new GameObject[lines.length];

        for(int i = 0; i < lines.length; i++){
            text[i] = new GameObject(new TextRendererComponent(lines[i], style));
            text[i].getComponent(TextRendererComponent.class).shrinkParentToFit(0);
        }

        double width = Arrays.stream(text).map(GameObject::getSize).map(Vec2::x).reduce(0., Math::max);
        double height = Arrays.stream(text).map(GameObject::getSize).map(Vec2::y).reduce(0., Double::sum);

        res.setSize(new Vec2(width, height));

        double top = -height / 2;
        for(GameObject o : text){
            o.setSize(new Vec2(width, o.getSize().y));
            o.setPosition(new Vec2(0, top));
            top += o.getSize().y;
        }

        res.addChildren(text);

        return res;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
