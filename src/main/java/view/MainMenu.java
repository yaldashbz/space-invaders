package view;

import controller.UserController;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.*;


public class MainMenu extends Menu {
    private final PlayerMenu playerMenu;
    private final LoginMenu loginMenu;
    private final ScoreBoardMenu scoreboardMenu;

    public MainMenu(Menu parent) {
        super(parent);
        playerMenu = new PlayerMenu(this);
        loginMenu = new LoginMenu(this);
        scoreboardMenu = new ScoreBoardMenu(this);
    }

    @Override
    public void start() {
        window.setTitle("Main Menu");
        GridPane gridPane = createGridPane();
        createBackGround(gridPane);
        addTitle(gridPane);
        if (UserController.getInstance().getCurrentUser() == null)
            addUIControls(gridPane);
        else
            addUIControlsForPlayer(gridPane);
        addScoreBoard(gridPane);
        Scene playerScene = new Scene(gridPane, 700, 500);
        window.setScene(playerScene);
        window.show();
    }

    public GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(30);
        gridPane.setVgap(40);
        return gridPane;
    }

    private void addUIControls(GridPane gridPane) {
        Button login = new Button("Login");
        buttonEdit(login, button, 18);
        login.setOnAction(event -> {
            playButtonAudio();
            loginMenu.setFields();
            loginMenu.start();
        });
        gridPane.add(login, 0, 1, 2,1);
        GridPane.setHalignment(login, HPos.CENTER);
    }



    private void addUIControlsForPlayer(GridPane gridPane) {
        playerMenu.setUser(UserController.getInstance().getCurrentUser());
        Button logout = new Button("Logout");
        buttonEdit(logout, button, 16);
        logout.setOnAction(event -> {
            playButtonAudio();
            logout();
        });
        gridPane.add(logout, 0, 3, 2, 1);
        GridPane.setHalignment(logout, HPos.CENTER);

        Button player = new Button("Player Menu");
        buttonEdit(player, button, 14);
        player.setOnAction(event -> {
            playButtonAudio();
            playerMenu.start();
        });
        gridPane.add(player, 0, 1, 2, 1);
        GridPane.setHalignment(player, HPos.CENTER);
    }

    private void addScoreBoard(GridPane gridPane) {
        Button scoreboard = new Button("Scoreboard");
        buttonEdit(scoreboard, button, 14);
        scoreboard.setOnAction(event -> {
            playButtonAudio();
            scoreboardMenu.start();
        });
        gridPane.add(scoreboard, 0, 2, 2,1);
        GridPane.setHalignment(scoreboard, HPos.CENTER);
    }

    private void logout() {
        showAlert(Alert.AlertType.INFORMATION, window,
                ":(", "bye bye " + UserController.getInstance().getCurrentUser().getUsername());
        UserController.getInstance().setCurrentUser(null);
        this.start();
    }
}
