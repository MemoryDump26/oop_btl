package components.commands;

public class RemoteCommand<T> implements IndieCommand {
    private TargetedCommand<T> command;
    private T target;

    public RemoteCommand(TargetedCommand<T> command, T target) {
        this.command = command;
        this.target = target;
    }

    public void execute() {
        command.execute(target);
    }
}
