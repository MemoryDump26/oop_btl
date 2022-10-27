package components.commands;

import resources.Resources;

public class PlaySoundCommand implements IndieCommand {
    private final String name;
    private int cycleCount;
    private boolean executed = false;

    public PlaySoundCommand(String name) {
        this(name, 1);
    }

    public PlaySoundCommand(String name, int cycleCount) {
        this.name = name;
        this.cycleCount = cycleCount;
    }

    @Override
    public void execute() {
        if (executed) return;
        Resources.getSound(name).setCycleCount(cycleCount);
        Resources.getSound(name).play();
        executed = true;
    }
}
