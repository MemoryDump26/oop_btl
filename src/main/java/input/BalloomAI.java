package input;

import entity.Entity;
import world.World;

import java.util.ArrayList;
import java.util.Random;

public class BalloomAI extends InputComponent {
    private ArrayList<World.Direction> availMove = new ArrayList<>();
    private World.Direction currentDirection = World.Direction.UP;
    private Random r = new Random();

    @Override
    public void onAttach(Entity e) {
    }

    @Override
    public void handle(Entity e, World w) {
        availMove.clear();
        availMove.add(World.Direction.UP);
        availMove.add(World.Direction.DOWN);
        availMove.add(World.Direction.LEFT);
        availMove.add(World.Direction.RIGHT);
        for (Entity p:w.getNearbyPlayers(e)) {
            if (p.getHitBox().intersect(e.getHitBox(), 0, -1)) p.kill();
            if (p.getHitBox().intersect(e.getHitBox(), 0, 1)) p.kill();
            if (p.getHitBox().intersect(e.getHitBox(), -1, 0)) p.kill();
            if (p.getHitBox().intersect(e.getHitBox(), 1, 0)) p.kill();
        }
        for (Entity nearby:w.getNearbyEntities(e)) {
            if (!nearby.getCollisionState()) continue;
            if (nearby.getHitBox().intersect(e.getHitBox(), 0, -1)) availMove.remove(World.Direction.UP);
            if (nearby.getHitBox().intersect(e.getHitBox(), 0, 1)) availMove.remove(World.Direction.DOWN);
            if (nearby.getHitBox().intersect(e.getHitBox(), -1, 0)) availMove.remove(World.Direction.LEFT);
            if (nearby.getHitBox().intersect(e.getHitBox(), 1, 0)) availMove.remove(World.Direction.RIGHT);
        }
        if (!availMove.contains(currentDirection) && availMove.size() > 0) {
            currentDirection = availMove.get(r.nextInt(availMove.size()));
        }
        if (r.nextInt(100) < 25 && availMove.size() > 2) {
            currentDirection = availMove.get(r.nextInt(availMove.size()));
        }

        switch (currentDirection) {
            case UP -> Command.Up.execute(e);
            case DOWN -> Command.Down.execute(e);
            case LEFT -> Command.Left.execute(e);
            case RIGHT -> Command.Right.execute(e);
        }
    }
}
