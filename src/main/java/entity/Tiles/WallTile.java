package entity.Tiles;

import entity.Entity;
import geometry.Point;
import javafx.scene.canvas.GraphicsContext;
import sprite.SpriteData;

public class WallTile extends Entity {
    public WallTile(Point spawn, SpriteData sprite, GraphicsContext gc) {
        super(spawn, sprite, gc);
    }

    @Override
    public void moveUp() {

    }

    @Override
    public void moveDown() {

    }

    @Override
    public void moveLeft() {

    }

    @Override
    public void moveRight() {

    }
}
