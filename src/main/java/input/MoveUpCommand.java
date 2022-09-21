package input;

import entity.Entity;

public class MoveUpCommand extends Command {
    @Override
    public void execute(Entity e) {
        e.moveUp();
    }
}
