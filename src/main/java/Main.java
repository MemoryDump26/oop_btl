import entity.DynamicEntity;
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

import java.util.ArrayList;

public class Main extends Application {
    Group root = new Group();
    Scene scene = new Scene(root);
    Canvas mainCanvas = new Canvas(1000, 1000);
    GraphicsContext gc = mainCanvas.getGraphicsContext2D();
    Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 48 );
    InputComponent p1Inp = new PlayerInputComponent();
    CollisionComponent p1Col = new PlayerCollisionComponent();
    DynamicEntity p1;

    InputComponent nullInp = new NullInputComponent();
    InputComponent balloomAI = new BalloomAI();
    ArrayList<Entity> wall = new ArrayList<Entity>();

    public Main() {
        Resources.getResource("sprites");
        p1 = new DynamicEntity(new Point(0, 0), p1Inp, p1Col, Resources.spriteDataMap.get("player"), gc);
        wall.add(new DynamicEntity(new Point(200, 200), nullInp, p1Col, Resources.spriteDataMap.get("wall"), gc));
        wall.add(new DynamicEntity(new Point(350, 500), nullInp, p1Col, Resources.spriteDataMap.get("wall"), gc));
        wall.add(new DynamicEntity(new Point(500, 500), nullInp, p1Col, Resources.spriteDataMap.get("wall"), gc));
        wall.add(new DynamicEntity(new Point(650, 500), nullInp, p1Col, Resources.spriteDataMap.get("wall"), gc));
        wall.add(new DynamicEntity(new Point(200, 350), nullInp, p1Col, Resources.spriteDataMap.get("wall"), gc));

        p1Inp.addKeybind(KeyCode.W, Command.Up);
        p1Inp.addKeybind(KeyCode.A, Command.Left);
        p1Inp.addKeybind(KeyCode.S, Command.Down);
        p1Inp.addKeybind(KeyCode.D, Command.Right);
        p1.setSpeed(10);
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
                update();
                renderClear();
                render();
                Input.clear();
            }
        }.start();

        stage.show();
    }

    public void update() {
        p1.update(wall);
    }
    public void renderClear() {
        gc.setGlobalBlendMode(BlendMode.SRC_OVER);
        gc.fillRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
    }
    public void render() {
        for (Entity e:wall) {
            e.render();
        }
        p1.render();
        gc.fillText("lmao", 100, 100);
        gc.strokeText("lmao", 100, 100);
    }

    public static void main(String[] args) {
        launch(args);
    }
}