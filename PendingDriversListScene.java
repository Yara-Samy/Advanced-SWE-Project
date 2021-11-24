import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PendingDriversListScene extends CustomScene {

    private Admin admin;
    TableView<Driver> driverTable;
    TableColumn<Driver, String> usernameColumn;
    TableColumn<Driver, String> passwordColumn;
    TableColumn<Driver, String> phoneNumberColumn;
    TableColumn<Driver, String> emailColumn;
    TableColumn<Driver, String> drivingLicenceColumn;
    TableColumn<Driver, String> nationalIdColumn;
    TableColumn<Driver, String> averageRatingColumn;
    TableColumn<Driver, String> isVerifiedColumn;
    Button verifyButton;
    Button backButton;
    Button signOutButton;

    public PendingDriversListScene(Pane pane, Stage stage, double width, double height, Admin admin) {
        super(pane, stage, width, height);
        this.admin = admin;
        initializeComponents();
        show();
    }

    @Override
    public void initializeComponents() {
        usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        passwordColumn = new TableColumn<>("Password");
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        phoneNumberColumn = new TableColumn<>("Phone Number");
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        drivingLicenceColumn = new TableColumn<>("Licence Number");
        drivingLicenceColumn.setCellValueFactory(new PropertyValueFactory<>("drivingLicence"));

        nationalIdColumn = new TableColumn<>("National ID");
        nationalIdColumn.setCellValueFactory(new PropertyValueFactory<>("nationalID"));

        averageRatingColumn = new TableColumn<>("Average Rating");
        averageRatingColumn.setCellValueFactory(new PropertyValueFactory<>("avgRate"));

        isVerifiedColumn = new TableColumn<>("Is Verified");
        isVerifiedColumn.setCellValueFactory(new PropertyValueFactory<>("isVerified"));

        for (TableColumn<Driver, String> column : new TableColumn[] {usernameColumn, passwordColumn, phoneNumberColumn, emailColumn, drivingLicenceColumn, nationalIdColumn, averageRatingColumn, isVerifiedColumn}) {
            column.setMinWidth(100);
            column.setStyle("-fx-alignment: CENTER;");
        }

        driverTable = new TableView<>();
        driverTable.setItems(getDrivers());
        driverTable.setMaxWidth(500);
        driverTable.getColumns().addAll(usernameColumn, passwordColumn, phoneNumberColumn, emailColumn, drivingLicenceColumn, nationalIdColumn, averageRatingColumn, isVerifiedColumn);

        verifyButton = new Button("Verify driver");
        verifyButton.setOnAction(actionEvent -> verify());

        backButton = new Button("<--");
        backButton.setOnAction(actionEvent -> parentStage.setScene(new AdminMenuScene(new BorderPane(), parentStage, this.getWidth(), this.getHeight(), admin)));

        signOutButton = new Button("Sign out");
        signOutButton.setOnAction(actionEvent -> parentStage.setScene(new MainMenuScene(new BorderPane(), parentStage, this.getWidth(), this.getHeight())));


        for (Button button : new Button[] {verifyButton, backButton, signOutButton})
            button.setStyle("-fx-font-size: 18");
    }

    @Override
    public void show() {
        BorderPane borderPane = (BorderPane) layout;

        VBox vBox = new VBox();
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(driverTable);

        HBox hBox1 = new HBox();
        hBox1.setSpacing(20);
        hBox1.getChildren().addAll(backButton, signOutButton);

        HBox hBox2 = new HBox();
        hBox2.setAlignment(Pos.CENTER);
        hBox2.setPadding(new Insets(30, 0, 0, 0));
        hBox2.getChildren().add(verifyButton);

        borderPane.setCenter(vBox);
        borderPane.setBottom(hBox1);
        borderPane.setTop(hBox2);
    }

    private ObservableList<Driver> getDrivers() {
        ObservableList<Driver> drivers = FXCollections.observableArrayList();
        drivers.setAll(admin.listAllPendingDrivers());
        return drivers;
    }

    private void verify() {
        Driver selectedDriver = driverTable.getSelectionModel().getSelectedItem();
        if (selectedDriver == null) {
            AlertBox.display("Error", "Please select a driver first");
            return;
        }
        admin.verify(selectedDriver.getUsername());
        driverTable.getItems().setAll(getDrivers());
    }
}
