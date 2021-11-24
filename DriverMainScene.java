import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DriverMainScene extends CustomScene {

    private Driver driver;
    TableView<Ride> rideTable;
    TableColumn<Ride, String> idColumn;
    TableColumn<Ride, String> customerColumn;
    TableColumn<Ride, String> sourceColumn;
    TableColumn<Ride, String> destinationColumn;
    TableColumn<Ride, String> stateColumn;

    Button offerButton;
    Button showOffersButton;
    Button showRatingsButton;
    Button addFavoriteButton;
    Button signOutButton;


    public DriverMainScene(Pane pane, Stage stage, double width, double height, Driver driver) {
        super(pane, stage, width, height);
        this.driver = driver;
        initializeComponents();
        show();
    }

    @Override
    public void initializeComponents() {
        idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        customerColumn = new TableColumn<>("Customer");
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        sourceColumn = new TableColumn<>("Source");
        sourceColumn.setCellValueFactory(new PropertyValueFactory<>("source"));

        destinationColumn = new TableColumn<>("Destination");
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));

        stateColumn = new TableColumn<>("State");
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));

        for (TableColumn<Ride, String> column : new TableColumn[]{idColumn, customerColumn, sourceColumn, destinationColumn, stateColumn}) {
            column.setMinWidth(100);
            column.setStyle("-fx-alignment: CENTER;");
        }

        rideTable = new TableView<>();
        rideTable.setItems(getRides());
        rideTable.getColumns().addAll(idColumn, customerColumn, sourceColumn, destinationColumn, stateColumn);

        offerButton = new Button("Add offer to a ride");
        offerButton.setOnAction(actionEvent -> offer());

        showRatingsButton = new Button("Show my ratings");
        showRatingsButton.setOnAction(actionEvent -> showRatings());

        addFavoriteButton = new Button("Add favorite area");
        addFavoriteButton.setOnAction(actionEvent -> addArea());

        signOutButton = new Button("Sign out");
        signOutButton.setOnAction(actionEvent -> parentStage.setScene(new MainMenuScene(new BorderPane(), parentStage, this.getWidth(), this.getHeight())));

        for (Button button : new Button[] {offerButton, showRatingsButton, signOutButton, addFavoriteButton}) {
            button.setStyle("-fx-font-size: 18");
        }
    }

    @Override
    public void show() {
        BorderPane borderPane = (BorderPane) layout;

        VBox vBox = new VBox();
        vBox.setSpacing(100);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.getChildren().addAll(rideTable, signOutButton);

        VBox buttons = new VBox();
        buttons.setSpacing(20);
        buttons.setPadding(new Insets(200, 0, 0, 40));
        buttons.getChildren().addAll(offerButton, showRatingsButton, addFavoriteButton);

        borderPane.setLeft(vBox);
        borderPane.setCenter(buttons);
    }

    private ObservableList<Ride> getRides() {
        ObservableList<Ride> rides = FXCollections.observableArrayList();
        rides.addAll(driver.getPossibleRides());
        return rides;
    }

    private void offer() {
        Ride selectedRide = rideTable.getSelectionModel().getSelectedItem();
        if (selectedRide == null) {
            AlertBox.display("Error", "Please select a ride first");
            return;
        }
        double price = AddOfferBox.display();
        if (price == 0)
            return;
        driver.offer(selectedRide, price);
    }

    private void showRatings() {
        RatingsBox.display(driver);
    }

    private void addArea() {
        String area = AddAreaBox.display();
        if (area == null)
            return;
        driver.addFavoriteArea(area);
        rideTable.getItems().setAll(driver.getPossibleRides());
    }
}
