import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Scanner;

public class Main extends Application {
    public static void main(String[] args) {
        ApplicationSystem.init();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Uber");
        stage.setResizable(false);
        stage.setScene(new MainMenuScene(new BorderPane(), stage, 800, 600));
        stage.show();
    }
}
