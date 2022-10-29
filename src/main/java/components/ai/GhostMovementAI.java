package components.ai;

import components.Component;
import entity.Entity;
import geometry.Point;
import geometry.Rectangle;
import world.World;

import java.util.ArrayList;
import java.util.Random;

public class GhostMovementAI extends Component<Entity> {
    protected double speed = 1;
    protected Point velocityVector = new Point(1, 1);
    protected Point accelVector;
    protected ArrayList<Entity> worldBound = new ArrayList<>();
    protected Random r = new Random();

    @Override
    public void onAttach(Entity e) {
    }

    @Override
    public void handle(Entity e) {
        if (e.isDead()) return;
        World w = e.getWorld();
        Rectangle eBox = e.getHitBox();

        accelVector = new Point(velocityVector).rotate(r.nextDouble(0.2) - 0.1).normalize().multiply(speed, speed).subtract(velocityVector);
        velocityVector = velocityVector.add(accelVector);
        for (Entity b : w.getWorldBoundEntities()) {
            Rectangle pBox = b.getHitBox();
            if (pBox.intersect(eBox, 0, -1) || pBox.intersect(eBox, 0, 1)) {
                velocityVector.setY(-1 * velocityVector.getY());
                break;
            }
            if (pBox.intersect(eBox, -1, 0) || pBox.intersect(eBox, 1, 0)) {
                velocityVector.setX(-1 * velocityVector.getX());
                break;
            }
        }
        eBox.move(velocityVector);
    }
}
