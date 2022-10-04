package collision;

import entity.Entity;

import java.util.ArrayList;

public abstract class CollisionComponent {
    public abstract void handle(Entity e, ArrayList<Entity> world);

    public static CollisionComponent Null = new CollisionComponent() {
        public void handle(Entity e, ArrayList<Entity> world) {
            return;
        }
    };
}
