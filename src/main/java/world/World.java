package world;

import attack.AttackComponent;
import attack.BombAttack;
import collision.CollisionComponent;
import entity.Entity;
import geometry.Point;
import input.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
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
    public ArrayList<Entity> enemies = new ArrayList<Entity>();
    public ArrayList<Entity> newSpawn = new ArrayList<Entity>();
    private int width;
    private int height;
    private boolean cleared = false;

    private PlayerInputComponent p1Inp = new PlayerInputComponent();
    private PlayerInputComponent p2Inp = new PlayerInputComponent();

    private static Entity pPlayer;
    private static Entity pWall;
    private static Entity pBrick;
    private static Entity pGrass;
    private static Entity pBomb;
    private static Entity pFlame;
    private static Entity pBalloom;
    private static Entity pOneal;
    private static Entity pFlamePower;
    private static Entity pBombPower;
    private static Entity pSpeedPower;
    private static Entity pPortal;

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT,
    }

    public World(GraphicsContext gc) {
        this.gc = gc;

        p1Inp.addKeybind(KeyCode.W, Command.Up, "hold");
        p1Inp.addKeybind(KeyCode.A, Command.Left, "hold");
        p1Inp.addKeybind(KeyCode.S, Command.Down, "hold");
        p1Inp.addKeybind(KeyCode.D, Command.Right, "hold");
        p1Inp.addKeybind(KeyCode.J, Command.Attack, "press");

        pPlayer = new Entity(
            new Point(0, 0),
            InputComponent.Null,
            CollisionComponent.Dynamic,
            AttackComponent.Null,
            this,
            Resources.spriteDataMap.get("player"),
            gc
        );
        pPlayer.setSpeed(3);

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
            InputComponent.Null,
            CollisionComponent.Dynamic,
            AttackComponent.Null,
            this,
            Resources.spriteDataMap.get("balloom"),
            gc
        );
        pBalloom.setSpeed(1);
        pBalloom.setHarmful(true);

        pOneal = new Entity(
            new Point(0, 0),
            InputComponent.Null,
            CollisionComponent.Dynamic,
            AttackComponent.Null,
            this,
            Resources.spriteDataMap.get("oneal"),
            gc
        );
        pOneal.setSpeed(1);
        pOneal.setHarmful(true);

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

        pBombPower = new Entity(
            new Point(0, 0),
            InputComponent.Null,
            CollisionComponent.BombPower,
            AttackComponent.Null,
            this,
            Resources.spriteDataMap.get("power"),
            gc
        );
        pBombPower.getSprite().setCurrentAnimation("bombs");

        pSpeedPower = new Entity(
            new Point(0, 0),
            InputComponent.Null,
            CollisionComponent.SpeedPower,
            AttackComponent.Null,
            this,
            Resources.spriteDataMap.get("power"),
            gc
        );
        pSpeedPower.getSprite().setCurrentAnimation("speed");

        pPortal = new Entity(
            new Point(0, 0),
            InputComponent.Null,
            CollisionComponent.Portal,
            AttackComponent.Null,
            this,
            Resources.spriteDataMap.get("portal"),
            gc
        );
    }

    public void createLevelFromFile(File file, boolean isNextLevel) {
        try {
            Scanner sc = new Scanner(file);
            num = sc.nextInt();
            height = sc.nextInt();
            width = sc.nextInt();
            sc.nextLine();
            field = new Entity[height][width];

            objects.clear();
            enemies.clear();
            newSpawn.clear();
            if (!isNextLevel) players.clear();
            int currentPlayer = 0;
            for (int row = 0; row < height; row++) {
                String tmp = sc.nextLine();
                for (int col = 0; col < width; col++) {
                    System.out.printf("%c", tmp.charAt(col));
                    Entity ins = pGrass;
                    switch (tmp.charAt(col)) {
                        case 'p':
                            if (currentPlayer < players.size()) {
                                players.get(currentPlayer).moveTo(row * Globals.cellSize, col * Globals.cellSize);
                                currentPlayer++;
                            }
                            else {
                                spawnPlayer(row, col, p1Inp, new BombAttack(1, 1));
                            }
                            break;
                        case '#':
                            ins = pWall;
                            break;
                        case '*':
                            ins = pBrick;
                            break;
                        case '1':
                            Entity balloom = new Entity(spawnAt(row, col), pBalloom);
                            balloom.setInput(new BalloomAI());
                            enemies.add(balloom);
                            break;
                        case '2':
                            Entity oneal = new Entity(spawnAt(row, col), pOneal);
                            oneal.setInput(new OnealAI());
                            enemies.add(oneal);
                            break;
                        case 'x':
                            ins = new Entity(spawnAt(row, col), pBrick);
                            ins.setInput(new BrickLogic(pPortal));
                            break;
                        case 'f':
                            ins = new Entity(spawnAt(row, col), pBrick);
                            ins.setInput(new BrickLogic(pFlamePower));
                            break;
                        case 'b':
                            ins = new Entity(spawnAt(row, col), pBrick);
                            ins.setInput(new BrickLogic(pBombPower));
                            break;
                        case 's':
                            ins = new Entity(spawnAt(row, col), pBrick);
                            ins.setInput(new BrickLogic(pSpeedPower));
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
        result.addAll(getNearbyStaticEntities(e));
        result.addAll(objects);
        result.addAll(enemies);
        result.addAll(getNearbyPlayers(e));
        result.remove(e);

        return result;
    }

    public ArrayList<Entity> getNearbyStaticEntities(Entity e) {
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
        result.remove(e);
        return result;
    }

    public ArrayList<Entity> getNearbyPlayers(Entity e) {
        ArrayList<Entity> result = new ArrayList<Entity>();
        result.addAll(players);
        result.remove(e);
        return result;
    }

    public ArrayList<Direction> getAvailableMoves(Entity e) {
        Point p = getBoardPosition(e);
        int row = (int) p.getY();
        int col = (int) p.getX();
        ArrayList<Direction> result = new ArrayList<>();
        if (col >= 0 && col < width) {
            if (row > 0 && !field[row - 1][col].getCollisionState()) result.add(Direction.UP);
            if (row < height - 1 && !field[row + 1][col].getCollisionState()) result.add(Direction.DOWN);
        }
        if (row >= 0 && row < height) {
            if (col > 0 && !field[row][col - 1].getCollisionState()) result.add(Direction.LEFT);
            if (col < width - 1 && !field[row][col + 1].getCollisionState()) result.add(Direction.RIGHT);
        }
        return result;
    }

    public boolean isOccupied(int row, int col) {
        if (field[row][col].getCollisionState()) return true;
        for (Entity e:objects) {
            int eRow = getCurrentRow(e);
            int eCol = getCurrentCol(e);
            if (eRow == row && eCol == col) return true;
        }
        for (Entity e:enemies) {
            int eRow = getCurrentRow(e);
            int eCol = getCurrentCol(e);
            if (eRow == row && eCol == col) return true;
        }
        for (Entity e:players) {
            int eRow = getCurrentRow(e);
            int eCol = getCurrentCol(e);
            if (eRow == row && eCol == col) return true;
        }
        return false;
    }

    public boolean isLevelCleared() {
        return enemies.isEmpty();
    }

    public void setCleared(boolean cleared) {
        this.cleared = cleared;
    }

    public void spawn(int row, int col, Entity e) {
        newSpawn.add(new Entity(spawnAt(row, col), e));
    }

    public void spawnPlayer(int row, int col, InputComponent input, AttackComponent attack) {
        Entity p = new Entity(spawnAt(row, col), pPlayer);
        p.setInput(input);
        p.setAttack(attack);
        p.setDestructible(false);
        players.add(p);
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
        enemies.removeIf(Entity::clearable);
        players.removeIf(Entity::clearable);
        objects.addAll(newSpawn);
        newSpawn.clear();
        for (Entity e:objects) {
            e.update();
        }
        for (Entity e:enemies) {
            e.update();
        }
        for (Entity e:players) {
            e.update();
            //System.out.printf("%f, %f\n", e.getHitBox().getX(), e.getHitBox().getY());
        }
        if (cleared) {
            createLevelFromFile(Resources.levelList.get((num + 1) % Resources.levelList.size()), true);
            cleared = false;
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
        for (Entity e:enemies) {
            e.render();
        }
        for (Entity e:players) {
            e.render();
        }
    }
}
