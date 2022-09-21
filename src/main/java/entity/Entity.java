package entity;

import geometry.Point;
import geometry.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import sprite.Sprite;
import sprite.SpriteData;

public abstract class Entity {
    protected Point position;
    protected Rectangle hitBox;
    protected boolean collision = true;
    protected Sprite sprite;

    public Entity(Point spawn, SpriteData sprite, GraphicsContext gc) {
        this.position = spawn;
        this.hitBox = new Rectangle(spawn.getX(), spawn.getY(), sprite.w, sprite.h);
        this.sprite = new Sprite(sprite, gc);
    }

    public void render() {
        sprite.render(position.getX(), position.getY());
    }

    public boolean colliding(Entity e) {
        if (e.collision) {
            return e.hitBox.intersect(this.hitBox);
        } else {
            return false;
        }
    }
    public abstract void moveUp();
    public abstract void moveDown();
    public abstract void moveLeft();
    public abstract void moveRight();
}