package input.logic;

import components.Component;
import entity.Entity;
import input.InputComponent;
import world.World;

public class BrickLogic extends Component<Entity> {
    private Entity spawnOnDead;
    private boolean spawned = false;

    public BrickLogic() {
        this.spawnOnDead = null;
    }

    public BrickLogic(Entity spawnOnDead) {
        this.spawnOnDead = spawnOnDead;
    }

    public void attachEntity(Entity spawnOnDead) {
        this.spawnOnDead = spawnOnDead;
    }

    @Override
    public void onAttach(Entity e) {
    }

    @Override
    public void handle(Entity e) {
        if (!e.isDead() || spawned) return;
        World w = e.getWorld();
        e.setCollisionState(false);
        w.spawn(w.getCurrentRow(e), w.getCurrentCol(e), spawnOnDead);
        spawned = true;
    }
}
