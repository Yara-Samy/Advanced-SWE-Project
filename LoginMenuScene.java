import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;

public class LoginMenuScene extends CustomScene {

    private TextField usernameField;
    private PasswordField passwordField;
    private RadioButton isCustomer;
    private RadioButton isDriver;
    private Button loginButton;
    private Label errorLabel;
    private Button backButton;

    public LoginMenuScene(Pane pane, Stage stage, double width, double height) {
        super(pane, stage, width, height);
        initializeComponents();
        show();
    }

    @Override
    public void initializeComponents() {
        usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(400);
        usernameField.setStyle("-fx-font-size: 24");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(400);
        passwordField.setStyle("-fx-font-size: 24");

        ToggleGroup toggleGroup = new ToggleGroup();
        isCustomer = new RadioButton("Login as customer");
        isCustomer.setToggleGroup(toggleGroup);
        isCustomer.setSelected(true);
        isCustomer.setStyle("-fx-font-size: 14");

        isDriver = new RadioButton("Login as driver");
        isDriver.setToggleGroup(toggleGroup);
        isDriver.setStyle("-fx-font-size: 14");

        loginButton = new Button("Login");
        loginButton.setStyle("-fx-font-size: 24");

        loginButton.setOnAction(actionEvent -> parentStage.getScene());
        loginButton.setOnAction(actionEvent -> login());

        errorLabel = new Label();
        errorLabel.setStyle("-fx-font-size: 24;");
        errorLabel.setTextFill(Color.web("red"));


        backButton = new Button("<--");
        backButton.setStyle("-fx-font-size: 20");
        backButton.setOnAction(actionEvent -> parentStage.setScene(new MainMenuScene(new BorderPane(), parentStage, this.getWidth(), this.getHeight())));
    }

    @Override
    public void show() {
        BorderPane borderPane = (BorderPane) layout;

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(100, 0, 0, 0));
        vBox.getChildren().addAll(usernameField, passwordField, isCustomer, isDriver, loginButton, errorLabel);

        HBox hBox = new HBox();
        hBox.getChildren().add(backButton);


        borderPane.setCenter(vBox);
        borderPane.setBottom(hBox);
    }

    public void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        Admin admin = new Admin(username, password);
        if (admin.login()) {
            parentStage.setScene(new AdminMenuScene(new BorderPane(), parentStage, this.getWidth(), this.getHeight(), admin));
            return;
        }
        User user;
        user = isCustomer.isSelected() ? new Customer(username, password) : new Driver(username, password);
        int loginStatus = user.login();
        if (loginStatus == 0)
            errorLabel.setText("Incorrect username or password");
        else if (loginStatus == 1 && isCustomer.isSelected()) {
            parentStage.setScene(new CustomerMainScene(new BorderPane(), parentStage, this.getWidth(), this.getHeight(), (Customer) ApplicationSystem.getUsers().stream().filter(user1 -> user1.getUsername().equalsIgnoreCase(username)).findFirst().get()));
        } else if (loginStatus == 1 && isDriver.isSelected()) {
            parentStage.setScene(new DriverMainScene(new BorderPane(), parentStage, this.getWidth(), this.getHeight(), (Driver) ApplicationSystem.getUsers().stream().filter(user1 -> user1.getUsername().equalsIgnoreCase(username)).findFirst().get()));
        } else if (loginStatus == -1) {
            errorLabel.setText("Account is suspended");
        } else if (loginStatus == -2) {
            errorLabel.setText("Your account is not verified yet");
        }
        usernameField.clear();
        passwordField.clear();
    }
}
