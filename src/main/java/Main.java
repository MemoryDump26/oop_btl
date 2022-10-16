import attack.BombAttack;
import collision.CollisionComponent;
import entity.Entity;
import geometry.Point;
import input.Command;
import input.Input;
import input.InputComponent;
import input.PlayerInputComponent;
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
import resources.SoundFX;
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
    Entity p1;
    World world;
    Timer t1 = new Timer(9999, false);

    ArrayList<Entity> wall = new ArrayList<Entity>();

    public Main() {
        gc.setImageSmoothing(false);
        Resources.getSprites();
        Resources.getLevels();
        Resources.getSoundFX();
        world = new World(gc);
        world.createLevelFromFile(Resources.levelList.get(0));
        p1 = new Entity(
            new Point(Globals.cellSize, Globals.cellSize),
            p1Inp,
            CollisionComponent.Dynamic,
            new BombAttack(1),
            world,
            Resources.spriteDataMap.get("player"),
            gc
        );
        world.players.add(p1);
        world.spawnBomb(3, 3, 4);

        p1Inp.addKeybind(KeyCode.W, Command.Up);
        p1Inp.addKeybind(KeyCode.A, Command.Left);
        p1Inp.addKeybind(KeyCode.S, Command.Down);
        p1Inp.addKeybind(KeyCode.D, Command.Right);
        p1Inp.addKeybind(KeyCode.J, Command.Attack);
        p1.setSpeed(4);
        t1.start();
    }

    public void start(Stage stage) {
        SoundFX.play("/stage_theme.mp3");
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
        world.update();
    }
    public void renderClear() {
        gc.setGlobalBlendMode(BlendMode.SRC_OVER);
        gc.fillRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
    }
    public void render() {
        world.render();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
