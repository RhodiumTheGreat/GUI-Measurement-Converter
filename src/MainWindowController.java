import javafx.animation.FillTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.GRAY;

public class MainWindowController {

    private ConversionManager conversionManager;
    private ObservableList<Type> types;
    private double minHeight = 217;

    @FXML
    private BorderPane mainWindow;

    @FXML
    private ComboBox<Type> conversionTypeSelector = new ComboBox<>();;

    @FXML
    private ComboBox<Conversion> conversionLeftSelector = new ComboBox<>();

    @FXML
    private ComboBox<Conversion> conversionRightSelector = new ComboBox<>();

    @FXML
    private TextField conversionLeftText;

    @FXML
    private TextField conversionRightText;

    @FXML
    private SVGPath conversionArrow;

    @FXML
    private Button conversionsDropDown;

    public void initialize() throws FileNotFoundException {
        //minHeight = mainWindow.getScene().getWindow().getHeight();
        conversionManager = new ConversionManager();

        // Call ConversionManager to load the conversion
        conversionManager.initialise();

        // Assign each conversion type to local observable list
        types = conversionManager.getTypes();

        for(Type x: types) {
            System.out.println("Listed type: " + x.getType());
        }

        // Add types to ComboBox
        conversionTypeSelector.setItems(types);
        conversionTypeSelector.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                // Code that runs when ComboBox is changed
                System.out.println("Selected Conversion Type: " + newValue.getType());

                conversionLeftSelector.setDisable(false);
                conversionLeftSelector.getSelectionModel().clearSelection();
                conversionLeftSelector.setPromptText("Select Measurement");

                conversionLeftText.setDisable(true);
                conversionLeftText.setPromptText("Please Select a Conversion");

                conversionRightSelector.setDisable(false);
                conversionRightSelector.getSelectionModel().clearSelection();
                conversionRightSelector.setPromptText("Select Measurement");

                conversionRightText.setDisable(true);
                conversionRightText.setPromptText("Please Select a Conversion");

                loadListLeft(newValue);
                loadListRight(newValue);
            }
        });

        // Show the Type's type rather than a reference to the object
        conversionTypeSelector.setConverter(new StringConverter<>() {
            @Override
            public String toString(Type type) {
                return type.getType();
            }

            @Override
            public Type fromString(String string) {
                return null;
            }
        });

    }

    private void loadListLeft(Type type) {
        // Add the unique observable list to a local one
        ObservableList<Conversion> conversions = conversionsByType(type);

        // Load the conversions into the ComboBoxes
        loadList(conversions, conversionLeftSelector, conversionLeftText);
    }

    private void loadListRight(Type type) {
        // Add the unique observable list to a local one
        ObservableList<Conversion> conversions = conversionsByType(type);

        // Load the conversions into the ComboBoxes
        loadList(conversions, conversionRightSelector, conversionRightText);
    }

    private ObservableList<Conversion> conversionsByType(Type type){
        ObservableList<Conversion> conversions = FXCollections.observableArrayList();

        conversions.addAll(type.getConversions());
        return conversions;
    }

    private void loadList(ObservableList<Conversion> conversions, ComboBox<Conversion> conversionSelector, TextField conversionText){
        // Load the conversions into the ComboBoxes
        conversionSelector.setItems(conversions);
        conversionSelector.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("Selected Conversion Value: " + newValue.getName());
                conversionText.setDisable(false);
                conversionText.setPromptText("Type your conversion");

                if (conversionSelector == conversionLeftSelector){
                    onRightTyped();
                }
                else{
                    onLeftTyped();
                }
            }
        });

        // Show the Conversion's name rather than a reference to the object
        conversionSelector.setConverter(new StringConverter<>() {
            @Override
            public String toString(Conversion conversion) {
                return conversion.getName();
            }

            @Override
            public Conversion fromString(String string) {
                return null;
            }
        });
    }

    @FXML
    protected void onLeftTyped(){
        runConversion(conversionLeftSelector, conversionRightSelector, conversionLeftText, conversionRightText);
    }

    @FXML
    protected void onRightTyped(){
        runConversion(conversionRightSelector, conversionLeftSelector, conversionRightText, conversionLeftText);
    }

    private void runConversion(ComboBox<Conversion> conversionSelector1, ComboBox<Conversion> ConversionSelector2, TextField conversionText1, TextField conversionText2){
        try{
            if (!conversionText1.getText().equals("") && ConversionSelector2.getSelectionModel().getSelectedItem() != null) {
                Double number = Double.parseDouble(conversionText1.getText());

                number /= conversionSelector1.getSelectionModel().getSelectedItem().getMultiplier();
                number *= ConversionSelector2.getSelectionModel().getSelectedItem().getMultiplier();

                conversionText2.setText(number.toString());
            }

            else {
                conversionText2.setText("");
            }
            if (ConversionSelector2.getSelectionModel().getSelectedItem() != null) {
                flashConversionArrow();
            }

        } catch (NumberFormatException e) {
            conversionText1.setText(conversionText1.getText().substring(0, conversionText1.getText().length()-1));
            conversionText1.positionCaret(conversionText1.getText().length());
        }
    }

    private void flashConversionArrow() {
        conversionArrow.setFill(BLACK);
        FillTransition ft = new FillTransition(Duration.millis(500), conversionArrow, BLACK, GRAY);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    @FXML
    private void dropDown(){
        Window stage = mainWindow.getScene().getWindow();
        if (stage.getHeight() == minHeight){
            stage.setHeight(900);
            conversionsDropDown.setText("^");
        }
        else {
            stage.setHeight(minHeight);
            conversionsDropDown.setText("V");
        }
    }
}
