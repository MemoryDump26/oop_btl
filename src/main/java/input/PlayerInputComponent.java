package input;

import entity.DynamicEntity;
import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Map;

public class PlayerInputComponent extends InputComponent {
    private Map<KeyCode, Command> keybinds = new HashMap<KeyCode, Command>();

    public void handle(DynamicEntity e) {
        for (Map.Entry<KeyCode, Command> k : keybinds.entrySet()) {
            if (Input.isKeyHeld(k.getKey())) {
                k.getValue().execute(e);
            }
        }
    }

    public void addKeybind(KeyCode k, Command c) {
        keybinds.put(k, c);
    }

    public void removeKeybind(KeyCode k) {
        keybinds.remove(k);
    }
}
