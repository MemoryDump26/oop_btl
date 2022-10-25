package attack;

import collision.CollisionComponent;
import entity.Entity;
import geometry.Point;
import input.BombLogic;
import resources.Resources;
import world.World;

public class BombAttack extends AttackComponent {
    private int power;
    private int numOfBombs;

    public BombAttack(int power, int numOfBombs) {
        this.power = power;
        this.numOfBombs = 1;
    }

    public int getPower() {return power;}
    public void setPower(int power) {this.power = power;}
    public int getNumOfBombs() {return numOfBombs;}
    public void setNumOfBombs(int numOfBombs) {this.numOfBombs = numOfBombs;}

    @Override
    public void handle(Entity e, World w) {
        if (numOfBombs > 0) {
            Point p = w.getBoardPosition(e);
            int row = (int) p.getY();
            int col = (int) p.getX();
            Entity b = new Entity(
                new Point(0, 0),
                new BombLogic(power, this),
                CollisionComponent.Bomb,
                AttackComponent.Null,
                w,
                Resources.spriteDataMap.get("bomb"),
                e.getSprite().getGc()
            );
            b.getSprite().setCurrentAnimation("bomb");
            w.spawn(row, col, b);
            numOfBombs--;
        }
    }
}
