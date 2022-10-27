package options;

import components.commands.EntityCommand;
import components.input.KeyboardInputComponent;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;

public class Globals{
    public static double cellSize = 32;

    public static ArrayList<KeyboardInputComponent> playerKeybinds = new ArrayList<>() {
        {
            KeyboardInputComponent p1Inp = new KeyboardInputComponent();
            p1Inp.addKeybind(KeyCode.W, EntityCommand.Up, "hold");
            p1Inp.addKeybind(KeyCode.A, EntityCommand.Left, "hold");
            p1Inp.addKeybind(KeyCode.S, EntityCommand.Down, "hold");
            p1Inp.addKeybind(KeyCode.D, EntityCommand.Right, "hold");
            p1Inp.addKeybind(KeyCode.J, EntityCommand.Attack, "press");

            KeyboardInputComponent p2Inp = new KeyboardInputComponent();
            p2Inp.addKeybind(KeyCode.UP, EntityCommand.Up, "hold");
            p2Inp.addKeybind(KeyCode.LEFT, EntityCommand.Left, "hold");
            p2Inp.addKeybind(KeyCode.DOWN, EntityCommand.Down, "hold");
            p2Inp.addKeybind(KeyCode.RIGHT, EntityCommand.Right, "hold");
            p2Inp.addKeybind(KeyCode.SPACE, EntityCommand.Attack, "press");

            add(p1Inp);
            add(p2Inp);
        }
    };
}
