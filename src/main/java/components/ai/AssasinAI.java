package components.ai;

import components.astar.Node;
import components.astar.PathFinder;
import components.commands.EntityCommands;
import entity.Entity;
import geometry.Point;
import world.World;

public class AssasinAI extends RandomMovementAI {
    private PathFinder pf;

    public AssasinAI(boolean incStatic, boolean incObjects, boolean incEnemies, boolean incPlayers) {
        super(incStatic, incObjects, incEnemies, incPlayers);
    }

    public AssasinAI() {
        super(true, true, true, false);
    }

    @Override
    public void onAttach(Entity e) {
        pf = new PathFinder(e.getWorld());
        super.onAttach(e);
    }

    @Override
    public void handle(Entity e) {
        if (e.isDead()) return;
        World w = e.getWorld();

        Point ePos = e.getHitBox().getCenter();
        Entity nearestPlayer = null;
        double minDistance = Double.MAX_VALUE;

        for (Entity p : w.getAllEntities(false, false, false, true)) {
            Point pPoint = p.getHitBox().getCenter();
            double distance = ePos.getDistance(pPoint);
            if (distance < minDistance) {
                nearestPlayer = p;
                minDistance = distance;
            }
        }

        if (minDistance > 200) e.getSprite().setVisible(false);
        else e.getSprite().setVisible(true);

        if (nearestPlayer != null) {
            ePos = w.getBoardPosition(e);
            Point pPos = w.getBoardPosition(nearestPlayer);
            Node pathEnd = pf.getPath((int)ePos.getY(), (int)ePos.getX(), (int)pPos.getY(), (int)pPos.getX());
            w.pathToDraw.add(pathEnd);
            World.Direction target = pf.getDirection(pathEnd);
            switch (target) {
                case UP -> EntityCommands.Up.execute(e);
                case DOWN -> EntityCommands.Down.execute(e);
                case LEFT -> EntityCommands.Left.execute(e);
                case RIGHT -> EntityCommands.Right.execute(e);
                case NA -> super.handle(e);
            }
        }
    }
}
