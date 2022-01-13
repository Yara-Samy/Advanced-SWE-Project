import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class AddAreaBox {

    private static Stage stage;
    private static TextField areaField;
    private static Button addButton;
    private static String result;
    private static boolean clicked;

    public static String display() {
        clicked = false;
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Request a ride");

        areaField = new TextField();
        areaField.setPromptText("Source");
        areaField.setMaxWidth(400);
        areaField.setStyle("-fx-font-size: 18;");

        addButton = new Button("Add area");
        addButton.setStyle("-fx-font-size: 18");
        addButton.setOnAction(actionEvent -> addArea());

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(areaField, addButton);

        Scene scene = new Scene(vBox, 500, 200);
        stage.setScene(scene);

        stage.showAndWait();

        return clicked ? result : null;
    }

    private static void addArea() {
        if (areaField.getText().isEmpty()) {
            if (areaField.getText().isEmpty())
                areaField.setStyle("-fx-font-size: 18;-fx-border-color: red");
            else
                areaField.setStyle("-fx-font-size: 18;");
            return;
        }
        clicked = true;
        result = areaField.getText();
        stage.close();
    }
}
