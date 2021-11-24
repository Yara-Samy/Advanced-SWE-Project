import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
    public static void display(String title, String message) {
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setResizable(false);

        Label label = new Label(message);
        label.setStyle("-fx-font-size: 14");
        label.setWrapText(true);

        Button okButton = new Button("OK");
        okButton.setMinWidth(100);
        okButton.setOnAction(actionEvent -> stage.close());

        VBox vBox = new VBox();
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(label, okButton);

        Scene scene = new Scene(vBox, 400, 100);
        stage.setScene(scene);

        stage.showAndWait();
    }
}
