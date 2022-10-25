package input;

import entity.Entity;
import world.World;

public class BalloomAI extends RandomMovementAI {
    @Override
    public void onAttach(Entity e) {
        super.onAttach(e);
    }

    @Override
    public void handle(Entity e, World w) {
        if (e.isDead()) return;
        for (Entity p:w.getNearbyPlayers(e)) {
            if (p.getHitBox().intersect(e.getHitBox(), 0, -1)) p.kill();
            if (p.getHitBox().intersect(e.getHitBox(), 0, 1)) p.kill();
            if (p.getHitBox().intersect(e.getHitBox(), -1, 0)) p.kill();
            if (p.getHitBox().intersect(e.getHitBox(), 1, 0)) p.kill();
        }
        super.handle(e, w);
    }
}
