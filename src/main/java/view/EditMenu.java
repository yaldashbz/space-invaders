package view;

import controller.UserController;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import model.User;

public class EditMenu extends Menu {
    private User user;
    private boolean edit = false;
    public EditMenu(Menu parent) {
        super(parent);
    }

    @Override
    public void start() {
        window.setTitle("Edit Menu");
        GridPane gridPane = createGridPane();
        createBackGround(gridPane);
        addTitle(gridPane);
        addUIControls(gridPane);
        Scene editScene = new Scene(gridPane, 700, 500);
        window.setScene(editScene);
        window.show();
    }

    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(30, 30, 30, 30));
        gridPane.setHgap(30);
        gridPane.setVgap(30);
        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        Label name = new Label("Current Username :");
        Label username = new Label(user.getUsername());
        labelEdit(name, 16);
        labelEdit(username, 16);

        Label password = new Label("Current Password :");
        labelEdit(password, 16);
        Label userPass = new Label(user.getPassword());
        labelEdit(userPass, 16);

        TextField newUsername = new TextField();
        TextField newPass = new TextField();

        Button submit = new Button();
        submit.setBackground(new Background(tick));
        submit.setPrefSize(60, 60);

        Button nameButt = new Button();
        nameButt.setBackground(new Background(process));
        nameButt.setPrefSize(60, 60);
        nameButt.setOnAction(event -> {
            playButtonAudio();
            name.setText("New Username :");
            newUsername.setPromptText("new username");
            newUsername.setPrefHeight(40);
            gridPane.add(newUsername, 1, 1);
            edit = true;
            nameButt.setDisable(true);
        });

        Button passButt = new Button();
        passButt.setBackground(new Background(process));
        passButt.setPrefSize(60, 60);
        passButt.setOnAction(event -> {
            playButtonAudio();
            password.setText("New Password :");
            newPass.setPromptText("new password");
            newPass.setPrefHeight(40);
            gridPane.add(newPass, 1, 2);
            edit = true;
            passButt.setDisable(true);
        });

        submit.setOnAction(event -> {
            playButtonAudio();
            if (!edit) {
                showAlert(Alert.AlertType.ERROR, window,
                        "Error", "You didn't edit anything yet!");
                return;
            }

            if (newUsername.isDisabled() && newUsername.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, window,
                        "Form Error!", "Please enter a username.");
                return;
            }

            if (newPass.isDisabled() && newPass.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, window,
                        "Form Error!", "Please enter a password.");
                return;
            }

            UserController.getInstance().editPlayer(user, newUsername.getText(), newPass.getText());
            showAlert(Alert.AlertType.INFORMATION, window,
                    "Submit Confirmed", "Successful!");

            this.start();
            edit = false;
        });

        gridPane.add(BACK_BUTTON, 2, 0);
        GridPane.setHalignment(BACK_BUTTON, HPos.RIGHT);
        GridPane.setHalignment(name, HPos.CENTER);
        GridPane.setHalignment(password, HPos.CENTER);
        gridPane.add(submit, 0, 3, 3, 1);
        GridPane.setHalignment(submit, HPos.CENTER);
        gridPane.add(name, 0, 1);
        gridPane.add(username, 1, 1);
        gridPane.add(nameButt, 2, 1);

        gridPane.add(password, 0, 2);
        gridPane.add(userPass, 1, 2);
        gridPane.add(passButt, 2, 2);
    }

    public void setUser(User user) {
        this.user = user;
    }
}
