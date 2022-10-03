package entity;

import geometry.Point;
import geometry.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import sprite.Sprite;
import sprite.SpriteData;

public abstract class Entity {
    private Rectangle hitBox;
    protected Sprite sprite;

    public Entity(Point spawn, SpriteData sprite, GraphicsContext gc) {
        this.hitBox = new Rectangle(spawn.getX(), spawn.getY(), sprite.w, sprite.h);
        this.sprite = new Sprite(sprite, gc);
    }

    public void render() {
        sprite.render(hitBox.getX(), hitBox.getY());
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public Sprite getSprite() {
        return sprite;
    }

}