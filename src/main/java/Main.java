import entity.Entity;
import entity.Player;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
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
    SpriteData playerSprite = new SpriteData("test/test", 26, 100, 100);
    SpriteData wallSprite = new SpriteData("test/test", 26, 100, 100);
    Player p1 = new Player(new Point2D(0, 0), playerSprite, gc);
    Entity[] wall = new Entity[5];

    public Main() {
        wall[0] = new Entity(new Point2D(0, 500), wallSprite, gc);
        wall[1] = new Entity(new Point2D(100, 500), wallSprite, gc);
        wall[2] = new Entity(new Point2D(200, 500), wallSprite, gc);
        wall[3] = new Entity(new Point2D(300, 500), wallSprite, gc);
        wall[4] = new Entity(new Point2D(400, 500), wallSprite, gc);
    }

    public void start(Stage stage) {
        stage.setScene(scene);
        stage.setTitle("ayy lmao");
        root.getChildren().add(mainCanvas);

        gc.setFill(Color.AQUA);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.setFont(theFont);

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
        p1.move(0, 10);
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