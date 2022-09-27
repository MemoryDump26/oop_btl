package input;

import entity.DynamicEntity;
import entity.Entity;

public abstract class CollisionComponent {
    public abstract void handle(DynamicEntity e, Entity[] wall);
}
