package components.ai;

import components.commands.IndieCommand;
import components.commands.TargetedCommand;
import components.commands.concrete.RemoteCommand;
import entity.Entity;
import geometry.Circle;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import timer.Timer;
import world.World;

import java.util.ArrayList;

import static javafx.scene.paint.Color.RED;

public class LaserGunnerAI extends RandomMovementAI {
    private Timer laserCooldown = new Timer(3);
    private Timer laserChargeTime = new Timer(3);
    private Circle laserRange = new Circle(300);
    private Point nearestTarget = null;
    public LaserGunnerAI(boolean incStatic, boolean incObjects, boolean incEnemies, boolean incPlayers) {
        super(incStatic, incObjects, incEnemies, incPlayers);
    }

    public LaserGunnerAI() {
        super(true, true, true, false);
    }

    @Override
    public void onAttach(Entity e) {
        laserCooldown.start();
        super.onAttach(e);
    }

    @Override
    public void handle(Entity e) {
        if (e.isDead()) return;
        World w = e.getWorld();
        Rectangle eBox = e.getHitBox();
        Point pLaserGunner = eBox.getCenter();
        nearestTarget = null;

        if (!laserCooldown.isFinished()) super.handle(e);
        else {
            ArrayList<Entity> players = w.getAllEntities(false, false, false, true);
            for (Entity p : players) {
                Point pPlayer = p.getHitBox().getCenter();
                if (nearestTarget == null) nearestTarget = pPlayer;
                else if (pLaserGunner.getDistance(pPlayer) < pLaserGunner.getDistance(nearestTarget)) {
                    nearestTarget = pPlayer;
                }
            }
            Line laser = new Line(pLaserGunner, nearestTarget);
            laser.lengthenEnd(1920);

            TargetedCommand<GraphicsContext> drawLaser = new TargetedCommand<>() {
                @Override
                public void execute(GraphicsContext gc) {
                    gc.setStroke(RED);
                    gc.setLineWidth(10);
                    gc.strokeLine(pLaserGunner.getX(), pLaserGunner.getY(), nearestTarget.getX(), nearestTarget.getY());
                }
            };
            RemoteCommand<GraphicsContext> drawLaserCommand = new RemoteCommand<>(drawLaser, w.getGc());
            w.commandsAfterDraw.add(drawLaserCommand);

            ArrayList<Entity> canDestroy = w.getAllEntities(true, false, true, true);
            canDestroy.remove(e);
            for (Entity m : canDestroy) {
                Point pM = m.getHitBox().getCenter();
                if (laser.getDistance(pM) < 20) {
                    m.kill();
                }
            }
        }
    }

    public void attack(Entity e, World w) {
    }
}
