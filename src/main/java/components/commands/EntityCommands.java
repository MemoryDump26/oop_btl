package components.commands;

import components.attack.BombAttack;
import components.input.KeyboardInputComponent;
import entity.Entity;

public class EntityCommands {
    public static TargetedCommand<Entity> Up = Entity::moveUp;
    public static TargetedCommand<Entity> Down = Entity::moveDown;
    public static TargetedCommand<Entity> Left = Entity::moveLeft;
    public static TargetedCommand<Entity> Right = Entity::moveRight;
    public static TargetedCommand<Entity> Attack = Entity::attack;

    public static TargetedCommand<Entity> BombPower = e -> {
        if (e.getAttack() instanceof BombAttack b) {
            int n = b.getNumOfBombs() + 1;
            b.setNumOfBombs(n);
        }
    };

    public static TargetedCommand<Entity> FlamePower = e -> {
        if (e.getAttack() instanceof BombAttack b) {
            int n = b.getPower() + 1;
            b.setPower(n);
        }
    };

    public static TargetedCommand<Entity> SpeedPower = e -> {
        if (e.getInput() instanceof KeyboardInputComponent) {
            double n = e.getSpeed() + 1;
            e.setSpeed(n);
        }
    };
}