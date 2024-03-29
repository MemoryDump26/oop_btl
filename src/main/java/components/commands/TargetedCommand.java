package components.commands;

public interface TargetedCommand<T> {
    void execute(T t);

    static <T> TargetedCommand<T> getNull() {
        return new TargetedCommand<T>() {
            @Override
            public void execute(T t) {
            }
        };
    }
}
