package entity.Tiles;

import geometry.Point;
import javafx.scene.canvas.GraphicsContext;
import sprite.SpriteData;

public abstract class WallTile extends Tiles {
    public WallTile(Point spawn, SpriteData sprite, GraphicsContext gc) {
        super(spawn, sprite, gc);
    }
}
