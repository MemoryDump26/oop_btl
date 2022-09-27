package input;

import entity.DynamicEntity;
import entity.Entity;
import javafx.scene.input.KeyCode;

public class NullInputComponent extends InputComponent {
    @Override
    public void handle(DynamicEntity e) {
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