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
        input.handle(this);
        position = position.add(velocity.getX(), 0);
        hitBox.setX(position.getX());
        for (Entity e:wall) {
            if (hitBox.intersect(e.hitBox)) {
                System.out.println("Touched along X\n");
                double xOffset;
                if (hitBox.getX() <= e.hitBox.getX()) xOffset = e.hitBox.getX() - (hitBox.getX() + hitBox.getW());
                else xOffset = (e.hitBox.getX() + e.hitBox.getW()) - hitBox.getX();
                position.add(xOffset, 0);
                hitBox.setX(position.getX());
            }
        }
        position = position.add(0, velocity.getY());
        hitBox.setY(position.getY());
        for (Entity e:wall) {
            if (hitBox.intersect(e.hitBox)) {
                System.out.println("Touched along Y\n");
                double yOffset;
                if (hitBox.getY() <= e.hitBox.getY()) yOffset = e.hitBox.getY() - (hitBox.getY() + hitBox.getH());
                else yOffset = (e.hitBox.getY() + e.hitBox.getH()) - hitBox.getY();
                position.add(0, yOffset);
                hitBox.setY(position.getY());
            }
        }
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
