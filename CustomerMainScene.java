import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CustomerMainScene extends CustomScene {

    Customer customer;
    TableView<Ride> rideTable;
    TableColumn<Ride, String> idColumn;
    TableColumn<Ride, String> sourceColumn;
    TableColumn<Ride, String> destinationColumn;
    TableColumn<Ride, String> stateColumn;
    Button requestRideButton;
    Button offersButton;
    Button signOutButton;

    public CustomerMainScene(Pane pane, Stage stage, double width, double height, Customer customer) {
        super(pane, stage, width, height);
        this.customer = customer;
        initializeComponents();
        show();
    }

    @Override
    public void initializeComponents() {
        idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        sourceColumn = new TableColumn<>("Source");
        sourceColumn.setCellValueFactory(new PropertyValueFactory<>("source"));

        destinationColumn = new TableColumn<>("Destination");
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));

        stateColumn = new TableColumn<>("State");
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));

        for (TableColumn<Ride, String> column : new TableColumn[]{idColumn, sourceColumn, destinationColumn, stateColumn}) {
            column.setMinWidth(100);
            column.setStyle("-fx-alignment: CENTER;");
        }

        rideTable = new TableView<>();
        rideTable.setItems(getRides());
        rideTable.getColumns().addAll(idColumn, sourceColumn, destinationColumn, stateColumn);

        requestRideButton = new Button("Request a new ride");
        requestRideButton.setOnAction(actionEvent -> requestRide());

        offersButton = new Button("Show offers for a ride");
        offersButton.setOnAction(actionEvent -> showOffers());

        for (Button button : new Button[] {requestRideButton, offersButton})
            button.setStyle("-fx-font-size: 18");

        requestRideButton.setMinWidth(offersButton.getWidth());

        signOutButton = new Button("Sign out");
        signOutButton.setStyle("-fx-font-size: 20");
        signOutButton.setOnAction(actionEvent -> parentStage.setScene(new MainMenuScene(new BorderPane(), parentStage, this.getWidth(), this.getHeight())));

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
        buttons.setPadding(new Insets(200, 0, 0, 80));
        buttons.getChildren().addAll(requestRideButton, offersButton);

        borderPane.setLeft(vBox);
        borderPane.setCenter(buttons);
    }

    public ObservableList<Ride> getRides() {
        ObservableList<Ride> rides = FXCollections.observableArrayList();
        rides.addAll(customer.getMyRideRequests());
        return rides;
    }

    public void requestRide() {
        String[] result = RequestRideBox.display();
        if (result == null)
            return;
        String source = result[0];
        String destination = result[1];

        Ride ride = new Ride(customer, source, destination);
        ApplicationSystem.addRide(ride);
        rideTable.getItems().add(ride);
        AlertBox.display("Ride request", "Your request is submitted, please wait for drivers' offers.");
    }

    public void showOffers() {
        Ride selectedRide = rideTable.getSelectionModel().getSelectedItem();
        if (selectedRide == null) {
            AlertBox.display("Error", "Please select a ride first");
            return;
        }
        OffersTable.display(selectedRide, rideTable, customer);
    }
}
