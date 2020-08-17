package view;

import controller.UserController;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.User;


public class LoginMenu extends Menu {
    private final UserController userController = UserController.getInstance();
    private final MainMenu mainMenu;
    private TextField nameField;
    private PasswordField passwordField;

    public LoginMenu(Menu parent) {
        super(parent);
        mainMenu = (MainMenu) parent;
    }

    @Override
    public void start() {
        login(window);
    }

    public void login(Stage window) {
        setFields();
        window.setTitle("LoginMenu");
        GridPane gridPane = createGridPane();
        createBackGround(gridPane);
        addTitle(gridPane);
        addUIControlsAfterRegister(gridPane, window);
        Scene loginScene = new Scene(gridPane, 700, 500);
        window.setScene(loginScene);
        window.show();
    }

    private void register(Stage window) {
        setFields();
        window.setTitle("Register");
        GridPane gridPane = createGridPane();
        createBackGround(gridPane);
        addTitle(gridPane);
        addUIControlsBeforeRegister(gridPane, window);
        Scene registerScene = new Scene(gridPane, 700, 500);
        window.setScene(registerScene);
        window.show();
    }

    public GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(30);
        gridPane.setVgap(30);
        return gridPane;
    }

    public void addUIControlsAfterRegister(GridPane gridPane, Stage window) {

        addUsernamePassBack(gridPane);

        Button loginButton = new Button("Login");
        buttonEdit(loginButton, button, 16);
        gridPane.add(loginButton, 0, 3,2 , 1);
        loginButton.setOnAction(event -> {
            playButtonAudio();
            if (nameField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, window,
                        "Form Error!", "Please enter a username");
                return;
            }

            if (passwordField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, window,
                        "Form Error!", "Please enter a password.");
                return;
            }

            if (!userController.checkUsernameExists(nameField.getText())) {
                showAlert(Alert.AlertType.ERROR, window,
                        "Wrong Username", "Dude! you don't exist.");
                return;
            } else if (!userController.checkCorrectPassword(nameField.getText(), passwordField.getText())) {
                showAlert(Alert.AlertType.ERROR, window,
                        "Wrong Password", "Enter correct password.");
                return;
            }


            showAlert(Alert.AlertType.INFORMATION, window,
                    "Game is going to start soon!", "Welcome " + nameField.getText());

            createPlayerMenu(nameField.getText());
        });

        Button register = new Button("Don't have an account? click here.");
        register.setBackground(new Background(backgroundImage));
        register.setFont(Font.font(FONT, FontWeight.BOLD, 14));
        register.setTextFill(Color.WHITE);
        register.setPrefHeight(20);
        gridPane.add(register, 0, 4, 2, 1);
        GridPane.setHalignment(loginButton, HPos.CENTER);
        GridPane.setHalignment(register, HPos.LEFT);
        GridPane.setMargin(loginButton, new Insets(20, 0, 20, 0));

        register.setOnAction(event -> {
            playButtonAudio();
            register(window);
        });
    }

    public void createPlayerMenu(String username) {
        User user = userController.getPlayerByUsername(username);
        UserController.getInstance().setCurrentUser(user);
        mainMenu.start();
    }

    public void addUIControlsBeforeRegister(GridPane gridPane, Stage window) {

        addUsernamePassBack(gridPane);

        Button submitButton = new Button("Create Account");
        buttonEdit(submitButton, button, 12);
        gridPane.add(submitButton, 0, 3, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));
        submitButton.setOnAction(event -> {
            playButtonAudio();
            if (nameField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, window,
                        "Form Error!", "Please enter a username.");
                return;
            }

            if (passwordField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, window,
                        "Form Error!", "Please enter a password.");
                return;
            }

            if (userController.checkUsernameExists(nameField.getText())) {
                showAlert(Alert.AlertType.ERROR, window,
                        "Form Error!", "This dude already exists.");
                return;
            }

            userController.createAccount(nameField.getText(), passwordField.getText());
            showAlert(Alert.AlertType.INFORMATION, window,
                    "Registration Successful!", "Welcome " + nameField.getText() + "\n" + "Now login.");

            mainMenu.start();
        });

        Button login = new Button("Already have an account ? click here");
        login.setBackground(new Background(backgroundImage));
        login.setFont(Font.font(FONT, FontWeight.BOLD, 14));
        login.setTextFill(Color.WHITE);
        gridPane.add(login, 0, 4, 2, 1);
        GridPane.setHalignment(login, HPos.LEFT);
        login.setOnAction(event -> {
            playButtonAudio();
            login(window);
        });
    }

    private void addUsernamePassBack(GridPane gridPane) {
        gridPane.add(BACK_BUTTON, 1, 4);

        HBox username = new HBox();
        Label usernameLabel = new Label("Username :        ");
        usernameLabel.setTextFill(Color.WHITE);
        usernameLabel.setFont(Font.font(FONT, 16));


        nameField.setPrefHeight(40);
        nameField.setPromptText("username");

        username.getChildren().addAll(usernameLabel, nameField);
        GridPane.setHalignment(username, HPos.CENTER);
        gridPane.add(username, 0, 1, 2, 1);

        HBox password = new HBox();
        Label passwordLabel = new Label("Password :        ");
        passwordLabel.setTextFill(Color.WHITE);
        passwordLabel.setFont(Font.font(FONT, 16));

        passwordField.setPrefHeight(40);
        passwordField.setPromptText("password");
        password.getChildren().addAll(passwordLabel, passwordField);
        gridPane.add(password, 0, 2, 2, 1);
        GridPane.setHalignment(password, HPos.CENTER);
    }

    public void setFields() {
        nameField = new TextField();
        passwordField = new PasswordField();
    }
}