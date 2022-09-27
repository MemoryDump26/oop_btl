package entity.Tiles;

import entity.Entity;
import geometry.Point;
import javafx.scene.canvas.GraphicsContext;
import sprite.SpriteData;

public abstract class Tiles extends Entity {
    public Tiles(Point spawn, SpriteData sprite, GraphicsContext gc) {
        super(spawn, sprite, gc);
    }
    public void render(){
        sprite.render(hitBox.getX(), hitBox.getY());
    }

    public void update() {
    }
}

