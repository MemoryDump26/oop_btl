package components.attack;

import components.collision.CollisionComponent;
import components.Component;
import entity.Entity;
import geometry.Point;
import components.logic.BombLogic;
import resources.Resources;
import world.World;

public class BombAttack extends Component<Entity> {
    private int power;
    private int numOfBombs;

    public BombAttack(int power, int numOfBombs) {
        this.power = power;
        this.numOfBombs = numOfBombs;
    }

    public int getPower() {return power;}
    public void setPower(int power) {this.power = power;}
    public int getNumOfBombs() {return numOfBombs;}
    public void setNumOfBombs(int numOfBombs) {this.numOfBombs = numOfBombs;}

    @Override
    public void onAttach(Entity e) {}

    @Override
    public void handle(Entity e) {
        if (numOfBombs > 0) {
            World w = e.getWorld();
            Point p = w.getBoardPosition(e);
            int row = (int) p.getY();
            int col = (int) p.getX();
            Entity b = new Entity(
                Point.ZERO,
                new BombLogic(power, this),
                CollisionComponent.Bomb,
                Component.getNull(),
                w,
                Resources.spriteDataMap.get("bomb"),
                e.getSprite().getGc()
            );
            b.getSprite().setCurrentAnimation("bomb");
            w.spawn(row, col, b);
            numOfBombs--;
            Resources.soundDataMap.get("place_bomb").play();
        }
    }
}
