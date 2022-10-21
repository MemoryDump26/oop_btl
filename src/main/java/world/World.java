package world;

import attack.AttackComponent;
import collision.CollisionComponent;
import entity.Entity;
import geometry.Point;
import input.BalloomAI;
import input.InputComponent;
import input.BrickLogic;
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
    public ArrayList<Entity> players = new ArrayList<Entity>();
    public ArrayList<Entity> newSpawn = new ArrayList<Entity>();
    private int width;
    private int height;

    private static Entity pWall;
    private static Entity pBrick;
    private static Entity pGrass;
    private static Entity pBomb;
    private static Entity pFlame;
    private static Entity pBalloom;
    private static Entity pFlamePower;

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT,
    }

    public World(GraphicsContext gc) {
        this.gc = gc;

        pWall = new Entity(
            new Point(0, 0),
            InputComponent.Null,
            CollisionComponent.Static,
            AttackComponent.Null,
            this,
            Resources.spriteDataMap.get("wall"),
            this.gc
        );

        pBrick = new Entity(
            new Point(0, 0),
            InputComponent.Null,
            CollisionComponent.Destructibles,
            AttackComponent.Null,
            this,
            Resources.spriteDataMap.get("brick"),
            this.gc
        );
        pBrick.getSprite().setCurrentAnimation("brick");

        pGrass = new Entity(
            new Point(0, 0),
            InputComponent.Null,
            CollisionComponent.Null,
            AttackComponent.Null,
            this,
            Resources.spriteDataMap.get("grass"),
            gc
        );

        pBomb = new Entity(
            new Point(0, 0),
            InputComponent.Null,
            CollisionComponent.Bomb,
            AttackComponent.Null,
            this,
            Resources.spriteDataMap.get("bomb"),
            gc
        );
        pBomb.getSprite().setCurrentAnimation("bomb");

        pFlame = new Entity(
            new Point(0, 0),
            InputComponent.Null,
            CollisionComponent.Flame,
            AttackComponent.Null,
            this,
            Resources.spriteDataMap.get("explosion"),
            gc
        );
        pFlame.kill();
        pFlame.getSprite().setTickPerFrame(3);
        pFlame.setHarmful(true);

        pBalloom = new Entity(
            new Point(0, 0),
            new BalloomAI(),
            CollisionComponent.Dynamic,
            AttackComponent.Null,
            this,
            Resources.spriteDataMap.get("balloom"),
            gc
        );
        pBalloom.setSpeed(1);
        pBalloom.setHarmful(true);

        pFlamePower = new Entity(
            new Point(0, 0),
            InputComponent.Null,
            CollisionComponent.FlamePower,
            AttackComponent.Null,
            this,
            Resources.spriteDataMap.get("power"),
            gc
        );
        pFlamePower.getSprite().setCurrentAnimation("flames");
    }

    public void createLevelFromFile(File file) {
        try {
            Scanner sc = new Scanner(file);
            num = sc.nextInt();
            height = sc.nextInt();
            width = sc.nextInt();
            sc.nextLine();
            field = new Entity[height][width];
            for (int row = 0; row < height; row++) {
                String tmp = sc.nextLine();
                for (int col = 0; col < width; col++) {
                    System.out.printf("%c", tmp.charAt(col));
                    Entity ins = pGrass;
                    switch (tmp.charAt(col)) {
                        case '#':
                            ins = pWall;
                            break;
                        case '*':
                            ins = pBrick;
                            break;
                        case '1':
                            objects.add(new Entity(spawnAt(row, col), pBalloom));
                            break;
                        case 'f':
                            ins = new Entity(spawnAt(row, col), pBrick);
                            InputComponent inp = new BrickLogic(pFlamePower);
                            ins.setInput(inp);
                            break;
                        default:
                            ins = pGrass;
                            break;
                    }
                    field[row][col] = new Entity(spawnAt(row, col), ins);
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
        Point p = getBoardPosition(e);
        int row = (int)p.getY();
        int col = (int)p.getX();
        for (int i = row - 1; i <= row + 1; i++) {
            if (i < 0 || i > height - 1) continue;
            for (int j = col - 1; j <= col + 1; j++) {
                if (j < 0 || j > width - 1) continue;
                result.add(field[i][j]);
            }
        }
        result.addAll(objects);
        result.addAll(players);
        result.remove(e);

        return result;
    }

    public void spawn(int row, int col, Entity e) {
        newSpawn.add(new Entity(spawnAt(row, col), e));
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
            if (field[row][col].getCollisionState()) {
                animation = "hidden";
            }
            else {
                spawnFlame(row + rowOffset, col + colOffset, power - 1, rowOffset, colOffset);
            }
            pFlame.getSprite().setCurrentAnimation(animation);
            spawn(row, col, pFlame);
        }
    }

    public Point spawnAt(int row, int col) {
        return new Point(col * Globals.cellSize, row * Globals.cellSize);
    }

    public Point getBoardPosition(Entity e) {
        Point p = e.getHitBox().getCenter();
        double row = p.getY() / Globals.cellSize;
        double col = p.getX() / Globals.cellSize;
        return new Point((int)col, (int)row);
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
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                field[row][col].update();
                if (field[row][col].clearable()) {
                    field[row][col] = new Entity(spawnAt(row, col), pGrass);
                }
            }
        }
        objects.removeIf(Entity::clearable);
        players.removeIf(Entity::clearable);
        objects.addAll(newSpawn);
        newSpawn.clear();
        for (Entity e:players) {
            e.update();
        }
        for (Entity e:objects) {
            e.update();
        }
    }

    public void render() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                field[row][col].render();
            }
        }
        for (Entity e:objects) {
            e.render();
        }
        for (Entity e:players) {
            e.render();
        }
    }
}
