package entity;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import sprite.Sprite;
import sprite.SpriteData;

public class Entity {
    protected Point2D position;
    protected Sprite sprite;

    public Entity(Point2D spawn, SpriteData sprite, GraphicsContext gc) {
        this.position = spawn;
        this.sprite = new Sprite(sprite, gc);
    }

    public void render() {
        sprite.render(position.getX(), position.getY());
    }
}
