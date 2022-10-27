package components.commands;

import components.Component;
import components.logic.CommandOnDead;
import entity.Entity;
import world.World;

public class CommandPresets {
    public static Component<Entity> SpawnEntityOnDeadComponent(int row, int col, Entity e, World w) {
        TargetedCommand<World> spawnWorldCommand = new WorldSpawnCommand(row, col, e);
        RemoteCommand<World> spawnEntity = new RemoteCommand<>(spawnWorldCommand, w);
        return new CommandOnDead(spawnEntity);
    }
}
