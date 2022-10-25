package collision;

import attack.BombAttack;
import entity.Entity;
import input.PlayerInputComponent;
import options.Globals;
import world.World;

public abstract class CollisionComponent {
    public abstract void onAttach(Entity e);
    public abstract void handle(Entity e, World w);

    public static CollisionComponent Null = new CollisionComponent() {
        @Override
        public void onAttach(Entity e) {
            e.setCollisionState(false);
            e.setDestructible(false);
        }

        @Override
        public void handle(Entity e, World w) {
            return;
        }
    };

    public static CollisionComponent Dynamic = new CollisionComponent() {
        @Override
        public void onAttach(Entity e) {
            e.setCollisionState(true);
            e.setDestructible(true);
        }

        @Override
        public void handle(Entity e, World w) {
            if (e.isDead()) return;
            e.getHitBox().move(e.getVelocity().getX(), 0);
            for (Entity m:w.getNearbyEntities(e)) {
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
            int col = w.getCurrentCol(e);
            int gridX = col * (int)Globals.cellSize;
            double eX = e.getHitBox().getX();
            if (eX - gridX < e.getSpeed()) e.moveTo(gridX, e.getHitBox().getY());
            else if (gridX + Globals.cellSize - eX < e.getSpeed()) e.moveTo(gridX + Globals.cellSize, e.getHitBox().getY());

            e.getHitBox().move(0, e.getVelocity().getY());
            for (Entity m:w.getNearbyEntities(e)) {
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
            int row = w.getCurrentRow(e);
            int gridY = row * (int)Globals.cellSize;
            double eY = e.getHitBox().getY();
            if (eY - gridY < e.getSpeed()) e.moveTo(e.getHitBox().getX(), gridY);
            else if (gridY + Globals.cellSize - eY < e.getSpeed()) e.moveTo(e.getHitBox().getX(), gridY + Globals.cellSize);
        }
    };

    public static CollisionComponent Static = new CollisionComponent() {
        @Override
        public void onAttach(Entity e) {
            e.setCollisionState(true);
            e.setDestructible(false);
        }

        @Override
        public void handle(Entity e, World w) {
            return;
        }
    };

    public static CollisionComponent Destructibles = new CollisionComponent() {
        @Override
        public void onAttach(Entity e) {
            e.setCollisionState(true);
            e.setDestructible(true);
        }

        @Override
        public void handle(Entity e, World w) {
            return;
        }
    };

    public static CollisionComponent Bomb = new CollisionComponent() {
        @Override
        public void onAttach(Entity e) {
            e.setCollisionState(false);
            e.setDestructible(true);
        }

        @Override
        public void handle(Entity e, World w) {
            if (e.getCollisionState() == false) {
                boolean notColliding = true;
                for (Entity m:w.getNearbyEntities(e)) {
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
    };

    public static CollisionComponent Flame = new CollisionComponent() {
        @Override
        public void onAttach(Entity e) {
            e.setCollisionState(false);
            e.setDestructible(true);
        }

        @Override
        public void handle(Entity e, World w) {
            for (Entity m:w.getNearbyEntities(e))  {
                if (!m.getCollisionState()) continue;
                if (e.getHitBox().intersect(m.getHitBox())) {
                    m.kill();
                    break;
                }
            }
        }
    };

    public static CollisionComponent FlamePower = new CollisionComponent() {
        @Override
        public void onAttach(Entity e) {
            e.setCollisionState(false);
            e.setDestructible(true);
        }

        @Override
        public void handle(Entity e, World w) {
            if (e.isDead()) return;
            for (Entity m:w.getNearbyPlayers(e)) {
                if (!m.getCollisionState()) continue;
                if (e.getHitBox().intersect(m.getHitBox())) {
                    if (m.getAttack() instanceof BombAttack a) {
                        a.setPower(a.getPower() + 1);
                        e.kill();
                        break;
                    }
                }
            }
        }
    };

    public static CollisionComponent BombPower = new CollisionComponent() {
        @Override
        public void onAttach(Entity e) {
            e.setCollisionState(false);
            e.setDestructible(true);
        }

        @Override
        public void handle(Entity e, World w) {
            if (e.isDead()) return;
            for (Entity m:w.getNearbyPlayers(e)) {
                if (!m.getCollisionState()) continue;
                if (e.getHitBox().intersect(m.getHitBox())) {
                    if (m.getAttack() instanceof BombAttack a) {
                        a.setNumOfBombs(a.getNumOfBombs() + 1);
                        e.kill();
                        break;
                    }
                }
            }
        }
    };

    public static CollisionComponent SpeedPower = new CollisionComponent() {
        @Override
        public void onAttach(Entity e) {
            e.setCollisionState(false);
            e.setDestructible(true);
        }

        @Override
        public void handle(Entity e, World w) {
            if (e.isDead()) return;
            for (Entity m:w.getNearbyPlayers(e)) {
                if (!m.getCollisionState()) continue;
                if (e.getHitBox().intersect(m.getHitBox())) {
                    m.setSpeed(m.getSpeed() + 1);
                    e.kill();
                    break;
                }
            }
        }
    };

    public static CollisionComponent Portal = new CollisionComponent() {
        @Override
        public void onAttach(Entity e) {
            e.setCollisionState(false);
            e.setDestructible(true);
        }

        @Override
        public void handle(Entity e, World w) {
            if (e.isDead()) return;
            for (Entity m:w.getNearbyPlayers(e)) {
                if (!m.getCollisionState()) continue;
                if (e.getHitBox().intersect(m.getHitBox())) {
                    if (w.isLevelCleared()) {
                        w.setCleared(true);
                        break;
                    }
                }
            }
        }
    };
}
