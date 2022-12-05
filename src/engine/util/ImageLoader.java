package engine.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

/**
 * Utility class to load images from files
 */
public final class ImageLoader {
    private final static Map<String, BufferedImage> images = new TreeMap<>();

    private ImageLoader() {
    }

    /**
     * Gets an image by filename. Images are cached, so no image is loaded twice
     */
    public static BufferedImage get(String filename) {
        return get(filename, filename);
    }

    /**
     * Gets an image by name. Images are cached, so no image is loaded twice
     */
    public static BufferedImage get(String name, String filename) {
        if (images.containsKey(name)) return images.get(name);
        else return load(name, filename);
    }

    /**
     * Loads a file into the class and returns it
     * @param filename The location of the image file
     */
    public static BufferedImage load(String name, String filename) {
        try (InputStream inputStream = ImageLoader.class.getResourceAsStream("/" + filename)) {
            if (inputStream != null) {
                BufferedImage image = ImageIO.read(inputStream);
                images.put(name, image);
                return image;
            } else {
                throw new IOException("Null input stream");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not load file \"" + filename + "\"");
        }
        return null;
    }
}