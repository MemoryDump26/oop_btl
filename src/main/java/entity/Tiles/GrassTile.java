package entity.Tiles;

import geometry.Point;
import javafx.scene.canvas.GraphicsContext;
import sprite.SpriteData;

public abstract class GrassTile extends Tiles {
    public GrassTile(Point spawn, SpriteData sprite, GraphicsContext gc) {
        super(spawn, sprite, gc);
    }
}
