import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {

    public void start(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("ayy lmao");

        Canvas mainCanvas = new Canvas(500, 500);
        root.getChildren().add(mainCanvas);

        GraphicsContext gc = mainCanvas.getGraphicsContext2D();

        gc.setFill(Color.AQUA);
        gc.setStroke( Color.BLACK );
        gc.setLineWidth(2);
        Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 48 );
        gc.setFont( theFont );

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                gc.fillRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
                gc.fillText("lmao", 100, 100);
                gc.strokeText("lmao", 100, 100);
            }
        }.start();

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}