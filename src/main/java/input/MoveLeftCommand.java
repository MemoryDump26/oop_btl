package input;

import entity.Entity;

public class MoveLeftCommand extends Command {
    @Override
    public void execute(Entity e) {
        e.moveLeft();
    }
}
