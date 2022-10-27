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
        World w = e.getWorld();
        Rectangle eBox = e.getHitBox();
        for (Entity p:w.getNearbyEntities(e, false, false, false, true)) {
            Rectangle pBox = p.getHitBox();
            if (pBox.intersect(eBox, 0, -1)) p.kill();
            if (pBox.intersect(eBox, 0, 1)) p.kill();
            if (pBox.intersect(eBox, -1, 0)) p.kill();
            if (pBox.intersect(eBox, 1, 0)) p.kill();
        }
        super.handle(e);
    }
}
