package view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import model.User;


public class PlayerMenu extends Menu {
    private User user;
    private final EditMenu editMenu;
    private final GameMenu gameMenu;

    public PlayerMenu(Menu parent) {
        super(parent);
        editMenu = new EditMenu(this);
        gameMenu = new GameMenu(this);
    }

    public void start() {
        window.setTitle("Player Menu");
        GridPane gridPane = createGridPane();
        createBackGround(gridPane);
        addTitle(gridPane);
        addInfo(gridPane);
        addUIControls(gridPane);
        Scene playerScene = new Scene(gridPane, 700, 500);
        window.setScene(playerScene);
        window.show();
    }

    public GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(60, 60, 60, 60));
        gridPane.setHgap(30);
        gridPane.setVgap(30);
        return gridPane;
    }

    private void addInfo(GridPane gridPane) {
        Label playerInfo = new Label( user.getUsername() + "! You scored " + user.getTotalScore() + " till now.");
        labelEdit(playerInfo, 16);
        gridPane.add(playerInfo, 0, 1, 3, 1);
        GridPane.setHalignment(playerInfo, HPos.CENTER);
    }

    private void addUIControls(GridPane gridPane) {
        gridPane.add(BACK_BUTTON, 2, 0);
        Button startGame = new Button();
        startGame.setBackground(new Background(play));
        startGame.setPrefSize(60, 60);
        startGame.setOnAction(event -> {
            playButtonAudio();
            gameMenu.setPlayer(user);
            gameMenu.start();
        });

        Button edit = new Button();
        edit.setBackground(new Background(settings));
        edit.setPrefSize(60, 60);
        editMenu.setUser(user);
        edit.setOnAction(event -> {
            playButtonAudio();
            editMenu.start();
        });

        gridPane.add(startGame, 0, 2,3,1);
        gridPane.add(edit, 0 ,3,3,1);
        GridPane.setHalignment(startGame, HPos.CENTER);
        GridPane.setHalignment(edit, HPos.CENTER);
    }

    public void setUser(User user) {
        this.user = user;
    }

}
