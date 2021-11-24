import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class CustomScene extends Scene {

    Pane layout;
    Stage parentStage;

    public CustomScene(Pane pane, Stage stage, double width, double height) {
        super(pane, width, height);
        this.layout = pane;
        this.parentStage = stage;
    }

    public abstract void initializeComponents();
    public abstract void show();
}
