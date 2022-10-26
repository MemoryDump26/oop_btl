package collision;

import entity.Entity;
import input.Command;
import resources.SoundFX;
import world.World;

public class PowerCollisionComponent extends CollisionComponent {
    private Command<Entity> c;

    public PowerCollisionComponent(Command<Entity> c) {
        this.c = c;
    }

    @Override
    public void onAttach(Entity e) {
        e.setCollisionState(false);
        e.setDestructible(true);
    }

    @Override
    public void handle(Entity e, World w) {
        if (e.isDead()) return;
        for (Entity m:w.getNearbyEntities(e, false, false, false, true)) {
            if (!m.getCollisionState()) continue;
            if (e.getHitBox().intersect(m.getHitBox())) {
                SoundFX.playSound("powerup", 1, true);
                c.execute(m);
                e.kill();
                break;
            }
        }
    }
}
