package sprite;

import javafx.scene.image.Image;

public class SpriteData {
    final public Image[] images;
    final public int frames;
    final public int w;
    final public int h;

public SpriteData(String name, int frames, int w, int h) {
        images = new Image[frames];
        this.frames = frames;
        this.w = w;
        this.h = h;
        for (int i = 0; i < frames; i++) {
            try {
                images[i] = new Image(name + i + ".png");
            } catch(Exception e) {
                System.out.println("Can't load image: " + name + i + ".png");
            }
        }
    }
}
