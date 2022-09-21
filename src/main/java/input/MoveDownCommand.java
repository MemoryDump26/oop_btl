package input;

import entity.Entity;

public class MoveDownCommand extends Command {
    @Override
    public void execute(Entity e) {
        e.moveDown();
    }
}
