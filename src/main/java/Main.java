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
import view.ViewManager;
import world.World;

public class Main extends Application {
    Group root = new Group();
    Scene scene = new Scene(root);
    Canvas mainCanvas = new Canvas(992, 416);
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
    public void startGame(Stage gameStage) {
        Resources.soundDataMap.get("title_screen").stop();
        Resources.soundDataMap.get("stage_theme").play();
        gameStage.setScene(scene);
        gameStage.setTitle("ayy lmao");
        root.getChildren().add(mainCanvas);
        gameStage.show();
    }
    public void start(Stage stage) {
        ViewManager manager = new ViewManager();
        stage = manager.getMainStage();
        stage.setTitle("Bomberman");
        stage.setResizable(false);
        Input.attachEventHandlers(scene);
        Stage finalStage = stage;
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                update();
                renderClear();
                render();
                Input.clear();
                if (ViewManager.game != null) {
                    finalStage.close();
                    startGame(finalStage);
                    ViewManager.game = null;
                }
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
