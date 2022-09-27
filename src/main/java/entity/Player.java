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
    //private InputComponent input;

    public Player(Point spawn, InputComponent input, SpriteData sprite, GraphicsContext gc) {
        super(spawn, sprite, gc);
        //this.input = input;
    }

    public void update(Entity[] wall) {
        //input.handle(this);
        hitBox.move(velocity.getX(), 0);
        for (Entity e:wall) {
            if (hitBox.intersect(e.hitBox)) {
                double xOffset;
                if (hitBox.getX() <= e.hitBox.getX()) xOffset = e.hitBox.getX() - (hitBox.getX() + hitBox.getW());
                else xOffset = (e.hitBox.getX() + e.hitBox.getW()) - hitBox.getX();
                hitBox.move(xOffset, 0);
            }
        }
        hitBox.move(0, velocity.getY());
        for (Entity e:wall) {
            if (hitBox.intersect(e.hitBox)) {
                double yOffset;
                if (hitBox.getY() <= e.hitBox.getY()) yOffset = e.hitBox.getY() - (hitBox.getY() + hitBox.getH());
                else yOffset = (e.hitBox.getY() + e.hitBox.getH()) - hitBox.getY();
                hitBox.move(0, yOffset);
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
