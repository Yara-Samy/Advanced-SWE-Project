import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RatingsBox {
    private static Driver driver;
    private static Stage stage;
    private static TableView<Rating> ratingTable;
    private static TableColumn<Rating, String> customerColumn;
    private static TableColumn<Rating, String> ratingColumn;

    public static void display(Driver driver) {
        RatingsBox.driver = driver;
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("My ratings");

        customerColumn = new TableColumn<>("Customer");
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        ratingColumn = new TableColumn<>("Rating");
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rate"));

        for (TableColumn<Rating, String> column : new TableColumn[] {customerColumn, ratingColumn}) {
            column.setMinWidth(200);
            column.setStyle("-fx-alignment: CENTER;");
        }

        ratingTable = new TableView<>();
        ratingTable.setItems(getRatings());
        ratingTable.getColumns().addAll(customerColumn, ratingColumn);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(30);
        vBox.getChildren().addAll(ratingTable);

        Scene scene = new Scene(vBox, 400, 400);
        stage.setScene(scene);
        stage.showAndWait();

    }

    private static ObservableList<Rating> getRatings() {
        ObservableList<Rating> ratings = FXCollections.observableArrayList();
        ratings.addAll(driver.listAllRatings());
        return ratings;
    }
}
