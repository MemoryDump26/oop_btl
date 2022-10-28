package components;

import components.commands.concrete.RemoteCommand;
import components.commands.TargetedCommand;
import components.commands.concrete.WorldSpawnCommand;
import components.logic.CommandOnDead;
import entity.Entity;
import geometry.Rectangle;
import world.World;

public class EntityComponents {
    public static Component<Entity> SpawnEntityOnDeadComponent(int row, int col, Entity e, World w) {
        TargetedCommand<World> spawnWorldCommand = new WorldSpawnCommand(row, col, e);
        RemoteCommand<World> spawnEntity = new RemoteCommand<>(spawnWorldCommand, w);
        return new CommandOnDead(spawnEntity);
    }

    public static Component<Entity> KillPlayerOnTouch = new Component<>() {
        @Override
        public void onAttach(Entity e) {
        }

        @Override
        public void handle(Entity e) {
            if (e.isDead()) return;
            World w = e.getWorld();
            Rectangle eBox = e.getHitBox();
            for (Entity p:w.getNearbyEntities(e, false, false, false, true)) {
                Rectangle pBox = p.getHitBox();
                if (pBox.intersect(eBox, 0, -1)) p.kill();
                if (pBox.intersect(eBox, 0, 1)) p.kill();
                if (pBox.intersect(eBox, -1, 0)) p.kill();
                if (pBox.intersect(eBox, 1, 0)) p.kill();
            }
        }
    };
}
