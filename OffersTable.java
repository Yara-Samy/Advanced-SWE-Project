import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.stream.Collectors;

public class OffersTable {

    private static Stage stage;
    private static TableColumn<Offer, String> driverColumn;
    private static TableColumn<Offer, String> driverRatingColumn;
    private static TableColumn<Offer, String> priceColumn;
    private static TableColumn<Offer, String> stateColumn;
    private static TableView<Offer> offerTable;
    private static Ride targetedRide;
    private static Button acceptButton;
    private static Button rateDriverButton;
    private static TableView<Ride> rideTable;
    private static Customer customer;

    public static void display(Ride targetedRide, TableView<Ride> rideTable, Customer customer) {
        OffersTable.targetedRide = targetedRide;
        OffersTable.rideTable = rideTable;
        OffersTable.customer = customer;

        stage = new Stage();
        stage.setTitle("Offers");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        driverColumn = new TableColumn<>("Driver");
        driverColumn.setCellValueFactory(new PropertyValueFactory<>("driver"));

        driverRatingColumn = new TableColumn<>("Driver Rating");
        driverRatingColumn.setCellValueFactory(new PropertyValueFactory<>("AvgRate"));

        priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        stateColumn = new TableColumn<>("State");
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));

        for (TableColumn<Offer, String> column : new TableColumn[] {driverColumn, driverRatingColumn, priceColumn, stateColumn}) {
            column.setMinWidth(100);
            column.setStyle("-fx-alignment: CENTER;");
        }

        offerTable = new TableView<>();
        offerTable.setItems(getOffers());
        offerTable.getColumns().addAll(driverColumn, driverRatingColumn, priceColumn, stateColumn);

        acceptButton = new Button("Accept offer");
        acceptButton.setOnAction(actionEvent -> acceptOffer());

        rateDriverButton = new Button("Rate driver");
        rateDriverButton.setOnAction(actionEvent -> rateDriver());

        for (Button button : new Button[] {acceptButton, rateDriverButton})
            button.setStyle("-fx-font-size: 14");

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        hBox.setPadding(new Insets(20, 20, 20, 20));
        hBox.getChildren().addAll(acceptButton, rateDriverButton);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(offerTable, hBox);

        Scene scene = new Scene(vBox, 400, 300);
        stage.setScene(scene);

        stage.showAndWait();
    }

    private static ObservableList<Offer> getOffers() {
        ObservableList<Offer> offers = FXCollections.observableArrayList();
        offers.addAll(ApplicationSystem.getOffers().stream().filter(offer -> offer.getRide().getId() == targetedRide.getId()).collect(Collectors.toList()));
        return offers;
    }

    private static void acceptOffer() {
        Offer offer = offerTable.getSelectionModel().getSelectedItem();
        if (offer == null) {
            AlertBox.display("Alert", "Please select an offer first");
            return;
        }
        ApplicationSystem.acceptOffer(offer);
        offerTable.getItems().setAll(getOffers());
        rideTable.getItems().setAll(customer.getMyRideRequests());

    }

    private static void rateDriver() {
        Offer offer = offerTable.getSelectionModel().getSelectedItem();
        if (offer == null) {
            AlertBox.display("Error", "Please select a driver first");
            return;
        }
        int rating = RatingBox.display();
        if (rating == 0)
            return;
        customer.rate(offer.getDriver(), rating);
    }
}
