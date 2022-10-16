package attack;

import entity.Entity;
import world.World;

public class BombAttack extends AttackComponent {
    private int power;

    public BombAttack(int power) {
        this.power = power;
    }

    public int getPower() {return power;}
    public void setPower(int power) {this.power = power;}

    @Override
    public void handle(Entity e, World w) {
        w.spawnBomb(w.getCurrentRow(e), w.getCurrentCol(e), power);
    }
}
