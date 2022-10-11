import collision.CollisionComponent;
import entity.DynamicEntity;
import entity.StaticEntity;
import entity.Entity;
import geometry.Point;
import input.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import options.Globals;
import resources.Resources;
import timer.Timer;
import world.World;

import java.util.ArrayList;

public class Main extends Application {
    Group root = new Group();
    Scene scene = new Scene(root);
    Canvas mainCanvas = new Canvas(1000, 1000);
    GraphicsContext gc = mainCanvas.getGraphicsContext2D();
    Font theFont = Font.font("Ariel", FontWeight.BOLD, 48);

    InputComponent p1Inp = new PlayerInputComponent();
    DynamicEntity p1;
    StaticEntity b1;
    World world;
    Timer t1 = new Timer(9999, false);

    ArrayList<Entity> wall = new ArrayList<Entity>();

    public Main() {
        gc.setImageSmoothing(false);
        Resources.getSprites();
        Resources.getLevels();
        world = new World(gc);
        world.createLevelFromFile(Resources.levelList.get(0));
        p1 = new DynamicEntity(new Point(Globals.cellSize, Globals.cellSize), p1Inp, CollisionComponent.Dynamic, Resources.spriteDataMap.get("player"), gc);
        b1 = new StaticEntity(new Point(Globals.cellSize, Globals.cellSize), World.pBomb);
        world.objects.add(b1);
        world.objects.add(p1);
        wall.add(new DynamicEntity(new Point(200, 200), InputComponent.Null, CollisionComponent.Static, Resources.spriteDataMap.get("wall"), gc));
        wall.add(new DynamicEntity(new Point(350, 500), InputComponent.Null, CollisionComponent.Static, Resources.spriteDataMap.get("wall"), gc));
        wall.add(new DynamicEntity(new Point(500, 500), InputComponent.Null, CollisionComponent.Static, Resources.spriteDataMap.get("wall"), gc));
        wall.add(new DynamicEntity(new Point(650, 500), InputComponent.Null, CollisionComponent.Static, Resources.spriteDataMap.get("wall"), gc));
        wall.add(new DynamicEntity(new Point(200, 350), InputComponent.Null, CollisionComponent.Static, Resources.spriteDataMap.get("wall"), gc));

        p1Inp.addKeybind(KeyCode.W, Command.Up);
        p1Inp.addKeybind(KeyCode.A, Command.Left);
        p1Inp.addKeybind(KeyCode.S, Command.Down);
        p1Inp.addKeybind(KeyCode.D, Command.Right);
        p1.setSpeed(4);
        t1.start();
    }

    public void start(Stage stage) {
        stage.setScene(scene);
        stage.setTitle("ayy lmao");
        root.getChildren().add(mainCanvas);

        gc.setFill(Color.AQUA);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.setFont(theFont);

        Input.attachEventHandlers(scene);

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                if (Input.isKeyPressed(KeyCode.P)) {
                    if (t1.isPausing()) t1.resume();
                    else t1.pause();
                }
                update();
                renderClear();
                render();
                Input.clear();
            }
        }.start();

        stage.show();
    }

    public void update() {
        //p1.update(wall);
        b1.update(world.getNearbyEntities(b1));
        p1.update(world.getNearbyEntities(p1));
    }
    public void renderClear() {
        gc.setGlobalBlendMode(BlendMode.SRC_OVER);
        gc.fillRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
    }
    public void render() {
        world.render();
        for (Entity e:wall) {
            e.render();
        }
        //b1.render();
        //p1.render();
        String time = String.format("%d", t1.getElapsedTimeInSecond());
        gc.fillText(time, 100, 100);
        gc.strokeText(time, 100, 100);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
