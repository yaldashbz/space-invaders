import controller.UserController;
import javafx.application.Application;
import javafx.stage.Stage;
import view.MainMenu;
import view.Menu;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        UserController.getInstance().initializePlayers();
        Menu.setWindow(primaryStage);
        MainMenu mainMenu = new MainMenu(null);
        mainMenu.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
