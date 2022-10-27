package components.logic;

import components.Component;
import entity.Entity;

public class FlameLogic extends Component<Entity> {
    private int power;
    private int rowOffset;
    private int colOffset;
    private boolean spawned = false;

    public FlameLogic(int power, int rowOffset, int colOffset) {
        this.power = power;
        this.rowOffset = rowOffset;
        this.colOffset = colOffset;
    }

    @Override
    public void onAttach(Entity e) {
    }

    @Override
    public void handle(Entity e) {
        if (power < 0 || spawned) return;
    }
}
