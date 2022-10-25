package input;

import entity.Entity;
import timer.Timer;
import world.World;

import java.util.ArrayList;
import java.util.Random;

public class RandomMovementAI extends InputComponent {
    protected ArrayList<World.Direction> availMove;
    protected World.Direction currentDirection = World.Direction.UP;
    protected Timer dirChangeCooldown = new Timer(3);
    protected Random r = new Random();

    @Override
    public void onAttach(Entity e) {
        dirChangeCooldown.start();
    }

    @Override
    public void handle(Entity e, World w) {
        if (e.isDead()) return;

        availMove = w.getAvailableMoves(e);
        if (!availMove.contains(currentDirection) && availMove.size() > 0) {
            currentDirection = availMove.get(r.nextInt(availMove.size()));
        }
        availMove.remove(currentDirection);
        if (dirChangeCooldown.isFinished() && r.nextInt(100) < 1 && availMove.size() > 1) {
            currentDirection = availMove.get(r.nextInt(availMove.size()));
            dirChangeCooldown.start();
        }

        switch (currentDirection) {
            case UP -> Command.Up.execute(e);
            case DOWN -> Command.Down.execute(e);
            case LEFT -> Command.Left.execute(e);
            case RIGHT -> Command.Right.execute(e);
        }
    }
}