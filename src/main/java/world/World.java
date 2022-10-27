package world;

import components.attack.BombAttack;
import components.collision.CollisionComponent;
import components.collision.PowerCollisionComponent;
import components.Component;
import components.input.Command;
import components.input.PlayerInputComponent;
import entity.Entity;
import geometry.Point;
import components.ai.BalloomAI;
import components.ai.OnealAI;
import components.logic.BrickLogic;
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
    public ArrayList<Entity> objects = new ArrayList<>();
    public ArrayList<Entity> players = new ArrayList<>();
    public ArrayList<Entity> enemies = new ArrayList<>();
    public ArrayList<Entity> newSpawn = new ArrayList<>();
    private int width;
    private int height;
    private boolean cleared = false;

    private static Entity pPlayer;
    private static Entity pWall;
    private static Entity pBrick;
    private static Entity pGrass;
    private static Entity pBomb;
    private static Entity pFlame;
    private static Entity pBalloom;
    private static Entity pOneal;
    private static Entity pPower;
    private static Entity pPortal;

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT,
    }

    public World(GraphicsContext gc) {
        this.gc = gc;

        pPlayer = new Entity(
            Point.ZERO,
            Component.getNull(),
            CollisionComponent.Dynamic,
            Component.getNull(),
            this,
            Resources.spriteDataMap.get("player"),
            gc
        );
        pPlayer.setSpeed(3);

        pWall = new Entity(
            Point.ZERO,
            Component.getNull(),
            CollisionComponent.Static,
            Component.getNull(),
            this,
            Resources.spriteDataMap.get("wall"),
            this.gc
        );

        pBrick = new Entity(
            Point.ZERO,
            Component.getNull(),
            CollisionComponent.Destructibles,
            Component.getNull(),
            this,
            Resources.spriteDataMap.get("brick"),
            this.gc
        );
        pBrick.getSprite().setCurrentAnimation("brick");

        pGrass = new Entity(
            Point.ZERO,
            Component.getNull(),
            Component.getNull(),
            Component.getNull(),
            this,
            Resources.spriteDataMap.get("grass"),
            gc
        );

        pBomb = new Entity(
            Point.ZERO,
            Component.getNull(),
            CollisionComponent.Bomb,
            Component.getNull(),
            this,
            Resources.spriteDataMap.get("bomb"),
            gc
        );
        pBomb.getSprite().setCurrentAnimation("bomb");

        pFlame = new Entity(
            Point.ZERO,
            Component.getNull(),
            CollisionComponent.Flame,
            Component.getNull(),
            this,
            Resources.spriteDataMap.get("explosion"),
            gc
        );
        pFlame.kill();
        pFlame.getSprite().setTickPerFrame(3);

        pBalloom = new Entity(
            Point.ZERO,
            Component.getNull(),
            CollisionComponent.Dynamic,
            Component.getNull(),
            this,
            Resources.spriteDataMap.get("balloom"),
            gc
        );
        pBalloom.setSpeed(1);

        pOneal = new Entity(
            Point.ZERO,
            Component.getNull(),
            CollisionComponent.Dynamic,
            Component.getNull(),
            this,
            Resources.spriteDataMap.get("oneal"),
            gc
        );
        pOneal.setSpeed(1);

        pPower = new Entity(
            Point.ZERO,
            Component.getNull(),
            Component.getNull(),
            Component.getNull(),
            this,
            Resources.spriteDataMap.get("power"),
            gc
        );

        pPortal = new Entity(
            Point.ZERO,
            Component.getNull(),
            CollisionComponent.Portal,
            Component.getNull(),
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
            gc.getCanvas().setWidth(width * Globals.cellSize);
            gc.getCanvas().setHeight(height * Globals.cellSize);

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
                            if (isNextLevel && currentPlayer < players.size()) {
                                players.get(currentPlayer).moveTo(col * Globals.cellSize, row * Globals.cellSize);
                                currentPlayer++;
                            }
                            else {
                                PlayerInputComponent inp;
                                if (currentPlayer < Globals.playerKeybinds.size()) {
                                    inp = Globals.playerKeybinds.get(currentPlayer);
                                }
                                else {
                                    inp = new PlayerInputComponent();
                                    Globals.playerKeybinds.add(inp);
                                }
                                spawnPlayer(row, col, inp, new BombAttack(1, 1));
                                currentPlayer++;
                            }
                            break;
                        case '#':
                            ins = pWall;
                            break;
                        case '*':
                            ins = new Entity(spawnAt(row, col), pBrick);
                            ins.setInput(new BrickLogic());
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
                            Entity flamePower = new Entity(new Point(), pPower);
                            flamePower.setCollision(new PowerCollisionComponent(Command.FlamePower));
                            flamePower.getSprite().setCurrentAnimation("flames");
                            ins = new Entity(spawnAt(row, col), pBrick);
                            ins.setInput(new BrickLogic(flamePower));
                            break;
                        case 'b':
                            Entity bombPower = new Entity(new Point(), pPower);
                            bombPower.setCollision(new PowerCollisionComponent(Command.BombPower));
                            bombPower.getSprite().setCurrentAnimation("bombs");
                            ins = new Entity(spawnAt(row, col), pBrick);
                            ins.setInput(new BrickLogic(bombPower));
                            break;
                        case 's':
                            Entity speedPower = new Entity(new Point(), pPower);
                            speedPower.setCollision(new PowerCollisionComponent(Command.SpeedPower));
                            speedPower.getSprite().setCurrentAnimation("speed");
                            ins = new Entity(spawnAt(row, col), pBrick);
                            ins.setInput(new BrickLogic(speedPower));
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
        return getNearbyEntities(e, true, true, true, true);
    }

    public ArrayList<Entity> getAllEntities(
            boolean incStatic,
            boolean incObjects,
            boolean incEnemies,
            boolean incPlayers
    ) {
        ArrayList<Entity> result = new ArrayList<>();
        if (incStatic) result.addAll(getAllStaticEntities());
        if (incObjects) result.addAll(objects);
        if (incEnemies) result.addAll(enemies);
        if (incPlayers) result.addAll(players);
        return result;
    }

    public ArrayList<Entity> getNearbyEntities(
            Entity e,
            boolean incStatic,
            boolean incObjects,
            boolean incEnemies,
            boolean incPlayers
    ) {
        ArrayList<Entity> result = new ArrayList<>();
        if (incStatic) result.addAll(getNearbyStaticEntities(e));
        if (incObjects) result.addAll(getNearby(e, objects));
        if (incEnemies) result.addAll(getNearby(e, enemies));
        if (incPlayers) result.addAll(getNearby(e, players));

        return result;
    }

    public ArrayList<Entity> getAllStaticEntities() {
        ArrayList<Entity> result = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                result.add(field[i][j]);
            }
        }
        return result;
    }

    public ArrayList<Entity> getNearbyStaticEntities(Entity e) {
        ArrayList<Entity> result = new ArrayList<>();
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

    private ArrayList<Entity> getNearby(Entity e, ArrayList<Entity> fromSet) {
        ArrayList<Entity> result = new ArrayList<>();
        Point pE = getBoardPosition(e);
        int eRow = (int)pE.getY();
        int eCol = (int)pE.getX();
        for (Entity m:fromSet) {
            Point pM = getBoardPosition(m);
            int mRow = (int)pM.getY();
            int mCol = (int)pM.getX();
            if (Math.abs(eRow - mRow) < 2 && Math.abs(eCol - mCol) < 2) result.add(m);
        }
        result.remove(e);
        return result;
    }

    public ArrayList<Direction> getAvailableMoves(
            Entity e,
            ArrayList<Entity> constraintSet
    ) {
        Point p = getBoardPosition(e);
        int row = (int) p.getY();
        int col = (int) p.getX();
        /*int row = getCurrentRow(e);
        int col = getCurrentCol(e);*/
        ArrayList<Direction> result = new ArrayList<>();
        if (!isOccupied(row - 1, col, constraintSet)) result.add(Direction.UP);
        if (!isOccupied(row + 1, col, constraintSet)) result.add(Direction.DOWN);
        if (!isOccupied(row, col - 1, constraintSet)) result.add(Direction.LEFT);
        if (!isOccupied(row, col + 1, constraintSet)) result.add(Direction.RIGHT);
        return result;
    }

    public boolean isOccupied(int row, int col, ArrayList<Entity> constraintSet) {
        for (Entity e:constraintSet) {
            if (!e.getCollisionState()) continue;
            Point p = getBoardPosition(e);
            int eRow = (int) p.getY();
            int eCol = (int) p.getX();
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
        if (e == null) return;
        newSpawn.add(new Entity(spawnAt(row, col), e));
    }

    public void spawnPlayer(int row, int col, PlayerInputComponent input, Component<Entity> attack) {
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
        }
        if (cleared) {
            Resources.soundDataMap.get("next_level").play();
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
