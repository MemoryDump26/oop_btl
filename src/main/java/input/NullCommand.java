package input;

import entity.Entity;

public class NullCommand extends Command {
    @Override
    public void execute(Entity e) {
        return;
    }
}