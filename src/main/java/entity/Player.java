package entity;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import sprite.SpriteData;

public class Player extends Entity{
    private int power = 1;
    private double speed = 3;
    private Point2D velocity = new Point2D(0, 0);

    public Player(Point2D spawn, SpriteData sprite, GraphicsContext gc) {
        super(spawn, sprite, gc);
    }

    public void update(Entity[] wall) {
        for (Entity e:wall) {
            Rectangle2D tmp = new Rectangle2D(
                hitBox.getMinX() + velocity.getX(), hitBox.getMinY() + velocity.getY(),
                hitBox.getWidth(), hitBox.getHeight()
            );
            System.out.println(e.hitBox.getMinX() + "???" + e.hitBox.getMinY());
            if (e.hitBox.intersects(tmp)) {
                velocity = Point2D.ZERO;
            }
        }
        position = position.add(velocity);
        // Seriously where tf is the setter???
        // Fine imma do it my self
        hitBox = new Rectangle2D(position.getX(), position.getY(), hitBox.getWidth(), hitBox.getHeight());
        velocity = Point2D.ZERO;
    }
    public void stop() {

    }
    public void move(double x, double y) {
        // Where the heck is Point2D setter????
        velocity = velocity.add(x, y);
    }
    public void moveLeft() {
    }
    public void moveRight() {
    }
    public void moveUp() {
    }
    public void moveDown() {
    }
    public void bomb() {
    }
}
