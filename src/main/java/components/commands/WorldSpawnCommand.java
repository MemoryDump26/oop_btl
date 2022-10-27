package components.commands;

import entity.Entity;
import world.World;

public class WorldSpawnCommand implements TargetedCommand<World> {
    private int row;
    private int col;
    private Entity p;

    public WorldSpawnCommand(int row, int col, Entity p) {
        this.row = row;
        this.col = col;
        this.p = p;
    }

    @Override
    public void execute(World w) {
        w.spawn(row, col, p);
    }
}
