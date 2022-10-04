package collision;

import entity.Entity;

import java.util.ArrayList;

public abstract class CollisionComponent {
    public abstract void handle(Entity e, ArrayList<Entity> world);
}
