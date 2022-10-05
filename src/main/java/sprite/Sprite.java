package sprite;

import javafx.scene.canvas.GraphicsContext;

public class Sprite {
    public static Object BrickTile;
    final private GraphicsContext gc;
    private SpriteData data;
    public static Sprite grass;

    private String currentAnimation = new String();
    private int currentFrame = 0;
    private int tickPerFrame = 2;
    private int ticks = 0;
    private boolean loop = true;
    private boolean visible = true;
    private boolean paused = false;

    public Sprite(SpriteData data, GraphicsContext gc) {
        this.data = data;
        this.currentAnimation = data.currentAnimation;
        this.gc = gc;
    }

    public void setCurrentAnimation(String name) {
        if (data.animations.containsKey(name)) {
            this.currentAnimation = name;
        }
        else System.out.printf("Animation: %s doesn't exists!!!1!\n", name);
    }

    public void setSpriteData(SpriteData data) {
        this.data = data;
        currentFrame = 0;
    }

    public void setTickPerFrame(int n) {
        this.tickPerFrame = n - 1;
        currentFrame = 0;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public void show() {
        visible = true;
    }

    public void hide() {
        visible = false;
    }

    public void render() {
        render(0, 0, data.w, data.h);
    }

    public void render(double x, double y) {
        render(x, y, data.w, data.h);
    }

    public void render(double x, double y, double w, double h) {
        if (!visible) {
            return;
        }
        gc.drawImage(data.animations.get(currentAnimation).get(currentFrame), x, y, w, h);
        if (paused) {
            return;
        }
        if (ticks < tickPerFrame) {
            ticks++;
        } else {
            currentFrame++;
            ticks = 0;
        }
        if (currentFrame == data.animations.get(currentAnimation).size()) {
            if (loop) {
                currentFrame = 0;
            } else {
                currentFrame--;
                paused = true;
            }
        }
    }
}