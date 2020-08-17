package view;

import controller.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import model.User;

public class ScoreBoardMenu extends Menu {

    public ScoreBoardMenu(Menu parent) {
        super(parent);
    }

    @Override
    public void start() {
        GridPane gridPane = createGridPane();
        addTitle(gridPane);
        createBackGround(gridPane);
        addUIControls(gridPane);
        TableView<User> scoreboard = createTable();
        gridPane.add(scoreboard, 0, 1);
        Scene scene = new Scene(gridPane, 700, 500);
        window.setScene(scene);
        window.show();
    }

    private void addUIControls(GridPane gridPane) {
        gridPane.add(BACK_BUTTON, 1, 1);
        GridPane.setHalignment(BACK_BUTTON, HPos.RIGHT);
        GridPane.setValignment(BACK_BUTTON, VPos.BOTTOM);
    }

    private TableView<User> createTable() {
        TableView<User> table = new TableView<>();
        table.getItems().addAll(getPlayerList());
        table.getColumns().addAll(getNameColumn(), getTotalScoreColumn(), getWinsColumn(), getLosesColumn());
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("nothing yet!"));
        return table;
    }

    private ObservableList<User> getPlayerList() {
        return FXCollections.observableArrayList(UserController.getInstance().getSortedPlayers());
    }

    private TableColumn<User, String> getNameColumn() {
        TableColumn<User, String> names = new TableColumn<>("Username");
        PropertyValueFactory<User, String> nameCellValueFactory = new PropertyValueFactory<>("username");
        names.setCellValueFactory(nameCellValueFactory);
        return names;
    }

    private TableColumn<User, Integer> getTotalScoreColumn() {
        TableColumn<User, Integer> totalScore =  new TableColumn<>("Total Score");
        PropertyValueFactory<User, Integer> totalScoreCellValueFactory = new PropertyValueFactory<>("totalScore");
        totalScore.setCellValueFactory(totalScoreCellValueFactory);
        return totalScore;
    }

    private TableColumn<User, Integer> getWinsColumn() {
        TableColumn<User, Integer> wins =  new TableColumn<>("Wins");
        PropertyValueFactory<User, Integer> winsCellValueFactory = new PropertyValueFactory<>("wins");
        wins.setCellValueFactory(winsCellValueFactory);
        return wins;
    }

    private TableColumn<User, Integer> getLosesColumn() {
        TableColumn<User, Integer> loses =  new TableColumn<>("Loses");
        PropertyValueFactory<User, Integer> losesCellValueFactory = new PropertyValueFactory<>("loses");
        loses.setCellValueFactory(losesCellValueFactory);
        return loses;
    }

    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(60, 60, 60, 60));
        gridPane.setHgap(30);
        gridPane.setVgap(30);
        return gridPane;
    }
}
