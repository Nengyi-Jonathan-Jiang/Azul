package engine.animation;

import engine.util.ImageLoader;

import java.awt.image.BufferedImage;

public class AnimationFrame {
    public final BufferedImage image;
    public final int duration, width, height;

    public AnimationFrame(String imagePath, int duration){
        this(ImageLoader.get(imagePath), duration);
    }

    public AnimationFrame(BufferedImage image, int duration){
        if(duration <= 0){
            throw new Error("Animation error: animation frame duration must be greater than zero");
        }
        
        this.duration = duration;
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }
}
