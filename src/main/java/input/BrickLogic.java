package input;

import entity.Entity;
import world.World;

public class BrickLogic extends InputComponent {
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
    public void handle(Entity e, World w) {
        if (!e.isDead() || spawned) return;
        e.setCollisionState(false);
        w.spawn(w.getCurrentRow(e), w.getCurrentCol(e), spawnOnDead);
        spawned = true;
    }
}
