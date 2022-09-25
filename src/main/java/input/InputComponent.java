package input;

import entity.Entity;
import javafx.scene.input.KeyCode;

public abstract class InputComponent {
    public abstract void handle(Entity e);
    public abstract void addKeybind(KeyCode k, Command c);
    public abstract void removeKeybind(KeyCode k);
}
