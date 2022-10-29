import components.input.Input;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.stage.Stage;
import resources.Resources;
import world.World;

import java.io.File;

public class Main extends Application {
    Group root = new Group();
    Scene scene = new Scene(root);
    Canvas mainCanvas = new Canvas(1000, 1000);
    GraphicsContext gc = mainCanvas.getGraphicsContext2D();

    World world;

    public Main() {
        gc.setImageSmoothing(false);
        Resources.loadAllSprites();
        Resources.loadAllLevels();
        Resources.loadAllSounds();
        world = new World(gc);
        File devLevel = new File("/home/memorydump/programming/javaTest/oop_btl/src/main/resources/levels/leveldev69.txt");
        world.createLevelFromFile(devLevel, false);
        //world.createLevelFromFile(Resources.getLevel(0), false);
    }

    public void start(Stage stage) {
        //Resources.soundDataMap.get("stage_theme").play();
        Resources.getSound("stage_theme").play();
        stage.setScene(scene);
        stage.setTitle("ayy lmao");
        root.getChildren().add(mainCanvas);

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
