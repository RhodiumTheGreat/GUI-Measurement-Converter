import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private ConversionManager conversionManager;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mainwindow.fxml"));
        stage.setTitle("Measurement Converter");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void init() throws Exception {
        conversionManager = new ConversionManager();
        conversionManager.initialise();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
