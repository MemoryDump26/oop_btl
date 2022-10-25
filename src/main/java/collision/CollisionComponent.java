package collision;

import attack.BombAttack;
import entity.Entity;
import geometry.Point;
import geometry.Rectangle;
import input.PlayerInputComponent;
import options.Globals;
import world.World;

import java.util.ArrayList;

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
            ArrayList<Entity> collidingWith = new ArrayList<>();
            boolean[] collidingAt = new boolean[4];
            Point offset = new Point(0, 0);

            Rectangle eBox = e.getHitBox();
            Point eVec = e.getVelocity();

            // Solving X axis collision
            double diffX = eBox.getX() % Globals.cellSize;
            if (diffX > 0 && diffX < e.getSpeed()) {
                eBox.move(-diffX, 0);
            }
            else if (Globals.cellSize - diffX > 0 && Globals.cellSize - diffX < e.getSpeed()) {
                eBox.move(Globals.cellSize - diffX, 0);
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
            double diffY = eBox.getY() % Globals.cellSize;
            if (diffY > 0 && diffY < e.getSpeed()) {
                eBox.move(0, -diffY);
            }
            else if (Globals.cellSize - diffY > 0 && Globals.cellSize - diffY < e.getSpeed()) {
                eBox.move(0, Globals.cellSize - diffY);
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


    public static CollisionComponent Dynamic3 = new CollisionComponent() {
        @Override
        public void onAttach(Entity e) {
            e.setCollisionState(true);
            e.setDestructible(true);
        }

        @Override
        public void handle(Entity e, World w) {
            ArrayList<Entity> collidingWith = new ArrayList<>();
            boolean[] collidingAt = new boolean[4];
            Point offset = new Point(0, 0);

            Rectangle eBox = e.getHitBox();
            Point eVec = e.getVelocity();

            eBox.move(eVec.getX(), 0);
            Point[] eCorners = new Point[] {
                    eBox.getTopLeft(),
                    eBox.getTopRight(),
                    eBox.getBotLeft(),
                    eBox.getBotRight(),
            };

            for (Entity nearby:w.getNearbyEntities(e)) {
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
                if (eX <= mX) xOffset = mX - (eX + eW);
                else xOffset = (mX + mW) - eX;
                offset.setX(xOffset);
            }

            // Calculating side offset
            if (collidingWith.size() == 1) {
                double yOffset = 0;
                if (collidingAt[0] || collidingAt[1]) {
                    if (Math.abs(Globals.cellSize * (w.getCurrentRow(e) + 1) - eBox.getY()) < e.getSpeed()) {
                        yOffset = Math.abs(Globals.cellSize * (w.getCurrentRow(e) + 1) - eBox.getY());
                    }
                    else if (Math.abs(Globals.cellSize * (w.getCurrentRow(e) + 1) - eBox.getY()) < Globals.cellSize / 1.5 && eVec.getY() == 0) {
                        yOffset = e.getSpeed();
                    }
                }
                else if (collidingAt[2] || collidingAt[3]){
                    if (Math.abs(eBox.getY() - Globals.cellSize * (w.getCurrentRow(e))) < e.getSpeed()) {
                        yOffset = -Math.abs(eBox.getY() - Globals.cellSize * (w.getCurrentRow(e)));
                    }
                    else if (Math.abs(eBox.getY() - Globals.cellSize * (w.getCurrentRow(e))) < Globals.cellSize / 1.5 && eVec.getY() == 0) {
                        yOffset = -e.getSpeed();
                    }
                }
                offset.setY(yOffset);
            }

            // Final step
            eBox.move(offset);

            // reset
            collidingWith.clear();
            collidingAt = new boolean[4];
            offset.zero();

            eBox.move(0, eVec.getY());
            eCorners = new Point[] {
                    eBox.getTopLeft(),
                    eBox.getTopRight(),
                    eBox.getBotLeft(),
                    eBox.getBotRight(),
            };

            for (Entity nearby:w.getNearbyEntities(e)) {
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
                if (eY <= mY) yOffset = mY - (eY + eH);
                else yOffset = (mY + mH) - eY;
                offset.setY(yOffset);
            }

            // Calculating side offset
            if (collidingWith.size() == 1) {
                double xOffset = 0;
                if (collidingAt[0] || collidingAt[2]) {
                    if (Math.abs(Globals.cellSize * (w.getCurrentCol(e) + 1) - eBox.getX()) < e.getSpeed()) {
                        xOffset = Math.abs(Globals.cellSize * (w.getCurrentCol(e) + 1) - eBox.getX());
                    }
                    else if (Math.abs(Globals.cellSize * (w.getCurrentCol(e) + 1) - eBox.getX()) < Globals.cellSize / 1.5 && eVec.getX() == 0) {
                        xOffset = e.getSpeed();
                    }
                }
                else {
                    if (Math.abs(eBox.getX() - Globals.cellSize * (w.getCurrentCol(e))) < e.getSpeed()) {
                        xOffset = -Math.abs(eBox.getX() - Globals.cellSize * (w.getCurrentCol(e)));
                    }
                    else if (Math.abs(eBox.getX() - Globals.cellSize * (w.getCurrentCol(e))) < Globals.cellSize / 1.5 && eVec.getX() == 0) {
                        xOffset = -e.getSpeed();
                    }
                }
                offset.setX(xOffset);
            }

            // Final step
            eBox.move(offset);
        }
    };

    public static CollisionComponent Dynamic2 = new CollisionComponent() {
        @Override
        public void onAttach(Entity e) {
            e.setCollisionState(true);
            e.setDestructible(true);
        }

        @Override
        public void handle(Entity e, World w) {
            if (e.isDead()) return;
            int pointColliding = 0;
            boolean collTopLeft = false;
            boolean collTopRight = false;
            boolean collBotLeft = false;
            boolean collBotRight = false;
            Rectangle eBox = e.getHitBox();

            Point offset = new Point(0, 0);
            eBox.move(e.getVelocity().getX(), 0);
            for (Entity m:w.getNearbyEntities(e)) {
                if (!m.getCollisionState()) continue;

                Rectangle mBox = m.getHitBox();
                if (mBox.contains(eBox.getTopLeft())) {
                    double xOffset = mBox.getX() + mBox.getW() - eBox.getX();
                    offset.setX(xOffset);
                    collTopLeft = true;
                    pointColliding++;
                }
                if (mBox.contains(eBox.getBotLeft())) {
                    double xOffset = mBox.getX() + mBox.getW() - eBox.getX();
                    offset.setX(xOffset);
                    collBotLeft = true;
                    pointColliding++;
                }
                if (pointColliding > 1) break;

                if (mBox.contains(eBox.getTopRight())) {
                    double xOffset = mBox.getX() - (eBox.getX() + eBox.getW());
                    offset.setX(xOffset);
                    collTopRight = true;
                    pointColliding++;
                }
                if (mBox.contains(eBox.getBotRight())) {
                    double xOffset = mBox.getX() - (eBox.getX() + eBox.getW());
                    offset.setX(xOffset);
                    collBotRight = true;
                    pointColliding++;
                }
            }
            int row = w.getCurrentRow(e);
            int gridY = row * (int)Globals.cellSize;
            double eY = e.getHitBox().getY();
            /*if (eY - gridY < Globals.cellSize / 2) e.getHitBox().move(0, -Math.min(eY - gridY, e.getSpeed()));
            else if (gridY + Globals.cellSize - eY < Globals.cellSize / 2) e.getHitBox().move(0, Math.min(gridY + Globals.cellSize - eY, e.getSpeed()));*/

            if (pointColliding > 1) offset.setY(0);
            else if (collTopLeft || collTopRight) {
                if (gridY + Globals.cellSize - eY < Globals.cellSize / 2) e.getHitBox().move(0, Math.min(gridY + Globals.cellSize - eY, e.getSpeed()));
                double yOffset = Math.min(e.getSpeed(), Math.abs(Globals.cellSize * (w.getCurrentRow(e) + 1) - eBox.getY()));
                offset = offset.add(0, yOffset);
            }
            else if (collBotLeft || collBotRight){
                if (eY - gridY < Globals.cellSize / 2) e.getHitBox().move(0, -Math.min(eY - gridY, e.getSpeed()));
                double yOffset = -Math.min(e.getSpeed(), Math.abs(eBox.getY() - Globals.cellSize * (w.getCurrentRow(e))));
                offset = offset.add(0, yOffset);
            }
            System.out.printf("After X (%.2f, %.2f):", offset.getX(), offset.getY());
            if (collTopLeft) System.out.printf(" TL");
            if (collTopRight) System.out.printf(" TR");
            if (collBotLeft) System.out.printf(" BL");
            if (collBotRight) System.out.printf(" BR");
            System.out.println();
            offset.setY(0);
            eBox.move(offset);

            collTopLeft = false;
            collTopRight = false;
            collBotLeft = false;
            collBotRight = false;
            pointColliding = 0;
            offset.zero();
            eBox.move(0, e.getVelocity().getY());
            for (Entity m:w.getNearbyEntities(e)) {
                if (!m.getCollisionState()) continue;

                Rectangle mBox = m.getHitBox();
                if (mBox.contains(eBox.getTopLeft())) {
                    double yOffset = mBox.getY() + mBox.getH() - eBox.getY();
                    offset.setY(yOffset);
                    collTopLeft = true;
                    pointColliding++;
                }
                if (mBox.contains(eBox.getTopRight())) {
                    double yOffset = mBox.getY() + mBox.getH() - eBox.getY();
                    offset.setY(yOffset);
                    collTopRight = true;
                    pointColliding++;
                }
                if (pointColliding > 1) break;

                if (mBox.contains(eBox.getBotLeft())) {
                    double yOffset = mBox.getY() - (eBox.getY() + eBox.getH());
                    offset.setY(yOffset);
                    collBotLeft = true;
                    pointColliding++;
                }
                if (mBox.contains(eBox.getBotRight())) {
                    double yOffset = mBox.getY() - (eBox.getY() + eBox.getH());
                    offset.setY(yOffset);
                    collBotRight = true;
                    pointColliding++;
                }
            }

            int col = w.getCurrentCol(e);
            int gridX = col * (int)Globals.cellSize;
            double eX = e.getHitBox().getX();
            /*if (eX - gridX < Globals.cellSize / 2) e.getHitBox().move(-Math.min(eX - gridX, e.getSpeed()), 0);
            else if (gridX + Globals.cellSize - eX < Globals.cellSize / 2) e.getHitBox().move(Math.min(gridX + Globals.cellSize - eX, e.getSpeed()), 0);*/

            if (pointColliding > 1) offset.setX(0);
            else if (collTopLeft || collBotLeft) {
                if (gridX + Globals.cellSize - eX < Globals.cellSize / 2) e.getHitBox().move(Math.min(gridX + Globals.cellSize - eX, e.getSpeed()), 0);
                double xOffset = Math.min(e.getSpeed(), Math.abs(Globals.cellSize * (w.getCurrentCol(e) + 1) - eBox.getX()));
                offset = offset.add(xOffset, 0);
            }
            else if (collTopRight || collBotRight){
                if (eX - gridX < Globals.cellSize / 2) e.getHitBox().move(-Math.min(eX - gridX, e.getSpeed()), 0);
                double xOffset = -Math.min(e.getSpeed(), Math.abs(eBox.getX() - Globals.cellSize * (w.getCurrentCol(e))));
                offset = offset.add(xOffset, 0);
            }
            System.out.printf("After Y (%.2f, %.2f):", offset.getX(), offset.getY());
            if (collTopLeft) System.out.printf(" TL");
            if (collTopRight) System.out.printf(" TR");
            if (collBotLeft) System.out.printf(" BL");
            if (collBotRight) System.out.printf(" BR");
            System.out.println();
            System.out.println();
            offset.setX(0);
            eBox.move(offset);
        }
    };

    public static CollisionComponent DynamicOld = new CollisionComponent() {
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
                    int row = w.getCurrentRow(e);
                    int gridY = row * (int)Globals.cellSize;
                    double eY = e.getHitBox().getY();
                    if (eY - gridY < Globals.cellSize / 2) e.getHitBox().move(0, -Math.min(eY - gridY, e.getSpeed()));
                    else if (gridY + Globals.cellSize - eY < Globals.cellSize / 2) e.getHitBox().move(0, Math.min(gridY + Globals.cellSize - eY, e.getSpeed()));

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
            /*int col = w.getCurrentCol(e);
            int gridX = col * (int)Globals.cellSize;
            double eX = e.getHitBox().getX();
            if (eX - gridX < e.getSpeed()) e.moveTo(gridX, e.getHitBox().getY());
            else if (gridX + Globals.cellSize - eX < e.getSpeed()) e.moveTo(gridX + Globals.cellSize, e.getHitBox().getY());*/

            e.getHitBox().move(0, e.getVelocity().getY());
            for (Entity m:w.getNearbyEntities(e)) {
                if (!m.getCollisionState()) continue;
                if (e.getHitBox().intersect(m.getHitBox())) {
                    int col = w.getCurrentCol(e);
                    int gridX = col * (int)Globals.cellSize;
                    double eX = e.getHitBox().getX();
                    if (eX - gridX < Globals.cellSize / 2) e.getHitBox().move(-Math.min(eX - gridX, e.getSpeed()), 0);
                    else if (gridX + Globals.cellSize - eX < Globals.cellSize / 2) e.getHitBox().move(Math.min(gridX + Globals.cellSize - eX, e.getSpeed()), 0);

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
            /*int row = w.getCurrentRow(e);
            int gridY = row * (int)Globals.cellSize;
            double eY = e.getHitBox().getY();
            if (eY - gridY < e.getSpeed()) e.moveTo(e.getHitBox().getX(), gridY);
            else if (gridY + Globals.cellSize - eY < e.getSpeed()) e.moveTo(e.getHitBox().getX(), gridY + Globals.cellSize);*/
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
