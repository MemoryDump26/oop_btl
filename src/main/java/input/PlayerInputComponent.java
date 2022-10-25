package input;

import entity.Entity;
import javafx.scene.input.KeyCode;
import world.World;

import java.util.HashMap;
import java.util.Map;

public class PlayerInputComponent extends InputComponent {
    private Map<KeyCode, Command> pKeybinds = new HashMap<KeyCode, Command>();
    private Map<KeyCode, Command> hKeybinds = new HashMap<KeyCode, Command>();
    private Map<KeyCode, Command> rKeybinds = new HashMap<KeyCode, Command>();

    @Override
    public void onAttach(Entity e) {
    }

    public void handle(Entity e, World w) {
        if (e.isDead()) return;
        for (Map.Entry<KeyCode, Command> k : pKeybinds.entrySet()) {
            if (Input.isKeyPressed(k.getKey())) {
                k.getValue().execute(e);
            }
        }
        for (Map.Entry<KeyCode, Command> k : hKeybinds.entrySet()) {
            if (Input.isKeyHeld(k.getKey())) {
                k.getValue().execute(e);
            }
        }
        for (Map.Entry<KeyCode, Command> k : rKeybinds.entrySet()) {
            if (Input.isKeyReleased(k.getKey())) {
                k.getValue().execute(e);
            }
        }
    }

    public void addKeybind(KeyCode k, Command c, String type) {
        switch (type) {
            case "press" -> pKeybinds.put(k, c);
            case "hold" -> hKeybinds.put(k, c);
            case "release" -> rKeybinds.put(k, c);
        }
    }

    public void removeKeybind(KeyCode k, String type) {
        switch (type) {
            case "press" -> pKeybinds.remove(k);
            case "hold" -> hKeybinds.remove(k);
            case "release" -> rKeybinds.remove(k);
        }
    }
}
