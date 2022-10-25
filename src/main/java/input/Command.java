package input;

import entity.Entity;

public abstract class Command<T> {
    public abstract void execute(T e);

    public static Command<Entity> Up = new Command<>() {
        @Override
        public void execute(Entity e) {
            e.moveUp();
        }
    };
    public static Command<Entity> Down = new Command<>() {
        @Override
        public void execute(Entity e) {
            e.moveDown();
        }
    };
    public static Command<Entity> Left = new Command<>() {
        @Override
        public void execute(Entity e) {
            e.moveLeft();
        }
    };
    public static Command<Entity> Right = new Command<>() {
        @Override
        public void execute(Entity e) {
            e.moveRight();
        }
    };
    public static Command<Entity> Attack = new Command<>() {
        @Override
        public void execute(Entity e) {
            e.attack();
        }
    };
    public static Command<Object> Null = new Command<Object>() {
        @Override
        public void execute(Object o) {
            return;
        }
    };
}