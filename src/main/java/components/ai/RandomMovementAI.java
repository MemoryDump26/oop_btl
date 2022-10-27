package components.ai;

import components.Component;
import entity.Entity;
import components.commands.Command;
import timer.Timer;
import world.World;

import java.util.ArrayList;
import java.util.Random;

public class RandomMovementAI extends Component<Entity> {
    protected ArrayList<World.Direction> availMove;
    protected World.Direction currentDirection = World.Direction.UP;
    protected Timer dirChangeCooldown = new Timer(3);
    protected Random r = new Random();
    protected boolean[] inc = new boolean[4];

    public RandomMovementAI(boolean incStatic, boolean incObjects, boolean incEnemies, boolean incPlayers) {
        inc[0] = incStatic;
        inc[1] = incObjects;
        inc[2] = incEnemies;
        inc[3] = incPlayers;
    }

    @Override
    public void onAttach(Entity e) {
        dirChangeCooldown.start();
    }

    @Override
    public void handle(Entity e) {
        if (e.isDead()) return;
        World w = e.getWorld();

        availMove = w.getAvailableMoves(e, w.getNearbyEntities(e, inc[0], inc[1], inc[2], inc[3]));
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