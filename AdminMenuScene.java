import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminMenuScene extends CustomScene {

    Admin admin;
    private Label adminLabel;
    private Button listUsersButton;
    private Button listDriversButton;
    private Button signOutButton;

    public AdminMenuScene(Pane pane, Stage stage, double width, double height, Admin admin) {
        super(pane, stage, width, height);
        this.admin = admin;
        initializeComponents();
        show();
    }

    @Override
    public void initializeComponents() {
        adminLabel = new Label("Admin panel");
        adminLabel.setStyle("-fx-font-size: 30");

        listUsersButton = new Button("List all users");
        listUsersButton.setOnAction(actionEvent -> parentStage.setScene(new UsersListScene(new BorderPane(), parentStage, this.getWidth(), this.getHeight(), admin)));

        listDriversButton = new Button("List pending drivers");
        listDriversButton.setOnAction(actionEvent -> parentStage.setScene(new PendingDriversListScene(new BorderPane(), parentStage, this.getWidth(), this.getHeight(), admin)));

        signOutButton = new Button("Sign out");
        signOutButton.setOnAction(actionEvent -> parentStage.setScene(new MainMenuScene(new BorderPane(), parentStage, this.getWidth(), this.getHeight())));


        for (Button button : new Button[] {listUsersButton, listDriversButton, signOutButton}) {
            button.setStyle("-fx-font-size: 18");
        }


    }

    @Override
    public void show() {
        BorderPane borderPane = (BorderPane) layout;

        HBox hBox1 = new HBox();
        hBox1.setSpacing(30);
        hBox1.setAlignment(Pos.CENTER);
        hBox1.getChildren().addAll(listUsersButton, listDriversButton);

        VBox vBox = new VBox();
        vBox.setSpacing(30);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(adminLabel, hBox1);

        HBox hBox2 = new HBox();
        hBox2.getChildren().add(signOutButton);

        borderPane.setCenter(vBox);
        borderPane.setBottom(hBox2);
    }
}
