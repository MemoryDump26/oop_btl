package components.logic;

import components.Component;
import components.commands.Command;
import components.commands.IndieCommand;
import entity.Entity;

public class CommandOnDead<T> extends Component<Entity> {
    private IndieCommand c;

    public CommandOnDead(IndieCommand c) {
        this.c = c;
    }

    @Override
    public void onAttach(Entity e) {
    }

    @Override
    public void handle(Entity e) {
        if (e.isDead()) c.execute();
    }
}
