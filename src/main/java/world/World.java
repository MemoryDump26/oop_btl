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
    public Entity[][] field;
    public ArrayList<Entity> objects = new ArrayList<Entity>();
    private int width;
    private int height;

    private static StaticEntity pWall;
    private static StaticEntity pBrick;
    private static StaticEntity pGrass;
    public static StaticEntity pBomb;
    public static StaticEntity pFlame;

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
        pBrick.getSprite().setCurrentAnimation("brick");

        pGrass = new StaticEntity(
            new Point(0, 0),
            InputComponent.Null,
            CollisionComponent.Null,
            Resources.spriteDataMap.get("grass"),
            gc
        );

        pBomb = new StaticEntity(
            new Point(0, 0),
            InputComponent.Null,
            CollisionComponent.Bomb,
            Resources.spriteDataMap.get("bomb"),
            gc
        );
        pBomb.getSprite().setCurrentAnimation("bomb");

        pFlame = new StaticEntity(
            new Point(0, 0),
            InputComponent.Null,
            CollisionComponent.Null,
            Resources.spriteDataMap.get("explosion"),
            gc
        );
        pFlame.kill();
        /*pFlame.getSprite().setLoop(false);
        pFlame.getSprite().setCurrentAnimation("center");*/

    }

    public void createLevelFromFile(File file) {
        try {
            Scanner sc = new Scanner(file);
            num = sc.nextInt();
            width = sc.nextInt();
            height = sc.nextInt();
            sc.nextLine();
            field = new Entity[width][height];
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
                    field[row][col] = new StaticEntity(spawnAt(row, col), ins);
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
        result.add(field[row-1][col-1]);
        result.add(field[row-1][col]);
        result.add(field[row-1][col+1]);
        result.add(field[row][col-1]);
        result.add(field[row][col]);
        result.add(field[row][col+1]);
        result.add(field[row+1][col-1]);
        result.add(field[row+1][col]);
        result.add(field[row+1][col+1]);
        result.addAll(objects);
        // ???.
        result.remove(e);

        return result;
    }

    public void spawn(int row, int col, StaticEntity e) {
        objects.add(new StaticEntity(spawnAt(row, col), e));
    }

    public void spawnFlame(int row, int col, int power, int rowOffset, int colOffset) {
        if (power < 0) return;

        if (rowOffset == 0 && colOffset == 0) {
            pFlame.getSprite().setCurrentAnimation("center");
            spawn(row, col, pFlame);
            spawnFlame(row - 1, col, power - 1, -1, 0);
            spawnFlame(row + 1, col, power - 1, 1, 0);
            spawnFlame(row, col - 1, power - 1, 0, -1);
            spawnFlame(row, col + 1, power - 1, 0, 1);
        }
        else {
            String animation = new String();
            if (power >= 1) {
                if (rowOffset == 0) animation = "horizontal";
                else animation = "vertical";
            }
            else {
                if (rowOffset == -1 && colOffset == 0) animation = "verticalUp";
                if (rowOffset == 1 && colOffset == 0) animation = "verticalDown";
                if (rowOffset == 0 && colOffset == -1) animation = "horizontalLeft";
                if (rowOffset == 0 && colOffset == 1) animation = "horizontalRight";
            }
            pFlame.getSprite().setCurrentAnimation(animation);
            spawn(row, col, pFlame);
            spawnFlame(row + rowOffset, col + colOffset, power - 1, rowOffset, colOffset);
        }
    }

    public Point spawnAt(int row, int col) {
        return new Point(col * Globals.cellSize, row * Globals.cellSize);
    }

    public int getCurrentRow(Entity e) {
        double row = e.getHitBox().getY() / Globals.cellSize;
        return (int)row;
    }

    public int getCurrentCol(Entity e) {
        double col = e.getHitBox().getX() / Globals.cellSize;
        return (int)col;
    }

    public void update() {
        objects.removeIf(Entity::isDead);
        for (Entity e:objects) {
            e.update(getNearbyEntities(e));
        }
    }

    public void render() {
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                field[row][col].render();
            }
        }
        for (Entity e:objects) {
            e.render();
        }
    }
}
