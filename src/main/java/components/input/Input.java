package components.input;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class Input {
    static ArrayList<KeyCode> pressed = new ArrayList<KeyCode>();
    static ArrayList<KeyCode> released = new ArrayList<KeyCode>();
    static ArrayList<KeyCode> held = new ArrayList<KeyCode>();
    
    public static void attachEventHandlers(Scene s){
        s.setOnKeyReleased(new keyReleaseHandler());
        s.setOnKeyPressed(new keyPressedHandler());
    }

    public static void clear() {
        pressed.clear();
        released.clear();
    }

    public static boolean isKeyPressed(KeyCode k) {
        return pressed.contains(k);
    }
    public static boolean isKeyReleased(KeyCode k) {
        return released.contains(k);
    }
    public static boolean isKeyHeld(KeyCode k) {
        return held.contains(k);
    }
}

class keyPressedHandler implements javafx.event.EventHandler<KeyEvent>{
    @Override
    public void handle(KeyEvent e) {
        KeyCode k = e.getCode();
        if (!Input.held.contains(k) && !Input.pressed.contains(k)) {
            Input.pressed.add(k);
            Input.held.add(k);
        }
    }
}

class keyReleaseHandler implements javafx.event.EventHandler<KeyEvent>{
    @Override
    public void handle(KeyEvent e) {
        KeyCode k = e.getCode();
        if (!Input.released.contains(k)) {
            Input.released.add(k);
        }
        if (Input.held.contains(k)) {
            Input.held.remove(k);
        }
    }
}