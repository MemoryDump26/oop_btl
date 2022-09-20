package sprite;

import javafx.scene.image.Image;

public class SpriteData {
    final public Image[] images;
    final public int frames;

    public SpriteData(String name, int frames) {
        images = new Image[frames];
        for (int i = 0; i < frames; i++) {
            try {
                images[i] = new Image(name + i + ".png");
            } catch(Exception e) {
                System.out.println("Can't load image: " + name + i + ".png");
            }
        }
        this.frames = frames;
    }
}
