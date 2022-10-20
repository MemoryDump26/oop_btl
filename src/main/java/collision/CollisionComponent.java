package collision;

import attack.BombAttack;
import entity.Entity;

import java.util.ArrayList;

public abstract class CollisionComponent {
    public abstract void handle(Entity e, ArrayList<Entity> world);
    public abstract boolean getDefaultState();
    public abstract boolean isDestructibles();

    public static CollisionComponent Null = new CollisionComponent() {
        @Override
        public void handle(Entity e, ArrayList<Entity> world) {
            return;
        }

        @Override
        public boolean getDefaultState() {return false;}
        public boolean isDestructibles() {return false;}
    };

    public static CollisionComponent Dynamic = new CollisionComponent() {
        @Override
        public void handle(Entity e, ArrayList<Entity> world) {
            if (e.isDead()) return;
            e.getHitBox().move(e.getVelocity().getX(), 0);
            for (Entity m:world) {
                if (!m.getCollisionState()) continue;
                if (e.getHitBox().intersect(m.getHitBox())) {
                    if (m.isHarmful()) {
                        e.kill();
                        break;
                    }
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
                    if (m.isHarmful()) {
                        e.kill();
                        break;
                    }
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

        @Override
        public boolean getDefaultState() {return true;}
        public boolean isDestructibles() {return true;}
    };

    public static CollisionComponent Static = new CollisionComponent() {
        @Override
        public void handle(Entity e, ArrayList<Entity> world) {
            return;
        }

        @Override
        public boolean getDefaultState() {return true;}
        public boolean isDestructibles() {return false;}
    };

    public static CollisionComponent Destructibles = new CollisionComponent() {
        @Override
        public void handle(Entity e, ArrayList<Entity> world) {
            return;
        }

        @Override
        public boolean getDefaultState() {return true;}
        public boolean isDestructibles() {return true;}
    };

    public static CollisionComponent Bomb = new CollisionComponent() {
        @Override
        public void handle(Entity e, ArrayList<Entity> world) {
            if (e.getCollisionState() == false) {
                boolean notColliding = true;
                for (Entity m:world) {
                    if (!m.getCollisionState()) continue;
                    if (e.getHitBox().intersect(m.getHitBox())) {
                        notColliding = false;
                        break;
                    }
                }
                if (notColliding) {
                    e.setCollisionState(true);
                }
            }
        }

        @Override
        public boolean getDefaultState() {return false;}
        public boolean isDestructibles() {return true;}
    };

    public static CollisionComponent Flame = new CollisionComponent() {
        @Override
        public void handle(Entity e, ArrayList<Entity> world) {
            for (Entity m:world) {
                if (!m.getCollisionState()) continue;
                if (e.getHitBox().intersect(m.getHitBox())) {
                    m.kill();
                    break;
                }
            }
        }

        @Override
        public boolean getDefaultState() {return false;}
        public boolean isDestructibles() {return true;}
    };

    public static CollisionComponent FlamePower = new CollisionComponent() {
        @Override
        public void handle(Entity e, ArrayList<Entity> world) {
            for (Entity m:world) {
                if (!m.getCollisionState()) continue;
                if (e.isDead()) return;
                if (e.getHitBox().intersect(m.getHitBox())) {
                    if (m.getAttack() instanceof BombAttack) {
                        BombAttack a = (BombAttack) m.getAttack();
                        a.setPower(a.getPower() + 1);
                        e.kill();
                        break;
                    }
                }
            }
        }

        @Override
        public boolean getDefaultState() {return false;}

        @Override
        public boolean isDestructibles() {return true;}
    };
}
