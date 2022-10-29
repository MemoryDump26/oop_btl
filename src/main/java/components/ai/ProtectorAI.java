package components.ai;

import components.Component;
import components.commands.IndieCommand;
import components.commands.TargetedCommand;
import components.commands.concrete.RemoteCommand;
import components.logic.CommandOnTimer;
import entity.Entity;
import geometry.Circle;
import geometry.Line;
import geometry.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import options.Globals;
import timer.Timer;
import world.World;

import java.util.ArrayList;

import static javafx.scene.paint.Color.*;

public class ProtectorAI extends RandomMovementAI{
    private Circle protectRadius = new Circle(200);
    private Timer protectCooldown = new Timer(2);
    private Timer protectDuration = new Timer(1);
    private ArrayList<Entity> protecting = new ArrayList<>();
    private Color shieldColor = rgb(68, 158, 214, 0.1);
    private Color protectedColor = rgb(142, 209, 255, 0.8);

    public ProtectorAI(boolean incStatic, boolean incObjects, boolean incEnemies, boolean incPlayers) {
        super(incStatic, incObjects, incEnemies, incPlayers);
    }

    public ProtectorAI() {
        super(true, true, true, false);
    }

    @Override
    public void onAttach(Entity e) {
        super.onAttach(e);
        protectDuration.start();
    }

    @Override
    public void handle(Entity e) {
        if (e.isDead()) return;

        if (protectDuration.isFinished()) {
            for (Entity enemy : protecting) {
                enemy.setDestructible(true);
            }
            protecting.clear();
            protectCooldown.start();
            protectDuration.stop();
        }

        if (protectCooldown.isFinished()) {
            if (!protectDuration.isRunning()) protectDuration.start();
            World w = e.getWorld();
            Point pProtector = e.getHitBox().getCenter();
            protectRadius.setCenter(pProtector);
            ArrayList<Entity> enemies = w.getAllEntities(false, false, true, false);
            for (Entity enemy : enemies) {
                Point pEnemy = enemy.getHitBox().getCenter();
                if (!protecting.contains(enemy) && protectRadius.contains(pEnemy)) {
                    if (enemy.isDestructible()) enemy.setDestructible(false);
                    protecting.add(enemy);
                }
                if (protecting.contains(enemy) && !protectRadius.contains(pEnemy)) {
                    enemy.setDestructible(true);
                    protecting.remove(enemy);
                }
            }
            double x = protectRadius.getX();
            double y = protectRadius.getY();
            double r = protectRadius.getR();
            w.commandsAfterDraw.add(createWorldDrawCommand(x - r, y - r, r, shieldColor, w.getGc()));
            for (Entity enemy : protecting) {
                x = enemy.getHitBox().getX();
                y = enemy.getHitBox().getY();
                r = Globals.cellSize / 2;
                w.commandsAfterDraw.add(createWorldDrawCommand(x, y, r, protectedColor, w.getGc()));
            }
        }

        super.handle(e);
    }

    private IndieCommand createWorldDrawCommand(double x, double y, double radius, Paint color, GraphicsContext gc) {
        TargetedCommand<GraphicsContext> drawCircle = new TargetedCommand<>() {
            @Override
            public void execute(GraphicsContext gc) {
                gc.setFill(color);
                gc.fillOval(x, y, radius * 2, radius * 2);
                gc.setFill(BLACK);
            }
        };
        RemoteCommand<GraphicsContext> drawCircleCommand = new RemoteCommand<>(drawCircle, gc);
        return drawCircleCommand;
    }
}
