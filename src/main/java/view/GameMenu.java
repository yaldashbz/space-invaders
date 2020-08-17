package view;

import controller.Game;
import controller.UserController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.User;

public class GameMenu extends Menu {
    private User player;
    private Game game;
    private Timeline timeline;
    public GameMenu(Menu parent) {
        super(parent);
    }

    @Override
    public void start() {
        StackPane stackPane = new StackPane();
        Canvas canvas = new Canvas(SCENE_WIDTH, SCENE_HEIGHT);

        stackPane.getChildren().addAll(canvas, BACK_BUTTON);
        stackPane.setAlignment(Pos.BOTTOM_RIGHT);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        game = new Game(gc, this);
        Scene scene = new Scene(stackPane);
        scene.setOnKeyPressed(event -> game.keyPressed(event));
        timeline = new Timeline(new KeyFrame(Duration.millis(80), event -> game.run()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        BACK_BUTTON.setOnAction(event -> endGame());

        window.setScene(scene);
        window.setTitle("Space Invaders");
        window.show();
    }

    public void endGame() {
        timeline.stop();
        endGameView();
    }

    public void endGameView() {
        GridPane gridPane = createGridPane();
        createBackGround(gridPane);
        addTitle(gridPane);
        addUIControlsAfterGame(gridPane);
        handleScore();
        Scene scene = new Scene(gridPane, SCENE_WIDTH, SCENE_HEIGHT);
        window.setScene(scene);
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

    public void addUIControlsAfterGame(GridPane gridPane) {
        createMessage(gridPane);

        Button replayButton = new Button();
        replayButton.setOnAction(event -> {
            playButtonAudio();
            start();
        });
        replayButton.setBackground(new Background(replay));
        replayButton.setPrefSize(60, 60);
        gridPane.add(replayButton, 0, 3,2,1);

        BACK_BUTTON.setOnAction(event -> {
            playButtonAudio();
            this.parent.start();
        });
        gridPane.add(BACK_BUTTON, 0, 4,2,1);

        GridPane.setHalignment(replayButton, HPos.CENTER);
        GridPane.setHalignment(BACK_BUTTON, HPos.CENTER);
    }

    public void setPlayer(User player) {
        this.player = player;
    }

    private void createMessage(GridPane gridPane) {
        String message = game.getMessage();
        if (message == null)
            message = "Never do it again :|";

        Text text = new Text(message);
        editText(text);

        Text score = new Text("You've got " + game.getScore() + " score in this game.");
        editText(score);

        gridPane.add(text, 0, 1, 2, 1);
        gridPane.add(score, 0, 2,2 ,1);

        GridPane.setHalignment(text, HPos.CENTER);
        GridPane.setHalignment(score, HPos.CENTER);
    }

    private void editText(Text text) {
        text.setFont(Font.font(FONT, FontWeight.BOLD, 30));
        text.setFill(Color.WHITE);
        text.setFontSmoothingType(FontSmoothingType.LCD);
    }

    private void handleScore() {
        if (game.getWon())
            UserController.getInstance().increaseWins(player, 1);
        else
            UserController.getInstance().increaseLoses(player, 1);
        UserController.getInstance().increaseScore(player, game.getScore());
    }
}
