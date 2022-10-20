package input;

import entity.Entity;
import javafx.scene.input.KeyCode;
import world.World;

public abstract class InputComponent {
    public abstract void handle(Entity e, World w);

    public static InputComponent Null = new InputComponent() {
        @Override
        public void handle(Entity e, World w) {
            return;
        }
    };
}
