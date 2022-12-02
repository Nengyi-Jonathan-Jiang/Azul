package game.scenes;

import engine.components.ButtonComponent;
import engine.components.ImageRendererComponent;
import engine.core.AbstractScene;
import engine.core.GameCanvas;
import engine.core.GameObject;
import engine.core.Vec2;
import engine.input.MouseEvent;
import game.frontend.InfoPanelObject;

public class RulesScene extends AbstractScene {
    private GameObject background;
    private GameObject[] pages = new GameObject[6];
    private ImageRendererComponent[] imgs = new ImageRendererComponent[6];

    private final InfoPanelObject info;

    private double scroll = 0;

    private boolean finished = false;

    public RulesScene(){
        background = createImageObject("table.jpg");
        for(int i = 0; i < 6; i++){
            pages[i] = createImageObject("rules/Azul Rulebook-" + (i + 1) + ".png");
            imgs[i] = pages[i].getComponent(ImageRendererComponent.class);
        }

        info = new InfoPanelObject("Viewing Rules", "Go Back");
    }

    public GameObject createImageObject(String path){
        ImageRendererComponent c = new ImageRendererComponent(path);
        return new GameObject(c.getImageSize(), c);
    }

    @Override
    public void scroll(int distance) {
        scroll -= distance * .02;
        System.out.println(scroll);
    }

    @Override
    public void draw(GameCanvas canvas) {
        background.draw(canvas);

        for(int i = 0; i < 6; i++){
            pages[i].setSize(new Vec2(canvas.getWidth()).scaledBy(1, imgs[i].getAspectRatio()).scaledBy(.8));
        }

        double top = scroll * canvas.getWidth();
        for(int i = 0; i < 6; i++){
            pages[i].setPosition(new Vec2(0, top));
            top += pages[i].getSize().y + 20;
            pages[i].draw(canvas);
        }

        info.draw(canvas);
    }

    @Override
    public void onMouseClick(MouseEvent me) {
        if(info.getRight().getComponent(ButtonComponent.class).contains(me.position)){
            finished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
