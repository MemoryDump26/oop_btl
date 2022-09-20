package entity;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import sprite.SpriteData;

public class Player extends Entity{
    public Player(Point2D spawn, SpriteData sprite, GraphicsContext gc) {
        super(spawn, sprite, gc);
    }

    public void moveLeft() {
        position = position.add(2, 0);
    }
    public void moveRight() {
        position = position.add(-2, 0);
    }
    public void moveUp() {
        position = position.add(0, -2);
    }
    public void moveDown() {
        position = position.add(0, 2);
    }

}
