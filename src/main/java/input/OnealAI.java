package input;

import entity.Entity;
import geometry.Point;
import world.World;

public class OnealAI extends BalloomAI {
    /*private ArrayList<World.Direction> availMove = new ArrayList<>();
    private World.Direction currentDirection = World.Direction.UP;
    private Random r = new Random();*/
    private boolean foundPlayer = false;
    private int playerRow;
    private int playerCol;

    @Override
    public void onAttach(Entity e) {
    }

    @Override
    public void handle(Entity e, World w) {
        foundPlayer = false;
        int eRow = w.getCurrentRow(e);
        int eCol = w.getCurrentCol(e);
        playerRow = Integer.MIN_VALUE;
        playerCol = Integer.MIN_VALUE;
        for (Entity p:w.getNearbyPlayers(e)) {
            Point player = w.getBoardPosition(p);
            int pRow = (int)player.getY();
            int pCol = (int)player.getX();
            if (eRow == pRow) {

            }
        }
        if (!foundPlayer) super.handle(e, w);
    }
}
