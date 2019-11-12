import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.shape.SVGPath;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MainWindowController {

    private ConversionManager conversionManager;

    @FXML
    private ComboBox conversionTypeSelector;

    @FXML
    private ComboBox leftConversionSelector;

    @FXML
    private ComboBox rightConversionSelector;

    @FXML
    private TextField leftConversionText;

    @FXML
    private TextField rightConversionText;

    @FXML
    private SVGPath conversionArrow;

    public void initialize() throws FileNotFoundException {
        conversionManager = new ConversionManager();

        // Call ConversionManager to load the conversion
        conversionManager.initialise();

        // Assign each conversion type

    }

    private ArrayList<Conversion> conversionsByType(String type){
        /* TODO
        *   Take the type of conversion and create a unique list of conversions to be used in the left and right ComboBox
        */
        return null;
    }
}
