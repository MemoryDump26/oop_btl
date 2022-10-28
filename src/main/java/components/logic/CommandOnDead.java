package components.logic;

import components.Component;
import components.commands.IndieCommand;
import entity.Entity;

public class CommandOnDead extends Component<Entity> {
    private IndieCommand c;
    private boolean executed = false;

    public CommandOnDead(IndieCommand c) {
        this.c = c;
    }

    @Override
    public void onAttach(Entity e) {
    }

    @Override
    public void handle(Entity e) {
        if (executed) return;
        if (e.isDead()) {
            c.execute();
            executed = true;
        }
    }
}
