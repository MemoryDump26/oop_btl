package components.commands;

import entity.Entity;
import resources.Resources;
import world.World;

public class WorldSpawnCommand extends Command<World> {
    private int row;
    private int col;
    Entity p;
    private boolean executed = false;

    public WorldSpawnCommand(int row, int col, Entity p) {
        this.row = row;
        this.col = col;
        this.p = p;
    }

    @Override
    public void execute(World w) {
        if (executed) return;
        w.spawn(row, col, p);
        executed = true;
    }
}
