import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RatingBox {

    private static Spinner<Integer> ratingSpinner;
    private static Button ratingButton;
    private static int rating;
    private static Stage stage;
    private static boolean clicked;

    public static int display() {
        clicked = false;
        stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Select rating");
        stage.setResizable(false);

        ratingSpinner = new Spinner<>(1, 5, 1);
        ratingSpinner.setPromptText("Enter a rating (1:5)");

        ratingButton = new Button("Rate");
        ratingButton.setOnAction(actionEvent -> rate());

        VBox vBox = new VBox();
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(ratingSpinner, ratingButton);


        Scene scene = new Scene(vBox, 400, 100);
        stage.setScene(scene);

        stage.showAndWait();

        return clicked ? rating : 0;
    }

    private static void rate() {
        clicked = true;
        rating = ratingSpinner.getValue();
        stage.close();
    }
}
