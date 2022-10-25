package input;

import entity.Entity;
import geometry.Rectangle;
import world.World;

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
    public void handle(Entity e, World w) {
        if (e.isDead()) return;
        Rectangle eBox = e.getHitBox();
        for (Entity p:w.getNearbyEntities(e, false, false, false, true)) {
            Rectangle pBox = p.getHitBox();
            if (pBox.intersect(eBox, 0, -1)) p.kill();
            if (pBox.intersect(eBox, 0, 1)) p.kill();
            if (pBox.intersect(eBox, -1, 0)) p.kill();
            if (pBox.intersect(eBox, 1, 0)) p.kill();
        }
        super.handle(e, w);
    }
}
