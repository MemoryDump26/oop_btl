package entity;

import geometry.Point;
import input.Input;
import input.InputComponent;
import javafx.scene.canvas.GraphicsContext;
import sprite.SpriteData;

public class Player extends Entity{
    private int power = 1;
    private double speed = 3;
    private Point velocity = new Point(0, 0);
    private InputComponent input;

    public Player(Point spawn, InputComponent input, SpriteData sprite, GraphicsContext gc) {
        super(spawn, sprite, gc);
        this.input = input;
    }

    public void update(Entity[] wall) {
        velocity.zero();
        input.handle(this);
        position = position.add(velocity);
        hitBox.setX(position.getX());
        hitBox.setY(position.getY());
        for (Entity e:wall) {
            if (e.hitBox.intersect(hitBox)) {
                position = position.subtract(velocity);
                hitBox.setX(position.getX());
                hitBox.setY(position.getY());
                break;
            }
        }
    }
    public void stop() {

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
    public void bomb() {
    }
}
