package entity;

import geometry.Point;
import javafx.scene.canvas.GraphicsContext;
import sprite.SpriteData;

public class Player extends Entity{
    private int power = 1;
    private double speed = 3;
    private Point velocity = new Point(0, 0);

    public Player(Point spawn, SpriteData sprite, GraphicsContext gc) {
        super(spawn, sprite, gc);
    }

    public void update(Entity[] wall) {
        for (Entity e:wall) {
            if (e.hitBox.intersect(hitBox)) {
                velocity.zero();
                break;
            }
        }
        position = position.add(velocity);
        hitBox.setX(position.getX());
        hitBox.setY(position.getY());
        velocity.zero();
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
