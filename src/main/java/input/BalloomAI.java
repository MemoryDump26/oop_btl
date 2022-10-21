package input;

import entity.Entity;
import world.World;

import java.util.Random;

public class BalloomAI extends InputComponent {

    @Override
    public void onAttach(Entity e) {
    }

    @Override
    public void handle(Entity e, World w) {
        Random r = new Random();
        e.availMove.clear();
        e.availMove.add(World.Direction.UP);
        e.availMove.add(World.Direction.DOWN);
        e.availMove.add(World.Direction.LEFT);
        e.availMove.add(World.Direction.RIGHT);
        for (Entity nearby:w.getNearbyEntities(e)) {
            if (!nearby.getCollisionState()) continue;
            if (nearby.getHitBox().intersect(e.getHitBox(), 0, -1)) e.availMove.remove(World.Direction.UP);
            if (nearby.getHitBox().intersect(e.getHitBox(), 0, 1)) e.availMove.remove(World.Direction.DOWN);
            if (nearby.getHitBox().intersect(e.getHitBox(), -1, 0)) e.availMove.remove(World.Direction.LEFT);
            if (nearby.getHitBox().intersect(e.getHitBox(), 1, 0)) e.availMove.remove(World.Direction.RIGHT);
        }
        if (!e.availMove.contains(e.currentDirection) && e.availMove.size() > 0) {
            e.currentDirection = e.availMove.get(r.nextInt(e.availMove.size()));
        }
        if (r.nextInt(100) < 25 && e.availMove.size() > 2) {
            e.currentDirection = e.availMove.get(r.nextInt(e.availMove.size()));
        }

        switch (e.currentDirection) {
            case UP -> Command.Up.execute(e);
            case DOWN -> Command.Down.execute(e);
            case LEFT -> Command.Left.execute(e);
            case RIGHT -> Command.Right.execute(e);
        }
    }
}
