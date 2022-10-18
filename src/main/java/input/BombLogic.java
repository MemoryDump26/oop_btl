package input;

import entity.Entity;
import javafx.scene.input.KeyCode;
import timer.Timer;
import world.World;

public class BombLogic extends InputComponent {
    private int power;
    private Timer t;
    boolean exploded = false;

    public BombLogic(int power) {
        this.power = power;
        this.t = new Timer(3, true);
        t.start();
    }

    @Override
    public void handle(Entity e, World w) {
        if (t.getElapsedTimeInSecond() >= 2) {
            t.stop();
            e.kill();
        }
        if (!exploded && e.isDead()) {
            w.spawnFlame(w.getCurrentRow(e), w.getCurrentCol(e), power, 0, 0);
        }
    }
}
