package input;

import entity.DynamicEntity;
import entity.Entity;

import java.util.ArrayList;

public class PlayerCollisionComponent extends CollisionComponent {
    @Override
    public void handle(DynamicEntity e, ArrayList<Entity> wall) {
        e.hitBox.move(e.velocity.getX(), 0);
        for (Entity w:wall) {
            if (e.hitBox.intersect(w.hitBox)) {
                double xOffset = 0;
                if (e.hitBox.getX() <= w.hitBox.getX()) xOffset = w.hitBox.getX() - (e.hitBox.getX() + e.hitBox.getW());
                else xOffset = (w.hitBox.getX() + w.hitBox.getW()) - e.hitBox.getX();
                e.hitBox.move(xOffset, 0);
                break;
            }
        }
        e.hitBox.move(0, e.velocity.getY());
        for (Entity w:wall) {
            if (e.hitBox.intersect(w.hitBox)) {
                double yOffset = 0;
                if (e.hitBox.getY() <= w.hitBox.getY()) yOffset = w.hitBox.getY() - (e.hitBox.getY() + e.hitBox.getH());
                else yOffset = (w.hitBox.getY() + w.hitBox.getH()) - e.hitBox.getY();
                e.hitBox.move(0, yOffset);
                break;
            }
        }
    }
}
