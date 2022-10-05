package input;

import entity.Entity;

public abstract class Command {
    public abstract void execute(Entity e);

    public static Command Up = new Command() {
        @Override
        public void execute(Entity e) {
            e.moveUp();
        }
    };
    public static Command Down = new Command() {
        @Override
        public void execute(Entity e) {
            e.moveDown();
        }
    };
    public static Command Left = new Command() {
        @Override
        public void execute(Entity e) {
            e.moveLeft();
        }
    };
    public static Command Right = new Command() {
        @Override
        public void execute(Entity e) {
            e.moveRight();
        }
    };
    public static Command Null = new Command() {
        @Override
        public void execute(Entity e) {
            return;
        }
    };
}