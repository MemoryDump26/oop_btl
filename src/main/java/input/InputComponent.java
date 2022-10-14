package input;

import entity.Entity;
import javafx.scene.input.KeyCode;
import world.World;

public abstract class InputComponent {
    public abstract void handle(Entity e, World w);
    public abstract void addKeybind(KeyCode k, Command c);
    public abstract void removeKeybind(KeyCode k);

    public static InputComponent Null = new InputComponent() {
        @Override
        public void handle(Entity e, World w) {
            return;
        }

        @Override
        public void addKeybind(KeyCode k, Command c) {
            return;
        }

        @Override
        public void removeKeybind(KeyCode k) {
            return;
        }
    };
}
