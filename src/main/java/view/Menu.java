package view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Commons;

import java.io.File;

public abstract class Menu implements Commons {
    protected Menu parent;
    public Button BACK_BUTTON = new Button();
    public static Stage window;
    public static double BUTTON_HEIGHT = 50;
    public static double BUTTON_WIDTH = 100;

    public Menu(Menu parent) {
        this.parent = parent;
        if (parent != null)
            BACK_BUTTON.setOnAction(event -> {
                playButtonAudio();
                parent.start();
            });
        createBackButton();
    }

    public static void setWindow(Stage window) {
       Menu.window = window;
    }

    public void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public void labelEdit(Label text, double size) {
        text.setTextFill(Color.WHITE);
        text.setFont(Font.font("Britannic Bold", size));
    }

    public void buttonEdit(Button button, BackgroundImage image, double size) {
        button.setFont(Font.font("Britannic Bold", FontWeight.BOLD, size));
        button.setTextFill(Color.WHITE);
        button.setBackground(new Background(image));
        button.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
    }



    private void createBackButton() {
        Image image = Commons.createImage("back");
        BackgroundImage back = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        BACK_BUTTON.setBackground(new Background(back));
        BACK_BUTTON.setPrefSize(40, 40);
    }

    public void addTitle(GridPane gridPane) {
        Text title = new Text("Space Invaders");
        title.setFont(Font.font("Britannic Bold", FontWeight.BOLD, 40));
        title.setFill(Color.WHITE);
        title.setFontSmoothingType(FontSmoothingType.LCD);
        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(2.0f);
        shadow.setOffsetY(2.0f);
        shadow.setColor(Color.GRAY);
        title.setEffect(shadow);
        title.setStroke(Color.GHOSTWHITE);
        gridPane.add(title, 0 ,0);
        GridPane.setMargin(title, new Insets(40, 0, 40, 0));
        GridPane.setHalignment(title, HPos.CENTER);
    }

    public void createBackGround(GridPane gridPane) {
        Background background = new Background(backgroundImage);
        gridPane.setBackground(background);
        ImageView image = new ImageView(rocket);
        gridPane.add(image, 1, 0);
    }

    public void playButtonAudio() {
        File file = new File(audiosPath + File.separator + "button.mp3");
        String path = file.toURI().toString();
        AudioClip sound = new AudioClip(path);
        sound.play(0.7);
    }

    public abstract void start();
}
