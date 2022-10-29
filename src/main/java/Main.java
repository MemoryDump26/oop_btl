import components.input.Input;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.stage.Stage;
import javafx.util.Duration;
import resources.Resources;
import view.ViewManager;
import world.World;

import java.io.File;

public class Main extends Application {
    Group root = new Group();
    Scene scene = new Scene(root);
    Canvas mainCanvas = new Canvas(1280, 720);
    GraphicsContext gc = mainCanvas.getGraphicsContext2D();

    int attempts = 0;
    World world;

    public Main() {
        gc.setImageSmoothing(false);
        Resources.loadAllSprites();
        Resources.loadAllLevels();
        Resources.loadAllSounds();
        root.getChildren().add(mainCanvas);
    }

    public void startGame(Stage gameStage) {
        world = new World(gc);
        File devLevel = new File("/home/memorydump/programming/javaTest/oop_btl/src/main/resources/levels/leveldev69.txt");
        //world.createLevelFromFile(devLevel, false);
        world.createLevelFromFile(Resources.getLevel(0), false);
        Resources.soundDataMap.get("title_screen").stop();
        Resources.soundDataMap.get("stage_theme").setVolume(0.4);
        Resources.soundDataMap.get("stage_theme").play();
        gameStage.setScene(scene);
        gameStage.setTitle("ayy lmao");

        Stage finalStage = gameStage;
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                update();
                renderClear();
                render();
                Input.clear();
                if (Resources.soundDataMap.get("player_die").isPlaying()) {
                    Timeline tl = new Timeline(
                            new KeyFrame(Duration.seconds(2.25), ae -> this.stop()),
                            new KeyFrame(Duration.seconds(2.25), ae -> gameStage.close()),
                            new KeyFrame(Duration.seconds(2.25), ae -> Main.this.start(gameStage)),
                            new KeyFrame(Duration.seconds(2.25), ae -> Resources.soundDataMap.get("player_die").stop()));
                    tl.setCycleCount(1);
                    if (ViewManager.game != 0) {
                        tl.play();
                        ViewManager.game = 0;
                        attempts++;
                    }
                }
            }
        }.start();
        gameStage.show();
    }

    public void start(Stage stage) {
        if (ViewManager.game == 0) {
        Resources.soundDataMap.get("stage_theme").stop();
        ViewManager manager = new ViewManager();
        stage = manager.getMainStage();
        stage.setTitle("Bomberman");
        stage.setResizable(false);
        Input.attachEventHandlers(scene);
        Stage finalStage = stage;
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                if (ViewManager.game == 1) {
                    finalStage.close();
                    startGame(finalStage);
                    this.stop();
                }
            }
        }.start();
        stage.show();
        }
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
