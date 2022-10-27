package view;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class menuButton extends Button {
    private final String FONT_PATH = "src/main/resources/menu/BOMBERMA.TTF";
    private static final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent;-fx-background-image: url('file:src/main/resources/menu/red_button_pressed.png');";
    private static final String BUTTON_FREE_STYLE = "-fx-background-color: transparent;-fx-background-image: url('file:src/main/resources/menu/red_button.png');";

    public menuButton(String text) {
        setText(text);
        setBackground(getBackground());
        setButtonFont();
        setPrefWidth(190);
        setPrefHeight(49);
        initialiseButtonListeners();
    }

    private void setButtonFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), 25));
        } catch (FileNotFoundException e) {
            setFont(Font.font("BOMBERMA", 25));
            System.out.println("Font not found or could not be loaded. Using default \"Verdana\"");
        }

    }

    void setButtonPressedStyle() {
        setStyle(BUTTON_PRESSED_STYLE);
        setPrefHeight(49);
        setLayoutY(getLayoutY() + 4);

    }

    private void setButtonReleasedStyle() {
        setStyle(BUTTON_FREE_STYLE);
        setPrefHeight(49);
        setLayoutY(getLayoutY() - 4);
    }

    private void initialiseButtonListeners() {
        setStyle(BUTTON_FREE_STYLE);
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonPressedStyle();

                }
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonReleasedStyle();
                }
            }

        });
        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(new DropShadow(50, Color.YELLOW));
            }
        });
        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(null);
            }
        });
    }
}
