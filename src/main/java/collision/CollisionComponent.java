package collision;

import entity.Entity;

import java.util.ArrayList;

public abstract class CollisionComponent {
    public abstract void handle(Entity e, ArrayList<Entity> world);

    public static CollisionComponent Null = new CollisionComponent() {
        @Override
        public void handle(Entity e, ArrayList<Entity> world) {
            return;
        }
    };

    public static CollisionComponent Dynamic = new CollisionComponent() {
        @Override
        public void handle(Entity e, ArrayList<Entity> world) {
            e.getHitBox().move(e.getVelocity().getX(), 0);
            for (Entity m:world) {
                if (!m.getCollisionState()) continue;
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
                if (!m.getCollisionState()) continue;
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
    };

    public static CollisionComponent Static = new CollisionComponent() {
        @Override
        public void handle(Entity e, ArrayList<Entity> world) {
            for (Entity m:world) {
                if (!m.getCollisionState()) continue;
                e.touched(m);
            }
        }
    };
}
