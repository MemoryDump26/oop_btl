package sprite;

import javafx.scene.image.Image;

public class Sprite {
    public Image[] images;
    private int frames;
    private int fps;
    private int currentFrame = 0;

    public Sprite(String name, int frames, int fps) {
        images = new Image[frames];
        for (int i = 0; i < frames; i++) {
            images[i] = new Image(name + i + ".png");
        }
        this.frames = frames;
        this.fps = fps;
    }

    public int advance() {
        int tmp = currentFrame;
        currentFrame = (currentFrame + 1) % frames;
        return tmp;
    }

    /*public Sprite() {
        images = new Image;
        int frames = 0;
        int fps = 0;
    }*/
}
