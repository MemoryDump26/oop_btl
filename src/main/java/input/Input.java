package input;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class Input {
    static ArrayList<String> pressed = new ArrayList<String>();
    static ArrayList<String> released = new ArrayList<String>();
    static ArrayList<String> held = new ArrayList<String>();
    
    public static void attachEventHandlers(Scene s){
        s.setOnKeyReleased(new keyReleaseHandler());
        s.setOnKeyPressed(new keyPressedHandler());
    }

    public static void clear() {
        pressed.clear();
        released.clear();
    }

    public static boolean isKeyPressed(String k) {
        return pressed.contains(k);
    }
    public static boolean isKeyReleased(String k) {
        return released.contains(k);
    }
    public static boolean isKeyHeld(String k) {
        return held.contains(k);
    }
}

class keyPressedHandler implements javafx.event.EventHandler<KeyEvent>{
    @Override
    public void handle(KeyEvent e) {
        String k = e.getCode().getName();
        if (!Input.held.contains(k) && !Input.pressed.contains(k)) {
            Input.pressed.add(k);
            Input.held.add(k);
        }
    }
}

class keyReleaseHandler implements javafx.event.EventHandler<KeyEvent>{
    @Override
    public void handle(KeyEvent e) {
        String k = e.getCode().getName();
        if (!Input.released.contains(k)) {
            Input.released.add(k);
        }
        if (Input.held.contains(k)) {
            Input.held.remove(k);
        }
    }
}