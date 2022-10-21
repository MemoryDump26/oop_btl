package input;

import attack.BombAttack;
import entity.Entity;
import timer.Timer;
import world.World;

public class BombLogic extends InputComponent {
    private int power;
    private BombAttack parent;
    private Timer t;
    private boolean exploded = false;

    public BombLogic(int power, BombAttack parent) {
        this.power = power;
        this.parent = parent;
        this.t = new Timer(3, true);
        t.start();
    }

    @Override
    public void onAttach(Entity e) {
    }

    @Override
    public void handle(Entity e, World w) {
        if (t.getElapsedTimeInSecond() >= 2) {
            t.stop();
            e.kill();
        }
        if (!exploded && e.isDead()) {
            parent.setNumOfBombs(parent.getNumOfBombs() + 1);
            w.spawnFlame(w.getCurrentRow(e), w.getCurrentCol(e), power, 0, 0);
            exploded = true;
        }
    }
}
