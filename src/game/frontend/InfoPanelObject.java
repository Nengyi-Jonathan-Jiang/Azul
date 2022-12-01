package game.frontend;

import engine.components.RectRendererComponent;
import engine.components.TextRendererComponent;
import engine.core.GameCanvas;
import engine.core.GameObject;
import engine.core.Vec2;
import game.Style;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class InfoPanelObject extends GameObject {
    private static final double HEIGHT = 65;

    private TextObject[] textObjects;
    private TextObject right;

    private String[] args;

    public InfoPanelObject(String... text){
        super(new RectRendererComponent(Style.FG_COLOR, Style.makeTransparent(Style.DM_COLOR, 127)));
        setChildren(text);
    }

    public void setChildren(String... text){
        new ArrayList<>(children).forEach(GameObject::removeFromParent);

        args = text;

        if(text.length > 0 && text[text.length - 1] != null){
            right = new TextObject(text[text.length - 1]);
            addChild(right);
        }
        else right = null;

        if(text.length > 1) {
            this.textObjects = new TextObject[text.length - 1];

            for (int i = 0; i + 1 < text.length; i++) {
                this.textObjects[i] = new TextObject(text[i]);
            }

            addChildren(this.textObjects);
        }
        else this.textObjects = new TextObject[0];
    }

    public TextObject getRight() {
        return right;
    }

    public TextObject get(int i){
        return textObjects[i];
    }

    public void set(int i, String text){
        get(i).getComponent(TextRendererComponent.class)
                .setText(text)
                .shrinkParentToFit(Style.TEXT_PADDING * 1.25);
    }

    public void setRight(String text){
        getRight().getComponent(TextRendererComponent.class).setText(text);
    }

    @Override
    public GameObject draw(GameCanvas canvas) {
        setSize(new Vec2(canvas.getWidth(), HEIGHT));
        setTopLeft(canvas.get_size().scaledBy(-.5));

        Vec2 offset = getTopLeftOffset();
        double left = 0;
        for(GameObject o : textObjects){
            left += Style.TEXT_PADDING;
            o.setTopLeft(new Vec2(left, Style.TEXT_PADDING).plus(offset));
            left += o.getSize().x;
        }

        if(right != null) {
            right.setTopRight(new Vec2(
                    canvas.getWidth() - Style.TEXT_PADDING,
                    Style.TEXT_PADDING
            ).plus(offset));
        }

        return super.draw(canvas);
    }

    public String[] getArgs() {
        return Stream.concat(
            Arrays.stream(textObjects).map(TextObject::getText),
            Stream.of(right.getText())
        ).toArray(String[]::new);
    }
}