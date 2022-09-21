package input;

import entity.Entity;

public abstract class Command {
    public abstract void execute(Entity e);
}
