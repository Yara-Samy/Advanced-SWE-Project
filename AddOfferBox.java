import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddOfferBox {
    private static Stage stage;
    private static TextField priceField;
    private static Button offerButton;
    private static double result;
    private static boolean clicked;

    public static double display() {
        clicked = false;
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add offer");

        priceField = new TextField();
        priceField.setStyle("-fx-font-size: 18");
        priceField.setMaxWidth(400);

        offerButton = new Button("Add offer");
        offerButton.setStyle("-fx-font-size: 18");
        offerButton.setOnAction(actionEvent -> offer());

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(30);
        vBox.getChildren().addAll(priceField, offerButton);

        Scene scene = new Scene(vBox, 500, 150);
        stage.setScene(scene);
        stage.showAndWait();

        return clicked ? result : 0;
    }

    private static void offer() {
        clicked = true;
        try {
            result = Double.parseDouble(priceField.getText());
            stage.close();
        } catch (NumberFormatException e) {
            clicked = false;
            priceField.setStyle("-fx-font-size: 18;-fx-border-color: red");
        }
    }
}
