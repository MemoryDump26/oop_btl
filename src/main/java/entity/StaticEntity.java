package entity;

import collision.CollisionComponent;
import input.InputComponent;
import geometry.Point;
import sprite.SpriteData;

import javafx.scene.canvas.GraphicsContext;

public class StaticEntity extends Entity {

    public StaticEntity(Point spawn, InputComponent input, CollisionComponent collision, SpriteData sprite, GraphicsContext gc) {
        super(spawn, input, collision, sprite, gc);
    }

    public StaticEntity(Point spawn, StaticEntity p) {
        super(spawn, p);
    }

    public void move(double x, double y) {}
    public void moveTo(double x, double y) {}
    public void moveUp() {}
    public void moveDown() {}
    public void moveLeft() {}
    public void moveRight() {}
    public void touched(Entity e) {}
    public void attack() {}
    public void kill() {}
}
