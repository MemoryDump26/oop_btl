package view;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import resources.Resources;
public class ViewManager {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private AnchorPane mainPane;
    private Stage mainStage;
    private Scene mainScene;

    public static String game = null;


    public ViewManager() {
        Resources.soundDataMap.get("title_screen").setVolume(0.2);
        Resources.soundDataMap.get("title_screen").play();
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        createPlayButtons();
        createHighScoreButtons();
        createHowToPlayButtons();
        createQuitButtons();
        createBackground();

    }

    public Stage getMainStage()
    {
        return mainStage;
    }

    public void createPlayButtons() {
        menuButton button = new menuButton(" Play ");
        button.setLayoutX(500);
        button.setLayoutY(400);
        mainPane.getChildren().add(button);
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("mouse enter");
            }
        });
        button.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    button.setButtonPressedStyle();
                    game = "1";
                }
            }
        });
    }

    public void createHighScoreButtons() {
        menuButton button = new menuButton("Highscore");
        button.setLayoutX(500);
        button.setLayoutY(475);
        mainPane.getChildren().add(button);
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("mouse enter");
            }
        });
    }

    public void createHowToPlayButtons() {
        menuButton button = new menuButton("Tutorial");
        button.setLayoutX(500);
        button.setLayoutY(550);
        mainPane.getChildren().add(button);
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("mouse enter");
            }
        });
    }

    public void createQuitButtons() {
        menuButton button = new menuButton("Quit");
        button.setLayoutX(500);
        button.setLayoutY(625);
        mainPane.getChildren().add(button);
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("mouse enter");
            }
        });
    }

    private void createBackground() {
        try {
            Image bgImage = new Image("file:src/main/resources/menu/backgroundmenu.jpg", 0, 0, false, true);
            BackgroundImage bg = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT, new BackgroundSize(1280, 720, false, false, false, false));
            mainPane.setBackground(new Background(bg));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
