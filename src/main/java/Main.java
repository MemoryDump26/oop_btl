import input.Input;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.stage.Stage;
import resources.Resources;
import resources.SoundFX;
import world.World;
public class Main extends Application {
    Group root = new Group();
    Scene scene = new Scene(root);
    Canvas mainCanvas = new Canvas(1000, 500);
    GraphicsContext gc = mainCanvas.getGraphicsContext2D();

    World world;

    public Main() {
        gc.setImageSmoothing(false);
        Resources.getSprites();
        Resources.getLevels();
        Resources.getSounds();
        world = new World(gc);
        world.createLevelFromFile(Resources.levelList.get(0), false);
    }

    public void start(Stage stage) {
        SoundFX.playSound("stage_theme", 1, false);
        //Resources.soundDataMap.get("stage_theme").play();

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
