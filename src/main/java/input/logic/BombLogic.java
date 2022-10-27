package input.logic;

import attack.BombAttack;
import components.Component;
import entity.Entity;
import input.InputComponent;
import resources.Resources;
import timer.Timer;
import world.World;

public class BombLogic extends Component<Entity> {
    private int power;
    private BombAttack parent;
    private Timer t;
    private boolean exploded = false;

    public BombLogic(int power, BombAttack parent) {
        this.power = power;
        this.parent = parent;
        this.t = new Timer(2);
        t.start();
    }

    @Override
    public void onAttach(Entity e) {
    }

    @Override
    public void handle(Entity e) {
        if (t.isFinished()) {
            t.stop();
            e.kill();
        }
        if (!exploded && e.isDead()) {
            World w = e.getWorld();
            e.setCollisionState(false);
            parent.setNumOfBombs(parent.getNumOfBombs() + 1);
            w.spawnFlame(w.getCurrentRow(e), w.getCurrentCol(e), power, 0, 0);
            exploded = true;
            Resources.soundDataMap.get("explosion").play();
        }
    }
}
