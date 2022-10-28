package options;

import components.commands.EntityCommands;
import components.input.KeyboardInputComponent;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;

public class Globals{
    public static double cellSize = 32;

    public static ArrayList<KeyboardInputComponent> playerKeybinds = new ArrayList<>() {
        {
            KeyboardInputComponent p1Inp = new KeyboardInputComponent();
            p1Inp.addKeybind(KeyCode.W, EntityCommands.Up, "hold");
            p1Inp.addKeybind(KeyCode.A, EntityCommands.Left, "hold");
            p1Inp.addKeybind(KeyCode.S, EntityCommands.Down, "hold");
            p1Inp.addKeybind(KeyCode.D, EntityCommands.Right, "hold");
            p1Inp.addKeybind(KeyCode.J, EntityCommands.Attack, "press");

            KeyboardInputComponent p2Inp = new KeyboardInputComponent();
            p2Inp.addKeybind(KeyCode.UP, EntityCommands.Up, "hold");
            p2Inp.addKeybind(KeyCode.LEFT, EntityCommands.Left, "hold");
            p2Inp.addKeybind(KeyCode.DOWN, EntityCommands.Down, "hold");
            p2Inp.addKeybind(KeyCode.RIGHT, EntityCommands.Right, "hold");
            p2Inp.addKeybind(KeyCode.SPACE, EntityCommands.Attack, "press");

            add(p1Inp);
            add(p2Inp);
        }
    };
}
