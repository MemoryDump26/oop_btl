package components.input;

import geometry.Point;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.HashSet;
import java.util.Set;

public class Input {
    protected static Set<KeyCode> pressed = new HashSet<>();
    protected static Set<KeyCode> released = new HashSet<>();
    protected static Set<KeyCode> held = new HashSet<>();

    protected static Set<MouseButton> mousePressed = new HashSet<>();
    protected static Set<MouseButton> mouseReleased = new HashSet<>();
    protected static Set<MouseButton> mouseHeld = new HashSet<>();

    protected static double mouseX;
    protected static double mouseY;

    public static void attachEventHandlers(Scene s){
        s.setOnKeyReleased(new KeyReleasedHandler());
        s.setOnKeyPressed(new KeyPressedHandler());
        s.setOnMouseMoved(new UpdateMousePosition());
        s.setOnMousePressed(new MousePressedHandler());
        s.setOnMouseReleased(new MouseReleasedHandler());
    }

    public static void clear() {
        pressed.clear();
        released.clear();
        mousePressed.clear();
        mouseReleased.clear();
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

    public static boolean isMousePressed(MouseButton b) {
        return mousePressed.contains(b);
    }

    public static boolean isMouseReleased(MouseButton b) {
        return mouseReleased.contains(b);
    }

    public static boolean isMouseHeld(MouseButton b) {
        return mouseHeld.contains(b);
    }

    public static Point getRelativeMousePosition() {
        return new Point(mouseX, mouseY);
    }
}

class KeyPressedHandler implements javafx.event.EventHandler<KeyEvent> {
    @Override
    public void handle(KeyEvent e) {
        KeyCode k = e.getCode();
        Input.pressed.add(k);
        Input.held.add(k);
    }
}

class KeyReleasedHandler implements javafx.event.EventHandler<KeyEvent> {
    @Override
    public void handle(KeyEvent e) {
        KeyCode k = e.getCode();
        Input.held.remove(k);
        Input.released.add(k);
    }
}

class UpdateMousePosition implements javafx.event.EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent e) {
        Input.mouseX = e.getX();
        Input.mouseY = e.getY();
    }
}

class MousePressedHandler implements javafx.event.EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent e) {
        MouseButton b = e.getButton();
        Input.mousePressed.add(b);
        Input.mouseHeld.add(b);
    }
}

class MouseReleasedHandler implements javafx.event.EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent e) {
        MouseButton b = e.getButton();
        Input.mouseHeld.remove(b);
        Input.mouseReleased.add(b);
    }
}