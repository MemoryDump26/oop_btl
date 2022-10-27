package components.input;

import components.Component;
import components.commands.TargetedCommand;
import entity.Entity;
import javafx.scene.input.MouseButton;

import java.util.HashMap;
import java.util.Map;

public class MouseInputComponent extends Component<Entity> {
    private final Map<MouseButton, TargetedCommand<Entity>> pKeybinds = new HashMap<>();
    private final Map<MouseButton, TargetedCommand<Entity>> hKeybinds = new HashMap<>();
    private final Map<MouseButton, TargetedCommand<Entity>> rKeybinds = new HashMap<>();

    @Override
    public void onAttach(Entity e) {
    }

    @Override
    public void handle(Entity e) {
        for (Map.Entry<MouseButton, TargetedCommand<Entity>> m : pKeybinds.entrySet()) {
            if (Input.isMousePressed(m.getKey())) {
                m.getValue().execute(e);
            }
        }

        for (Map.Entry<MouseButton, TargetedCommand<Entity>> m : hKeybinds.entrySet()) {
            if (Input.isMouseHeld(m.getKey())) {
                m.getValue().execute(e);
            }
        }

        for (Map.Entry<MouseButton, TargetedCommand<Entity>> m : rKeybinds.entrySet()) {
            if (Input.isMouseReleased(m.getKey())) {
                m.getValue().execute(e);
            }
        }
    }

    public void addKeybind(MouseButton k, TargetedCommand<Entity> c, String type) {
        switch (type) {
            case "press" -> pKeybinds.put(k, c);
            case "hold" -> hKeybinds.put(k, c);
            case "release" -> rKeybinds.put(k, c);
        }
    }

    public void removeKeybind(MouseButton k, String type) {
        switch (type) {
            case "press" -> pKeybinds.remove(k);
            case "hold" -> hKeybinds.remove(k);
            case "release" -> rKeybinds.remove(k);
        }
    }
}