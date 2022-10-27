package components.commands;

import components.commands.Command;

public class TargetedCommand<T> implements IndieCommand {
    private Command<T> command;
    private T target;

    public TargetedCommand(Command<T> command, T target) {
        this.command = command;
        this.target = target;
    }

    public void execute() {
        command.execute(target);
    }
}
