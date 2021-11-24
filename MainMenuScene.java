import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainMenuScene extends CustomScene {

    private Label welcomeLabel;
    private Button registerButton;
    private Button loginButton;

    public MainMenuScene(Pane pane, Stage stage, double width, double height) {
        super(pane, stage, width, height);
        initializeComponents();
        show();
    }

    @Override
    public void initializeComponents() {
        welcomeLabel = new Label("Welcome to Uber!");
        welcomeLabel.setStyle("-fx-font-size: 30px");

        registerButton = new Button("Register now!");
        registerButton.setStyle("-fx-font-size: 24");
        registerButton.setOnAction(actionEvent -> parentStage.setScene(new RegisterMenuScene(new BorderPane(), parentStage, this.getWidth(), this.getHeight())));

        loginButton = new Button("Login now!");
        loginButton.setStyle("-fx-font-size: 24");
        loginButton.setOnAction(actionEvent -> parentStage.setScene(new LoginMenuScene(new BorderPane(), parentStage, this.getWidth(), this.getHeight())));
    }

    @Override
    public void show() {
        BorderPane borderPane = (BorderPane) layout;

        HBox hBox = new HBox();
        hBox.setSpacing(50);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(registerButton, loginButton);

        VBox vBox = new VBox();
        vBox.setSpacing(100);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(welcomeLabel, hBox);

        borderPane.setCenter(vBox);
    }
}
