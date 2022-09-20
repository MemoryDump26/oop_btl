import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import sprite.Sprite;
import sprite.SpriteData;

public class Main extends Application {
    Group root = new Group();
    Scene scene = new Scene(root);
    Canvas mainCanvas = new Canvas(1000, 1000);
    GraphicsContext gc = mainCanvas.getGraphicsContext2D();
    Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 48 );
    int x = 0;
    int y = 0;
    SpriteData testData;
    Sprite test;

    public Main() {
        testData = new SpriteData("test/test", 26, 1000, 1000);
        test = new Sprite(testData, gc);
    }

    public void start(Stage stage) {
        stage.setScene(scene);
        stage.setTitle("ayy lmao");
        root.getChildren().add(mainCanvas);
        test.setTickPerFrame(3);

        gc.setFill(Color.AQUA);
        gc.setStroke( Color.WHITE );
        gc.setLineWidth(2);
        gc.setFont( theFont );

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                update();
                renderClear();
                render();
            }
        }.start();

        stage.show();
    }

    public void update() {
        x = (x + 10) % 1000;
        y = (y + 10) % 1000;
    }
    public void renderClear() {
        gc.setGlobalBlendMode(BlendMode.SRC_OVER);
        gc.fillRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
    }
    public void render() {
        //gc.drawImage(test.images[test.advance()], x, y);
        test.render(100, 100, 200, 200);
        gc.fillText("lmao", 100, 100);
        gc.strokeText("lmao", 100, 100);
    }

    public static void main(String[] args) {
        launch(args);
    }
}