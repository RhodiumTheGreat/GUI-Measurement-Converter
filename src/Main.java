import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mainwindow.fxml"));
        stage.getIcons().add(new Image("file:media/icon.png"));
        stage.setTitle("Measurement Converter");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    /*
    public void init() throws Exception {

    }
    */

    public static void main(String[] args) {
        launch(args);
    }
}
