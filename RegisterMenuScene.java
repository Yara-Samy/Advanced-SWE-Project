import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class RegisterMenuScene extends CustomScene {

    private TextField usernameField;
    private PasswordField passwordField;
    private TextField phoneNumberField;
    private TextField emailField;
    private TextField drivingLicenceField;
    private TextField nationalIdField;
    private RadioButton isCustomer;
    private RadioButton isDriver;
    private Button registerButton;
    private Label resultLabel;
    private Button backButton;

    public RegisterMenuScene(Pane pane, Stage stage, double width, double height) {
        super(pane, stage, width, height);
        initializeComponents();
        show();
    }

    @Override
    public void initializeComponents() {
        usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-font-size: 24");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-font-size: 24");

        phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Phone number");
        phoneNumberField.setStyle("-fx-font-size: 24");

        emailField = new TextField();
        emailField.setPromptText("Email (Optional)");
        emailField.setStyle("-fx-font-size: 24");

        drivingLicenceField = new TextField();
        drivingLicenceField.setPromptText("Licence number");
        drivingLicenceField.setStyle("-fx-font-size: 24");
        drivingLicenceField.visibleProperty().set(false);

        nationalIdField = new TextField();
        nationalIdField.setPromptText("National ID");
        nationalIdField.setStyle("-fx-font-size: 24");
        nationalIdField.visibleProperty().set(false);

        ToggleGroup toggleGroup = new ToggleGroup();

        isCustomer = new RadioButton("Register as customer");
        isCustomer.setStyle("-fx-font-size: 14");
        isCustomer.setToggleGroup(toggleGroup);
        isCustomer.setSelected(true);
        isCustomer.setOnAction(this::toggleFields);

        isDriver = new RadioButton("Register as driver");
        isDriver.setStyle("-fx-font-size: 14");
        isDriver.setToggleGroup(toggleGroup);
        isDriver.setOnAction(this::toggleFields);

        registerButton = new Button("Register");
        registerButton.setStyle("-fx-font-size: 24");
        registerButton.setOnAction(actionEvent -> register());

        resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 18");

        backButton = new Button("<--");
        backButton.setStyle("-fx-font-size: 20");
        backButton.setOnAction(actionEvent -> parentStage.setScene(new MainMenuScene(new BorderPane(), parentStage, this.getWidth(),this.getHeight())));
    }

    @Override
    public void show() {
        BorderPane borderPane = (BorderPane) layout;

        HBox hBox1 = new HBox();
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setSpacing(30);
        hBox1.getChildren().addAll(usernameField, passwordField);

        HBox hBox2 = new HBox();
        hBox2.setAlignment(Pos.CENTER);
        hBox2.setSpacing(30);
        hBox2.getChildren().addAll(phoneNumberField, emailField);

        HBox hBox3 = new HBox();
        hBox3.setAlignment(Pos.CENTER);
        hBox3.setSpacing(30);
        hBox3.getChildren().addAll(drivingLicenceField, nationalIdField);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(100, 0, 0, 0));
        vBox.getChildren().addAll(hBox1, hBox2, hBox3, isCustomer, isDriver, registerButton, resultLabel);

        HBox hBox4 = new HBox();
        hBox4.getChildren().add(backButton);

        borderPane.setCenter(vBox);
        borderPane.setBottom(hBox4);
    }

    public void register() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String phoneNumber = phoneNumberField.getText();
        String email = emailField.getText();
        String licenceNumber = drivingLicenceField.getText();
        String nationalID = nationalIdField.getText();

        if (isFieldsEmpty()) {
            for (TextField textField : new TextField[]{usernameField, passwordField, phoneNumberField, drivingLicenceField, nationalIdField}) {
                if (textField.getText().equals(""))
                    textField.setStyle("-fx-font-size: 24;-fx-border-color: red");
                else
                    textField.setStyle("-fx-font-size: 24");
            }
            resultLabel.setTextFill(Color.web("red"));
            resultLabel.setText("Please fill the required fields");
            return;
        }
        User user = isCustomer.isSelected() ? new Customer(username, password, phoneNumber, email.isEmpty() ? null : email) : new Driver(username, password, phoneNumber, email.isEmpty() ? null : email, licenceNumber, nationalID);
        int registerStatus = user.register();
        switch (registerStatus) {
            case 0:
                resultLabel.setTextFill(Color.web("red"));
                resultLabel.setText("Username is taken");
                break;
            case -1:
                resultLabel.setTextFill(Color.web("red"));
                resultLabel.setText("Duplicate national ID");
                break;
            case 1:
                resultLabel.setTextFill(Color.web("green"));
                resultLabel.setText("Registered successfully!");
                break;
        }
    }

    public boolean isFieldsEmpty() {
        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || phoneNumberField.getText().isEmpty()) {
            return true;
        } else if (isDriver.isSelected() && (drivingLicenceField.getText().isEmpty() || nationalIdField.getText().isEmpty())) {
            return true;
        }
        return false;
    }

    public void toggleFields(ActionEvent actionEvent) {
        if (actionEvent.getSource() == isCustomer) {
            drivingLicenceField.visibleProperty().set(false);
            nationalIdField.visibleProperty().set(false);
        } else if (actionEvent.getSource() == isDriver) {
            drivingLicenceField.visibleProperty().set(true);
            nationalIdField.visibleProperty().set(true);
        }
    }
}
