package entity;

import geometry.Point;
import input.CollisionComponent;
import input.InputComponent;
import javafx.scene.canvas.GraphicsContext;
import sprite.SpriteData;

public class DynamicEntity extends Entity {
    public double speed;
    public Point velocity = new Point(0, 0);
    InputComponent input;
    CollisionComponent collision;

    public DynamicEntity(Point spawn, InputComponent input, CollisionComponent collision, SpriteData sprite, GraphicsContext gc) {
        super(spawn, sprite, gc);
        this.input = input;
        this.collision = collision;
    }

    public void update(Entity[] wall) {
        input.handle(this);
        collision.handle(this, wall);
        velocity.zero();
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Point getVelocity() {
        return velocity;
    }

    public void setVelocity(Point velocity) {
        this.velocity = velocity;
    }

    public void move(double x, double y) {
        velocity.add(x, y);
    }
    public void moveLeft() {
        move(-speed, 0);
    }
    public void moveRight() {
        move(speed, 0);
    }
    public void moveUp() {
        move(0, -speed);
    }
    public void moveDown() {
        move(0, speed);
    }
}
