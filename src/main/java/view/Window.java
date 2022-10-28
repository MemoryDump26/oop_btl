package view;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.util.Duration;

public class Window extends SubScene{

    private final static String FONT_PATH = "file:src/main/resources/menu/BOMBERMA.TTF";
    private final static String BACKGROUND_IMAGE = "file:src/main/resources/menu/tutorial.png";

    private  boolean isHidden;


    public Window(){
        super(new AnchorPane(), 580, 360);
        prefWidth(1000);
        prefHeight(500);

        BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, 580, 360, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.setBackground(new Background(image));

        isHidden = true ;

        setLayoutX(2390);
        setLayoutY(350);

    }

    public void moveSubScene() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.2));
        transition.setNode(this);

        if (isHidden) {

            transition.setToX(-1676);
            isHidden = false;

        } else {

            transition.setToX(0);
            isHidden = true ;
        }


        transition.play();
    }

    public AnchorPane getPane() {
        return (AnchorPane) this.getRoot();
    }

}
