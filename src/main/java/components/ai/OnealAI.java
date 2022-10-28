package components.ai;

import entity.Entity;
import geometry.Point;
import geometry.Rectangle;
import components.commands.EntityCommands;
import world.World;

import java.util.ArrayList;

public class OnealAI extends RandomMovementAI {
    public OnealAI(boolean incStatic, boolean incObjects, boolean incEnemies, boolean incPlayers) {
        super(incStatic, incObjects, incEnemies, incPlayers);
    }

    public OnealAI() {
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
        Point pE = w.getBoardPosition(e);
        int eRow = (int)pE.getY();
        int eCol = (int)pE.getX();

        ArrayList<Entity> players = w.getAllEntities(false, false, false, true);
        boolean foundPlayer = false;
        World.Direction playerDirection = World.Direction.UP;
        int playerDistance = Integer.MAX_VALUE;

        for (Entity p:players) {
            if (p.isDead()) continue;

            Point pP = w.getBoardPosition(p);
            int pRow = (int)pP.getY();
            int pCol = (int)pP.getX();

            if (eRow != pRow && eCol != pCol) continue;

            int rowDistance = eRow - pRow;
            int colDistance = eCol - pCol;
            int newDistance = Math.abs(rowDistance + colDistance);
            if (newDistance >= playerDistance) continue;

            foundPlayer = true;
            playerDistance = newDistance;
            ArrayList<Entity> everything = w.getAllEntities(true, true, true, true);

            if (colDistance == 0) {
                int lineStart = Math.min(eRow, pRow) + 1;
                int lineEnd = Math.max(eRow, pRow) - 1;
                for (int i = lineStart; i <= lineEnd; i++) {
                    if (w.isOccupied(i, eCol, everything)) {
                        foundPlayer = false;
                        break;
                    }
                }
            }
            else {
                int lineStart = Math.min(eCol, pCol) + 1;
                int lineEnd = Math.max(eCol, pCol) - 1;
                for (int i = lineStart; i <= lineEnd; i++) {
                    if (w.isOccupied(eRow, i, everything)) {
                        foundPlayer = false;
                        break;
                    }
                }
            }

            if (!foundPlayer) continue;

            if (rowDistance > 0) playerDirection = World.Direction.UP;
            if (rowDistance < 0) playerDirection = World.Direction.DOWN;
            if (colDistance > 0) playerDirection = World.Direction.LEFT;
            if (colDistance < 0) playerDirection = World.Direction.RIGHT;
        }

        if (foundPlayer) {
            e.setSpeed(2);
            super.currentDirection = playerDirection;
            switch (playerDirection) {
                case UP -> EntityCommands.Up.execute(e);
                case DOWN -> EntityCommands.Down.execute(e);
                case LEFT -> EntityCommands.Left.execute(e);
                case RIGHT -> EntityCommands.Right.execute(e);
            }
        }
        else {
            e.setSpeed(1);
            super.handle(e);
        }
    }
}
