package components;

import entity.Entity;
import input.InputComponent;

public abstract class Component<T> {
    public abstract void onAttach(T t);
    public abstract void handle(T t);

    public static <T> Component<T> getNull() {
        return new Component<T>() {
            @Override
            public void onAttach(Object e) {
            }

            @Override
            public void handle(Object e) {
            }
        };
    }

    public static Component<Object> Null = new Component<>() {
        @Override
        public void onAttach(Object e) {
        }

        @Override
        public void handle(Object e) {
        }
    };
}
