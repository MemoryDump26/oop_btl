package components.commands;

import components.attack.BombAttack;
import components.input.PlayerInputComponent;
import entity.Entity;

public class EntityCommand {

    public static TargetedCommand<Entity> Up = new TargetedCommand<>() {
        @Override
        public void execute(Entity e) {
            e.moveUp();
        }
    };
    public static TargetedCommand<Entity> Down = new TargetedCommand<>() {
        @Override
        public void execute(Entity e) {
            e.moveDown();
        }
    };
    public static TargetedCommand<Entity> Left = new TargetedCommand<>() {
        @Override
        public void execute(Entity e) {
            e.moveLeft();
        }
    };
    public static TargetedCommand<Entity> Right = new TargetedCommand<>() {
        @Override
        public void execute(Entity e) {
            e.moveRight();
        }
    };
    public static TargetedCommand<Entity> Attack = new TargetedCommand<>() {
        @Override
        public void execute(Entity e) {
            e.attack();
        }
    };

    public static TargetedCommand<Entity> BombPower = new TargetedCommand<>() {
        @Override
        public void execute(Entity e) {
            if (e.getAttack() instanceof BombAttack b) {
                int n = b.getNumOfBombs() + 1;
                b.setNumOfBombs(n);
            }
        }
    };


    public static TargetedCommand<Entity> FlamePower = new TargetedCommand<>() {
        @Override
        public void execute(Entity e) {
            if (e.getAttack() instanceof BombAttack b) {
                int n = b.getPower() + 1;
                b.setPower(n);
            }
        }
    };

    public static TargetedCommand<Entity> SpeedPower = new TargetedCommand<>() {
        @Override
        public void execute(Entity e) {
            if (e.getInput() instanceof PlayerInputComponent) {
                double n = e.getSpeed() + 1;
                e.setSpeed(n);
            }
        }
    };
}