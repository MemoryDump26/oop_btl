package sprite;

import javafx.scene.canvas.GraphicsContext;

public class Sprite {
    final private GraphicsContext gc;
    private SpriteData data;

    private String currentAnimation;
    private int currentFrame = 0;
    private int tickPerFrame = 4;
    private int ticks = 0;
    private boolean loop = true;
    private boolean visible = true;
    private boolean paused = false;

    public Sprite(SpriteData data, GraphicsContext gc) {
        this.data = data;
        this.currentAnimation = data.currentAnimation;
        this.gc = gc;
    }

    public Sprite(Sprite p) {
        this.data = p.data;
        this.currentAnimation = p.currentAnimation;
        this.tickPerFrame = p.tickPerFrame;
        this.loop = p.loop;
        this.visible = p.visible;
        this.paused = p.paused;
        this.gc = p.gc;
    }

    public GraphicsContext getGc() {return gc;}
    public SpriteData getData() {return data;}

    public void setCurrentAnimation(String name) {
        if (data.animations.containsKey(name)) {
            this.currentAnimation = name;
        }
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

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void show() {
        visible = true;
    }

    public void hide() {
        visible = false;
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
    }

    public boolean isPausing() {
        return paused;
    }

    public void render() {
        render(0, 0, data.getW(), data.getH());
    }

    public void render(double x, double y) {
        render(x, y, data.getW(), data.getH());
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
