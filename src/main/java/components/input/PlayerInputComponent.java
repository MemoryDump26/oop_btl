package components.input;

import components.Component;
import components.commands.EntityCommand;
import components.commands.TargetedCommand;
import entity.Entity;
import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Map;

public class PlayerInputComponent extends Component<Entity> {
    private Map<KeyCode, TargetedCommand<Entity>> pKeybinds = new HashMap<>();
    private Map<KeyCode, TargetedCommand<Entity>> hKeybinds = new HashMap<>();
    private Map<KeyCode, TargetedCommand<Entity>> rKeybinds = new HashMap<>();

    @Override
    public void onAttach(Entity e) {
    }

    public void handle(Entity e) {
        if (e.isDead()) {
            //Resources.getSound("player_die").play();
            return;
        }
        for (Map.Entry<KeyCode, TargetedCommand<Entity>> k : pKeybinds.entrySet()) {
            if (Input.isKeyPressed(k.getKey())) {
                k.getValue().execute(e);
            }
        }
        for (Map.Entry<KeyCode, TargetedCommand<Entity>> k : hKeybinds.entrySet()) {
            if (Input.isKeyHeld(k.getKey())) {
                k.getValue().execute(e);
            }
        }
        for (Map.Entry<KeyCode, TargetedCommand<Entity>> k : rKeybinds.entrySet()) {
            if (Input.isKeyReleased(k.getKey())) {
                k.getValue().execute(e);
            }
        }
    }

    public void addKeybind(KeyCode k, TargetedCommand<Entity> c, String type) {
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
