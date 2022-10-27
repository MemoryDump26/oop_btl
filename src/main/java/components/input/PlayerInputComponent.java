package components.input;

import components.Component;
import entity.Entity;
import javafx.scene.input.KeyCode;
import resources.Resources;

import java.util.HashMap;
import java.util.Map;

public class PlayerInputComponent extends Component<Entity> {
    private Map<KeyCode, Command<Entity>> pKeybinds = new HashMap<>();
    private Map<KeyCode, Command<Entity>> hKeybinds = new HashMap<>();
    private Map<KeyCode, Command<Entity>> rKeybinds = new HashMap<>();

    @Override
    public void onAttach(Entity e) {
    }

    public void handle(Entity e) {
        if (e.isDead()) {
            Resources.getSound("player_die").play();
            return;
        }
        for (Map.Entry<KeyCode, Command<Entity>> k : pKeybinds.entrySet()) {
            if (Input.isKeyPressed(k.getKey())) {
                k.getValue().execute(e);
            }
        }
        for (Map.Entry<KeyCode, Command<Entity>> k : hKeybinds.entrySet()) {
            if (Input.isKeyHeld(k.getKey())) {
                k.getValue().execute(e);
            }
        }
        for (Map.Entry<KeyCode, Command<Entity>> k : rKeybinds.entrySet()) {
            if (Input.isKeyReleased(k.getKey())) {
                k.getValue().execute(e);
            }
        }
    }

    public void addKeybind(KeyCode k, Command<Entity> c, String type) {
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
