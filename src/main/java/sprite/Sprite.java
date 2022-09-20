package sprite;

import javafx.scene.canvas.GraphicsContext;

public class Sprite {
    final private GraphicsContext gc;
    private SpriteData data;

    private int currentFrame = 0;
    private int tickPerFrame = 0;
    private int ticks = 0;
    private boolean loop = true;
    private boolean visible = true;
    private boolean paused = false;

    public Sprite(SpriteData data, GraphicsContext gc) {
        this.data = data;
        this.gc = gc;
    }

    public void setSpriteData(SpriteData data) {
        this.data = data;
        currentFrame = 0;
    }

    public void setTickPerFrame(int n) {
        this.tickPerFrame = n - 1;
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

    public void render(int x, int y) {
        render(x, y, data.w, data.h);
    }

    public void render(int x, int y, int w, int h) {
        if (!visible) {
            return;
        }
        gc.drawImage(data.images[currentFrame], x, y, w, h);
        if (paused) {
            return;
        }
        if (ticks < tickPerFrame) {
            ticks++;
        } else {
            currentFrame++;
            ticks = 0;
        }
        if (currentFrame == data.frames) {
            if (loop) {
                currentFrame = 0;
            } else {
                currentFrame--;
                paused = true;
            }
        }
    }
}