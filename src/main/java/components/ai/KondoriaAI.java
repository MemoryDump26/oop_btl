package components.ai;

import entity.Entity;
import geometry.Circle;
import geometry.Point;
import geometry.Rectangle;
import timer.Timer;
import world.World;

public class KondoriaAI extends GhostMovementAI {
    private Circle chaseRadius = new Circle(300);
    private boolean feared = false;
    Timer chaseCooldown = new Timer(3);

    public void setFeared(boolean feared) {
        this.feared = feared;
        chaseCooldown.start();
    }

    @Override
    public void handle(Entity e) {
        if (e.isDead()) return;
        World w = e.getWorld();
        Point ePos = e.getHitBox().getCenter();
        double minDistance = Double.MAX_VALUE;
        Point nearest = null;
        chaseRadius.setCenter(ePos);

        if (chaseCooldown.isFinished()) {
            feared = false;
            chaseCooldown.stop();
        }

        for (Entity p : w.getAllEntities(false, false, false, true)) {
            Point pPoint = p.getHitBox().getCenter();
            double distance = ePos.getDistance(pPoint);
            if (chaseRadius.contains(pPoint) && distance < minDistance) {
                nearest = pPoint;
                minDistance = distance;
            }
        }

        if (nearest != null) {
            Rectangle eBox = e.getHitBox();
            velocityVector = nearest.subtract(ePos).normalize().multiply(speed);
            if (feared) velocityVector.multiply(-2);

            for (Entity b : w.getWorldBoundEntities()) {
                Rectangle pBox = b.getHitBox();
                if (pBox.intersect(eBox, 0, velocityVector.getY())) {
                    velocityVector.setY(-1 * velocityVector.getY());
                    break;
                }
                if (pBox.intersect(eBox, velocityVector.getX(), 0)) {
                    velocityVector.setX(-1 * velocityVector.getX());
                    break;
                }
            }
            eBox.move(velocityVector);
        }
        else super.handle(e);

        StringBuffer animation = new StringBuffer();
        if (feared) animation.append("fear");
        else if (minDistance <= 100) animation.append("canfear");
        if (velocityVector.getX() < 0) animation.append("left");
        else animation.append("right");
        e.getSprite().setCurrentAnimation(animation.toString());
    }
}
