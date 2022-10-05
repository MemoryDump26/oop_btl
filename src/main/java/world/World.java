package world;

import collision.CollisionComponent;
import entity.Entity;
import entity.StaticEntity;
import geometry.Point;
import input.InputComponent;
import javafx.scene.canvas.GraphicsContext;
import resources.Resources;
import options.Globals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class World {
    private GraphicsContext gc;

    private int num;
    private Entity[][] matrix;
    private int width;
    private int height;

    private static StaticEntity pWall;
    private static StaticEntity pBrick;
    private static StaticEntity pGrass;

    public World(GraphicsContext gc) {
        this.gc = gc;

        pWall = new StaticEntity(
            new Point(0, 0),
            InputComponent.Null,
            CollisionComponent.Static,
            Resources.spriteDataMap.get("wall"),
            this.gc
        );

        pBrick = new StaticEntity(
            new Point(0, 0),
            InputComponent.Null,
            CollisionComponent.Static,
            Resources.spriteDataMap.get("brick"),
            this.gc
        );

        pGrass = new StaticEntity(
            new Point(0, 0),
            InputComponent.Null,
            CollisionComponent.Null,
            Resources.spriteDataMap.get("grass"),
            gc
        );
    }

    public void createLevelFromFile(File file) {
        try {
            Scanner sc = new Scanner(file);
            num = sc.nextInt();
            width = sc.nextInt();
            height = sc.nextInt();
            sc.nextLine();
            matrix = new Entity[width][height];
            for (int row = 0; row < width; row++) {
                String tmp = sc.nextLine();
                for (int col = 0; col < height; col++) {
                    System.out.printf("%c", tmp.charAt(col));
                    StaticEntity ins;
                    switch (tmp.charAt(col)) {
                        case '#':
                            ins = pWall;
                            break;
                        case '*':
                            ins = pBrick;
                            break;
                        default:
                            ins = pGrass;
                            break;
                    }
                    matrix[row][col] = new StaticEntity(spawnAt(row, col), ins);
                }
                System.out.println();
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.printf("Level file not found!!!\n");
        }
    }

    public ArrayList<Entity> getNearbyEntities(Entity e) {
        ArrayList<Entity> result = new ArrayList<Entity>();
        int row = getCurrentRow(e);
        int col = getCurrentCol(e);
        System.out.printf("%d, %d\n", row, col);
        result.add(matrix[row-1][col-1]);
        result.add(matrix[row-1][col]);
        result.add(matrix[row-1][col+1]);
        result.add(matrix[row][col-1]);
        result.add(matrix[row][col]);
        result.add(matrix[row][col+1]);
        result.add(matrix[row+1][col-1]);
        result.add(matrix[row+1][col]);
        result.add(matrix[row+1][col+1]);

        return result;
    }

    public Point spawnAt(int row, int col) {
        return new Point(col * Globals.cellSize, row * Globals.cellSize);
    }

    public int getCurrentRow(Entity e) {
        double col = e.getHitBox().getY() / Globals.cellSize;
        return (int)col;
    }

    public int getCurrentCol(Entity e) {
        double row = e.getHitBox().getX() / Globals.cellSize;
        return (int)row;
    }

    public void render() {
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                matrix[row][col].render();
            }
        }
    }
}
