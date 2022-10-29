package components.attack;

import components.ai.KondoriaAI;
import components.collision.CollisionComponent;
import components.Component;
import entity.Entity;
import geometry.Circle;
import geometry.Point;
import components.logic.BombLogic;
import resources.Resources;
import sprite.Sprite;
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

            Circle fearRadius = new Circle(100);
            fearRadius.setCenter(e.getHitBox().getCenter());
            for (Entity enemies : w.getAllEntities(false, false, true, false)) {
                if (fearRadius.contains(enemies.getHitBox().getCenter())) {
                    if (enemies.getInput() instanceof KondoriaAI k) {
                        k.setFeared(true);
                    }
                }
            }

            Entity b = new Entity(Point.ZERO, w, new Sprite(Resources.getSprite("bomb"), e.getSprite().getGc()));
            b.setInput(new BombLogic(power, this));
            b.setCollision(CollisionComponent.Bomb);
            b.getSprite().setCurrentAnimation("bomb");

            w.spawn(row, col, b);
            numOfBombs--;
            Resources.getSound("place_bomb").play();
        }
    }
}
