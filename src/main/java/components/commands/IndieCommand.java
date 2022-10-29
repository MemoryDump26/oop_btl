package components.commands;

public interface IndieCommand {
    void execute();

    static IndieCommand getNull() {
        return new IndieCommand() {
            @Override
            public void execute() {
            }
        };
    }
}
