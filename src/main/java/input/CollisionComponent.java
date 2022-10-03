package input;

import entity.DynamicEntity;
import entity.Entity;

import java.util.ArrayList;

public abstract class CollisionComponent {
    public abstract void handle(DynamicEntity e, ArrayList<Entity> wall);
}
