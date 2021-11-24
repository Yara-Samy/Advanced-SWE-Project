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

public class UsersListScene extends CustomScene {

    Admin admin;
    TableView<User> usersTable;
    TableColumn<User, String> usernameColumn;
    TableColumn<User, String> passwordColumn;
    TableColumn<User, String> phoneNumberColumn;
    TableColumn<User, String> emailColumn;
    TableColumn<User, String> isSuspendedColumn;
    Button suspendButton;
    Button backButton;
    Button signOutButton;

    public UsersListScene(Pane pane, Stage stage, double width, double height, Admin admin) {
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

        isSuspendedColumn = new TableColumn<>("Is suspended");
        isSuspendedColumn.setCellValueFactory(new PropertyValueFactory<>("isSuspended"));

        for (TableColumn<User, String> column : new TableColumn[] {usernameColumn, passwordColumn, phoneNumberColumn, emailColumn, isSuspendedColumn}) {
            column.setMinWidth(100);
            column.setStyle("-fx-alignment: CENTER;");
        }

        usersTable = new TableView<>();
        usersTable.setMaxWidth(500);
        usersTable.setItems(getUsers());
        usersTable.getColumns().addAll(usernameColumn, passwordColumn, phoneNumberColumn, emailColumn, isSuspendedColumn);

        suspendButton = new Button("Suspend user");
        suspendButton.setOnAction(actionEvent -> suspend());

        backButton = new Button("<--");
        backButton.setOnAction(actionEvent -> parentStage.setScene(new AdminMenuScene(new BorderPane(), parentStage, this.getWidth(), this.getHeight(), admin)));

        signOutButton = new Button("Sign out");
        signOutButton.setOnAction(actionEvent -> parentStage.setScene(new MainMenuScene(new BorderPane(), parentStage, this.getWidth(), this.getHeight())));

        for (Button button : new Button[] {suspendButton, backButton, signOutButton}) {
            button.setStyle("-fx-font-size: 18");
        }

    }

    @Override
    public void show() {
        BorderPane borderPane = (BorderPane) layout;

        VBox vBox = new VBox();
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(usersTable);

        HBox hBox1 = new HBox();
        hBox1.setSpacing(20);
        hBox1.getChildren().addAll(backButton, signOutButton);

        HBox hBox2 = new HBox();
        hBox2.setAlignment(Pos.CENTER);
        hBox2.setPadding(new Insets(30, 0, 0, 0));
        hBox2.getChildren().add(suspendButton);

        borderPane.setCenter(vBox);
        borderPane.setBottom(hBox1);
        borderPane.setTop(hBox2);
    }

    private ObservableList<User> getUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();
        users.addAll(ApplicationSystem.getUsers());
        return users;
    }

    private void suspend() {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            AlertBox.display("Error", "Please select a user first");
            return;
        }
        if (selectedUser.getIsSuspended() == 1) {
            AlertBox.display("Alert", "User is already suspended");
            return;
        }
        admin.suspend(selectedUser);
        usersTable.getItems().setAll(getUsers());
    }
}
