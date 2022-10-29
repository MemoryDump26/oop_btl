package components.collision;

import components.Component;
import entity.Entity;
import geometry.Point;
import geometry.Rectangle;
import options.Globals;
import world.World;

import java.util.ArrayList;

public abstract class CollisionComponent {
    public static Component<Entity> Dynamic = new Component<Entity>() {
        @Override
        public void onAttach(Entity e) {
            e.setCollisionState(true);
            e.setDestructible(true);
        }

        @Override
        public void handle(Entity e) {
            if (e.isDead()) return;
            World w = e.getWorld();

            ArrayList<Entity> collidingWith = new ArrayList<>();
            boolean[] collidingAt = new boolean[4];
            Point offset = new Point(0, 0);

            Rectangle eBox = e.getHitBox();
            Point eVec = e.getVelocity();

            // Solving X axis collision
            double diffX = Math.round(eBox.getX() % Globals.cellSize);
            double gridX = Math.floor(eBox.getX() / Globals.cellSize);
            if (diffX > 0 && diffX < e.getSpeed()) {
                //eBox.move(-diffX, 0);
                eBox.setX(gridX * Globals.cellSize);
            }
            else if (Globals.cellSize - diffX > 0 && Globals.cellSize - diffX < e.getSpeed()) {
                //eBox.move(Globals.cellSize - diffX, 0);
                eBox.setX((gridX + 1) * Globals.cellSize);
            }
            else {
                eBox.move(eVec.getX(), 0);
                Point[] eCorners = new Point[]{
                        eBox.getTopLeft(),
                        eBox.getTopRight(),
                        eBox.getBotLeft(),
                        eBox.getBotRight(),
                };

                // Get colliding entities
                for (Entity nearby : w.getNearbyEntities(e)) {
                    if (!nearby.getCollisionState()) continue;
                    for (int i = 0; i < 4; i++) {
                        if (nearby.getHitBox().contains(eCorners[i])) {
                            collidingWith.add(nearby);
                            collidingAt[i] = true;
                        }
                    }
                    if (collidingWith.size() > 1) break;
                }

                // Calculating main offset
                if (collidingWith.size() > 0) {
                    Rectangle collBox = collidingWith.get(0).getHitBox();
                    double eX = e.getHitBox().getX();
                    double eW = e.getHitBox().getW();
                    double mX = collBox.getX();
                    double mW = collBox.getW();
                    double xOffset = 0;
                    if (eX < mX) xOffset = mX - (eX + eW);
                    else if (eX > mX) xOffset = (mX + mW) - eX;
                    offset.setX(xOffset);
                }

                // Calculating side offset
                if (collidingWith.size() == 1) {
                    double yOffset = 0;
                    if (eVec.getY() != 0) yOffset = 0;
                    else if (collidingAt[0] || collidingAt[1]) {
                        yOffset = e.getSpeed();
                    }
                    else {
                        yOffset = -e.getSpeed();
                    }
                    offset.setY(yOffset);
                }

                // Final step
                eBox.move(offset);
            }

            // reset
            collidingWith.clear();
            collidingAt = new boolean[4];
            offset.zero();

            // Solving Y axis collision
            double diffY = Math.round(eBox.getY() % Globals.cellSize);
            double gridY = Math.floor(eBox.getY() / Globals.cellSize);
            if (diffY > 0 && diffY < e.getSpeed()) {
                //eBox.move(0, -diffY);
                eBox.setY(gridY * Globals.cellSize);
            }
            else if (Globals.cellSize - diffY > 0 && Globals.cellSize - diffY < e.getSpeed()) {
                //eBox.move(0, Globals.cellSize - diffY);
                eBox.setY((gridY + 1) *Globals.cellSize);
            }
            else {
                eBox.move(0, eVec.getY());
                Point[] eCorners = new Point[]{
                        eBox.getTopLeft(),
                        eBox.getTopRight(),
                        eBox.getBotLeft(),
                        eBox.getBotRight(),
                };

                // Get colliding entities
                for (Entity nearby : w.getNearbyEntities(e)) {
                    if (!nearby.getCollisionState()) continue;
                    for (int i = 0; i < 4; i++) {
                        if (nearby.getHitBox().contains(eCorners[i])) {
                            collidingWith.add(nearby);
                            collidingAt[i] = true;
                        }
                    }
                    if (collidingWith.size() > 1) break;
                }

                // Calculating main offset
                if (collidingWith.size() > 0) {
                    Rectangle collBox = collidingWith.get(0).getHitBox();
                    double eY = e.getHitBox().getY();
                    double eH = e.getHitBox().getH();
                    double mY = collBox.getY();
                    double mH = collBox.getH();
                    double yOffset = 0;
                    if (eY < mY) yOffset = mY - (eY + eH);
                    else if (eY > mY) yOffset = (mY + mH) - eY;
                    offset.setY(yOffset);
                }

                // Calculating side offset
                if (collidingWith.size() == 1) {
                    double xOffset = 0;
                    if (eVec.getX() != 0) xOffset = 0;
                    else if (collidingAt[0] || collidingAt[2]) {
                        xOffset = e.getSpeed();
                    }
                    else {
                        xOffset = -e.getSpeed();
                    }
                    offset.setX(xOffset);
                }

                // Final step
                eBox.move(offset);
            }
        }
    };

    public static Component<Entity> Static = new Component<Entity>() {
        @Override
        public void onAttach(Entity e) {
            e.setCollisionState(true);
            e.setDestructible(false);
        }

        @Override
        public void handle(Entity e) {
            return;
        }
    };

    public static Component<Entity> Destructibles = new Component<Entity>() {
        @Override
        public void onAttach(Entity e) {
            e.setCollisionState(true);
            e.setDestructible(true);
        }

        @Override
        public void handle(Entity e) {
            return;
        }
    };

    public static Component<Entity> Bomb = new Component<Entity>() {
        @Override
        public void onAttach(Entity e) {
            e.setCollisionState(false);
            e.setDestructible(true);
        }

        @Override
        public void handle(Entity e) {
            if (e.isDead()) return;
            World w = e.getWorld();
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

    public static Component<Entity> Flame = new Component<Entity>() {
        @Override
        public void onAttach(Entity e) {
            e.setCollisionState(false);
            e.setDestructible(true);
        }

        @Override
        public void handle(Entity e) {
            if (e.isDead()) return;
            World w = e.getWorld();
            for (Entity m:w.getNearbyEntities(e))  {
                //if (!m.getCollisionState()) continue;
                if (e.getHitBox().intersect(m.getHitBox())) {
                    m.kill();
                }
            }
            e.kill();
        }
    };

    public static Component<Entity> Portal = new Component<Entity>() {
        @Override
        public void onAttach(Entity e) {
            e.setCollisionState(false);
            e.setDestructible(true);
        }

        @Override
        public void handle(Entity e) {
            if (e.isDead()) return;
            World w = e.getWorld();
            for (Entity m:w.getNearbyEntities(e, false, false, false, true)) {
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