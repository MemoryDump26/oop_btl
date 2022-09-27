package entity.Tiles;

import entity.Entity;
import geometry.Point;
import javafx.scene.canvas.GraphicsContext;
import sprite.SpriteData;

public abstract class BrickTile extends Entity {
    public BrickTile(Point spawn, SpriteData sprite, GraphicsContext gc) {
        super(spawn, sprite, gc);
    }
}

