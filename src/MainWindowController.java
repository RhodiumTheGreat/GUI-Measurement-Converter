import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.shape.SVGPath;

public class MainWindowController {
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
}
