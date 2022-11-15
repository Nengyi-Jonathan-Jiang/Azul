package engine.components;

import engine.animation.Animation;
import engine.animation.AnimationFrame;
import engine.core.GameCanvas;
import engine.core.Vec2;
import engine.util.ImageLoader;

import java.awt.image.BufferedImage;

public class AnimationRendererComponent extends Component {
    private Animation animation;
    private boolean started = false;
    private boolean looping = false;
    private int elapsedFrameTime = 0;
    private int animationFrameIndex = 0;

    public AnimationRendererComponent(Animation animation){
        this.animation = animation;
    }

    public Animation getAnimation(){
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    @Override
    public void drawAndUpdate(GameCanvas canvas) {
        if(!started){
            AnimationFrame frame = animation.startFrame;
            canvas.graphics.drawImage(frame.image, (int)gameObject.getAbsoluteTopLeft().x, (int)gameObject.getAbsoluteTopLeft().y, null);
            return;
        }
        if(hasEnded()){
            AnimationFrame frame = animation.endFrame;
            canvas.graphics.drawImage(frame.image, (int)gameObject.getAbsoluteTopLeft().x, (int)gameObject.getAbsoluteTopLeft().y, null);
            return;
        }

        AnimationFrame frame = animation.frames[animationFrameIndex];
        canvas.graphics.drawImage(frame.image, (int)gameObject.getAbsoluteTopLeft().x, (int)gameObject.getAbsoluteTopLeft().y, null);

        elapsedFrameTime++;

        if(elapsedFrameTime > frame.duration){
            animationFrameIndex++;
            elapsedFrameTime = 0;
        }
        if(animationFrameIndex >= animation.frames.length && looping) animationFrameIndex = 0;
    }

    public void start(){
        start(false);
    }
    public void start(boolean looping){
        this.started = true;
        this.looping = looping;
        this.elapsedFrameTime = 0;
        this.animationFrameIndex = 0;
    }
    public void stop(){
        started = false;
    }

    public boolean hasEnded(){
        return animationFrameIndex >= animation.frames.length;
    }

    public Vec2 getFrameSize(){
        return new Vec2(animation.width, animation.height);
    }

    public double getAspectRatio(){
        return 1.0 * animation.width / animation.height;
    }

    public int getDuration(){
        return animation.duration;
    }
}
