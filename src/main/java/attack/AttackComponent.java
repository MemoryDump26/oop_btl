package attack;

import entity.Entity;
import world.World;

public abstract class AttackComponent {
    public abstract void handle(Entity e, World w);

    public static AttackComponent Null = new AttackComponent() {
        @Override
        public void handle(Entity e, World w) {
            return;
        }
    };
}
