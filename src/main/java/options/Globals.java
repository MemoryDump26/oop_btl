package options;

import components.commands.Command;
import components.input.PlayerInputComponent;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;

public class Globals{
    public static double cellSize = 32;

    public static ArrayList<PlayerInputComponent> playerKeybinds = new ArrayList<>() {
        {
            PlayerInputComponent p1Inp = new PlayerInputComponent();
            p1Inp.addKeybind(KeyCode.W, Command.Up, "hold");
            p1Inp.addKeybind(KeyCode.A, Command.Left, "hold");
            p1Inp.addKeybind(KeyCode.S, Command.Down, "hold");
            p1Inp.addKeybind(KeyCode.D, Command.Right, "hold");
            p1Inp.addKeybind(KeyCode.J, Command.Attack, "press");

            PlayerInputComponent p2Inp = new PlayerInputComponent();
            p2Inp.addKeybind(KeyCode.UP, Command.Up, "hold");
            p2Inp.addKeybind(KeyCode.LEFT, Command.Left, "hold");
            p2Inp.addKeybind(KeyCode.DOWN, Command.Down, "hold");
            p2Inp.addKeybind(KeyCode.RIGHT, Command.Right, "hold");
            p2Inp.addKeybind(KeyCode.SPACE, Command.Attack, "press");

            add(p1Inp);
            add(p2Inp);
        }
    };
}
