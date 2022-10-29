package components.ai;

import components.commands.IndieCommand;
import components.commands.TargetedCommand;
import components.commands.concrete.RemoteCommand;
import entity.Entity;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import options.Globals;
import timer.Timer;
import world.World;

import java.util.ArrayList;

import static javafx.scene.paint.Color.RED;

public class LaserGunnerAI extends RandomMovementAI {
    private Timer laserCooldown = new Timer(3);
    private Timer laserChargeTime = new Timer(3);
    private Timer laserHoldTime = new Timer(0.2);
    private Point target = null;
    private Line laser = new Line();
    IndieCommand laserDrawCommand = IndieCommand.getNull();
    public LaserGunnerAI(boolean incStatic, boolean incObjects, boolean incEnemies, boolean incPlayers) {
        super(incStatic, incObjects, incEnemies, incPlayers);
    }

    public LaserGunnerAI() {
        super(true, true, true, false);
    }

    @Override
    public void onAttach(Entity e) {
        super.onAttach(e);
    }

    @Override
    public void handle(Entity e) {
        if (e.isDead()) return;
        World w = e.getWorld();
        Rectangle eBox = e.getHitBox();
        Point pLaserGunner = eBox.getCenter();
        laser.setStart(eBox.getCenter());

        if (!laserHoldTime.isFinished()) {
            w.commandsAfterDraw.add(laserDrawCommand);
        }
        else {
            laserDrawCommand = IndieCommand.getNull();
        }

        if (laserCooldown.isFinished()) laserCooldown.stop();
        if (laserCooldown.isRunning()) {
            super.handle(e);
            return;
        }

        Point pNearestPlayer = null;
        ArrayList<Entity> players = w.getAllEntities(false, false, false, true);
        for (Entity p : players) {
            Point pPlayer = p.getHitBox().getCenter();
            if (pNearestPlayer == null) pNearestPlayer = pPlayer;
            if (pLaserGunner.getDistance(pPlayer) < pLaserGunner.getDistance(pNearestPlayer)) {
                pNearestPlayer = pPlayer;
            }
        }

        if (pLaserGunner.getDistance(pNearestPlayer) < 200) target = pNearestPlayer;
        if (target == null) {
            super.handle(e);
            return;
        }

        laser.setEnd(target);

        if (laserChargeTime.isRunning()) {
            laser.lengthenEnd(1920);
            laserDrawCommand = createLaserCommand(laser, 3, w.getGc());
            w.commandsAfterDraw.add(laserDrawCommand);
        }
        else {
            laserChargeTime.start();
        }

        if (laserChargeTime.isFinished()) {
            laser.lengthenEnd(1920);
            laserDrawCommand = createLaserCommand(laser, Globals.cellSize / 2, w.getGc());
            w.commandsAfterDraw.add(laserDrawCommand);
            ArrayList<Entity> canDestroy = w.getAllEntities(true, false, true, true);
            canDestroy.remove(e);
            for (Entity m : canDestroy) {
                Point pM = m.getHitBox().getCenter();
                if (laser.getDistance(pM) < 20) {
                    m.kill();
                }
            }
            laserCooldown.start();
            laserHoldTime.start();
            laserChargeTime.stop();
            target = null;
        }
    }

    private IndieCommand createLaserCommand(Line laser, double width, GraphicsContext gc) {
        TargetedCommand<GraphicsContext> drawLaser = new TargetedCommand<>() {
            @Override
            public void execute(GraphicsContext gc) {
                gc.setStroke(RED);
                gc.setLineWidth(width);
                gc.strokeLine(laser.getStart().getX(), laser.getStart().getY(), laser.getEnd().getX(), laser.getEnd().getY());
            }
        };
        RemoteCommand<GraphicsContext> drawLaserCommand = new RemoteCommand<>(drawLaser, gc);
        return drawLaserCommand;
    }
}
