package input;

import entity.Entity;

public class MoveRightCommand extends Command {
    @Override
    public void execute(Entity e) {
        e.moveRight();
    }
}
