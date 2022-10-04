package collision;

import entity.Entity;

import java.util.ArrayList;

public class DynamicCollisionComponent extends CollisionComponent {
    @Override
    public void handle(Entity e, ArrayList<Entity> world) {
        e.getHitBox().move(e.getVelocity().getX(), 0);
        for (Entity m:world) {
            if (e.getHitBox().intersect(m.getHitBox())) {
                double eX = e.getHitBox().getX();
                double eW = e.getHitBox().getW();
                double mX = m.getHitBox().getX();
                double mW = m.getHitBox().getW();
                double xOffset = 0;
                if (eX <= mX) xOffset = mX - (eX + eW);
                else xOffset = (mX + mW) - eX;
                e.getHitBox().move(xOffset, 0);
                break;
            }
        }
        e.getHitBox().move(0, e.getVelocity().getY());
        for (Entity m:world) {
            if (e.getHitBox().intersect(m.getHitBox())) {
                double eY = e.getHitBox().getY();
                double eH = e.getHitBox().getH();
                double mY = m.getHitBox().getY();
                double mH = m.getHitBox().getH();
                double yOffset = 0;
                if (eY <= mY) yOffset = mY - (eY + eH);
                else yOffset = (mY + mH) - eY;
                e.getHitBox().move(0, yOffset);
                break;
            }
        }
    }
}
