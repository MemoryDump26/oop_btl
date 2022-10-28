package world;

import components.EntityComponents;
import components.ai.KondoriaAI;
import components.astar.PathFinder;
import components.attack.BombAttack;
import components.collision.CollisionComponent;
import components.collision.PowerCollisionComponent;
import components.Component;
import components.commands.*;
import components.commands.concrete.PlaySoundCommand;
import components.input.KeyboardInputComponent;
import components.logic.CommandOnDead;
import entity.Entity;
import geometry.Point;
import components.ai.BalloomAI;
import components.ai.OnealAI;
import components.logic.BrickLogic;
import javafx.scene.canvas.GraphicsContext;
import resources.Resources;
import options.Globals;
import sprite.Sprite;

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

    private static Entity pNull;
    private static Entity pPlayer;
    private static Entity pWall;
    private static Entity pBrick;
    private static Entity pGrass;
    private static Entity pBomb;
    private static Entity pFlame;
    private static Entity pBalloom;
    private static Entity pKondoria;
    private static Entity pOneal;
    private static Entity pPower;
    private static Entity pPortal;

    private static PathFinder pathFinder;

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT,
    }

    public World(GraphicsContext gc) {
        this.gc = gc;

        pNull = new Entity(Point.ZERO, this, new Sprite(Resources.getSprite("grass"), gc));

        IndieCommand playPlayerDyingNoise = new PlaySoundCommand("player_die");
        Component<Entity> onPlayerDied = new CommandOnDead(playPlayerDyingNoise);
        pPlayer = new Entity(pNull);
        pPlayer.addAuxiliaryComponent(onPlayerDied);
        pPlayer.setCollision(CollisionComponent.Dynamic);
        pPlayer.setSprite(new Sprite(Resources.getSprite("player"), gc));
        pPlayer.setSpeed(3);

        pWall = new Entity(pNull);
        pWall.setCollision(CollisionComponent.Static);
        pWall.setSprite(new Sprite(Resources.getSprite("wall"), gc));

        pBrick = new Entity(pNull);
        pBrick.setCollision(CollisionComponent.Destructibles);
        pBrick.setSprite(new Sprite(Resources.getSprite("brick"), gc));
        pBrick.getSprite().setCurrentAnimation("brick");

        pGrass = new Entity(pNull);

        pFlame = new Entity(pNull);
        pFlame.setCollision(CollisionComponent.Flame);
        pFlame.setSprite(new Sprite(Resources.getSprite("explosion"), gc));
        pFlame.getSprite().setLoop(false);
        pFlame.getSprite().setTickPerFrame(5);

        Entity enemyTemplate = new Entity(pNull);
        enemyTemplate.addAuxiliaryComponent(EntityComponents.KillPlayerOnTouch);
        enemyTemplate.setCollision(CollisionComponent.Dynamic);
        enemyTemplate.setSpeed(1);

        pBalloom = new Entity(enemyTemplate);
        pBalloom.setSprite(new Sprite(Resources.getSprite("balloom"), gc));

        pOneal = new Entity(enemyTemplate);
        pOneal.setSprite(new Sprite(Resources.getSprite("oneal"), gc));

        pKondoria = new Entity(enemyTemplate);
        pKondoria.setSprite(new Sprite(Resources.getSprite("kondoria"), gc));

        pPower = new Entity(pNull);
        pPower.setSprite(new Sprite(Resources.getSprite("power"), gc));

        pPortal = new Entity(pNull);
        pPortal.setCollision(CollisionComponent.Portal);
        pPortal.setSprite(new Sprite(Resources.getSprite("portal"), gc));
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
                                KeyboardInputComponent inp;
                                if (currentPlayer < Globals.playerKeybinds.size()) {
                                    inp = Globals.playerKeybinds.get(currentPlayer);
                                }
                                else {
                                    inp = new KeyboardInputComponent();
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
                        case '3':
                            Entity kondoria = new Entity(spawnAt(row, col), pKondoria);
                            kondoria.setInput(new KondoriaAI());
                            kondoria.setCollision(CollisionComponent.Destructibles);
                            kondoria.setCollisionState(false);
                            enemies.add(kondoria);
                            break;
                        case 'x':
                            Component<Entity> portalItem = EntityComponents.SpawnEntityOnDeadComponent(row, col, pPortal, this);
                            ins = new Entity(spawnAt(row, col), pBrick);
                            ins.addAuxiliaryComponent(portalItem);
                            break;
                        case 'f':
                            Entity flamePower = new Entity(pPower);
                            flamePower.setCollision(new PowerCollisionComponent(EntityCommands.FlamePower));
                            flamePower.getSprite().setCurrentAnimation("flames");
                            Component<Entity> flameItem = EntityComponents.SpawnEntityOnDeadComponent(row, col, flamePower, this);
                            ins = new Entity(spawnAt(row, col), pBrick);
                            ins.addAuxiliaryComponent(flameItem);
                            break;
                        case 'b':
                            Entity bombPower = new Entity(pPower);
                            bombPower.setCollision(new PowerCollisionComponent(EntityCommands.BombPower));
                            bombPower.getSprite().setCurrentAnimation("bombs");
                            Component<Entity> bombItem = EntityComponents.SpawnEntityOnDeadComponent(row, col, bombPower, this);
                            ins = new Entity(spawnAt(row, col), pBrick);
                            ins.addAuxiliaryComponent(bombItem);
                            break;
                        case 's':
                            Entity speedPower = new Entity(new Point(), pPower);
                            speedPower.setCollision(new PowerCollisionComponent(EntityCommands.SpeedPower));
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
            pathFinder = new PathFinder(width, height, this);
        } catch (FileNotFoundException e) {
            System.out.printf("Level file not found!!!\n");
        }
    }

    public ArrayList<Entity> getWorldBoundEntities() {
        ArrayList<Entity> result = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            result.add(field[row][0]);
            result.add(field[row][width - 1]);
        }
        for (int col = 1; col < width - 1; col++) {
            result.add(field[0][col]);
            result.add(field[height - 1][col]);
        }
        return result;
    }

    public ArrayList<Entity> getNearbyEntities(Entity e) {
        return getNearbyEntities(e, true, true, true, true);
    }

    public ArrayList<Entity> getAllEntities() {
        return getAllEntities(true, true, true, true);
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

    public void spawnPlayer(int row, int col, KeyboardInputComponent input, Component<Entity> attack) {
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
            Resources.getSound("next_level").play();
            createLevelFromFile(Resources.getLevel(num + 1), true);
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
        Point p = getBoardPosition(players.get(0));
        pathFinder.drawPath(pathFinder.getPath((int)p.getY(), (int)p.getX(), 9, 9), gc);
    }
}
