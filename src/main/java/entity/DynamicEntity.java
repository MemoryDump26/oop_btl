package entity;

import collision.CollisionComponent;
import input.InputComponent;
import geometry.Point;
import sprite.SpriteData;

import javafx.scene.canvas.GraphicsContext;

public class DynamicEntity extends Entity {

    public DynamicEntity(Point spawn, InputComponent input, CollisionComponent collision, SpriteData sprite, GraphicsContext gc) {
        super(spawn, input, collision, sprite, gc);
    }

    public DynamicEntity(Point spawn, DynamicEntity p) {
        super(spawn, p);
    }

    public void move(double x, double y) {
        velocity.add(x, y);
    }

    public void moveTo(double x, double y) {
        this.getHitBox().setX(x);
        this.getHitBox().setY(y);
    }

    public void moveUp() {
        sprite.setCurrentAnimation("up");
        move(0, -speed);
    }

    public void moveDown() {
        sprite.setCurrentAnimation("down");
        move(0, speed);
    }

    public void moveLeft() {
        sprite.setCurrentAnimation("left");
        move(-speed, 0);
    }

    public void moveRight() {
        sprite.setCurrentAnimation("right");
        move(speed, 0);
    }

    public void touched(Entity e) {return;}
    public void attack() {return;}

    public void kill() {
        if (destructible) {
            sprite.setCurrentAnimation("dead");
            sprite.setLoop(false);
            dead = true;
        }
    }
}
