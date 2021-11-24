import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RequestRideBox {

    private static Stage stage;
    private static TextField sourceField;
    private static TextField destinationField;
    private static Button requestButton;
    private static String[] result;
    private static boolean clicked;

    public static String[] display() {
        clicked = false;
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Request a ride");

        sourceField = new TextField();
        sourceField.setPromptText("Source");

        destinationField = new TextField();
        destinationField.setPromptText("Destination");

        for (TextField field : new TextField[] {sourceField, destinationField}) {
            field.setStyle("-fx-font-size: 18");
            field.setMaxWidth(400);
        }

        requestButton = new Button("Request");
        requestButton.setStyle("-fx-font-size: 18");
        requestButton.setOnAction(actionEvent -> request());

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(sourceField, destinationField, requestButton);

        Scene scene = new Scene(vBox, 500, 200);
        stage.setScene(scene);

        stage.showAndWait();

        return clicked ? result : null;
    }

    private static void request() {
        if (sourceField.getText().isEmpty() || destinationField.getText().isEmpty()) {
            for (TextField field : new TextField[]{sourceField, destinationField}) {
                if (field.getText().isEmpty())
                    field.setStyle("-fx-font-size: 18;-fx-border-color: red");
                else
                    field.setStyle("-fx-font-size: 18;");
            }
            return;
        }
        clicked = true;
        result = new String[2];
        result[0] = sourceField.getText();
        result[1] = destinationField.getText();
        stage.close();
    }
}
