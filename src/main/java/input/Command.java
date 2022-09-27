package input;

import entity.DynamicEntity;

public abstract class Command {
    public abstract void execute(DynamicEntity e);

    public static Command Up = new Command() {
        @Override
        public void execute(DynamicEntity e) {
            e.moveUp();
        }
    };
    public static Command Down = new Command() {
        @Override
        public void execute(DynamicEntity e) {
            e.moveDown();
        }
    };
    public static Command Left = new Command() {
        @Override
        public void execute(DynamicEntity e) {
            e.moveLeft();
        }
    };
    public static Command Right = new Command() {
        @Override
        public void execute(DynamicEntity e) {
            e.moveRight();
        }
    };
    public static Command Null = new Command() {
        @Override
        public void execute(DynamicEntity e) {
            return;
        }
    };
}