package components.ai;

import entity.Entity;
import geometry.Rectangle;
import world.World;

public class KondoriaAI extends GhostMovementAI {

    @Override
    public void handle(Entity e) {
        if (e.isDead()) return;
        super.handle(e);
    }
}
