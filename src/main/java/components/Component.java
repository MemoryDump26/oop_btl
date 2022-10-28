package components;

public abstract class Component<T> {
    public abstract void onAttach(T t);
    public abstract void handle(T t);

    public static <T> Component<T> getNull() {
        return new Component<T>() {
            @Override
            public void onAttach(T e) {
            }

            @Override
            public void handle(T e) {
            }
        };
    }
}
