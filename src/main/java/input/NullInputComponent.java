package input;

import entity.Entity;
import javafx.scene.input.KeyCode;

public class NullInputComponent extends InputComponent {
    @Override
    public void handle(Entity e) {
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
}