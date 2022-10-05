package world;

import collision.CollisionComponent;
import entity.Entity;
import entity.StaticEntity;
import geometry.Point;
import input.InputComponent;
import javafx.scene.canvas.GraphicsContext;
import resources.Resources;

public class World {
    GraphicsContext gc;

    Entity[][] matrix;

    public World(GraphicsContext gc) {
        this.gc = gc;
    }

    public void createMap() {
        matrix = new Entity[30][30];
        for (int row = 0; row < 30; row++) {
            for (int col = 0; col < 30; col++) {
                matrix[row][col] = new StaticEntity(
                    spawnAt(row, col),
                    InputComponent.Null,
                    CollisionComponent.Null,
                    Resources.spriteDataMap.get("grass"),
                    gc
                );
            }
        }
    }

    public Point spawnAt(int row, int col) {
        return new Point(16*row, 16*col);
    }

    public void render() {
        for (int row = 0; row < 30; row++) {
            for (int col = 0; col < 30; col++) {
                matrix[row][col].render();
            }
        }
    }
}
