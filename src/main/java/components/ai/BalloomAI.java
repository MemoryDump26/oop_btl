package components.ai;

import entity.Entity;
import geometry.Rectangle;
import world.World;

public class BalloomAI extends RandomMovementAI {
    public BalloomAI(boolean incStatic, boolean incObjects, boolean incEnemies, boolean incPlayers) {
        super(incStatic, incObjects, incEnemies, incPlayers);
    }

    public BalloomAI() {
        super(true, true, true, false);
    }

    @Override
    public void onAttach(Entity e) {
        super.onAttach(e);
    }

    @Override
    public void handle(Entity e) {
        if (e.isDead()) return;
        super.handle(e);
    }
}
