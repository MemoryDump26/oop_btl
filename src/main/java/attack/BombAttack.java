package attack;

import entity.Entity;
import geometry.Point;
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
        Point p = w.getBoardPosition(e);
        int row = (int)p.getY();
        int col = (int)p.getX();
        w.spawnBomb(row, col, power);
    }
}
