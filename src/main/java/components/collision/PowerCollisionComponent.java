package components.collision;

import components.Component;
import entity.Entity;
import components.commands.Command;
import resources.Resources;
import world.World;

public class PowerCollisionComponent extends Component<Entity> {
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
    public void handle(Entity e) {
        if (e.isDead()) return;
        World w = e.getWorld();
        for (Entity m:w.getNearbyEntities(e, false, false, false, true)) {
            if (!m.getCollisionState()) continue;
            if (e.getHitBox().intersect(m.getHitBox())) {
                c.execute(m);
                Resources.getSound("powerup").play();
                e.kill();
                break;
            }
        }
    }
}
