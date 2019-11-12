import javafx.animation.FillTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
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

    public void initialize() throws FileNotFoundException {
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

        conversionLeftSelector.setItems(conversions);
        conversionLeftSelector.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("Selected Conversion Value: " + newValue.getName());
                conversionLeftText.setDisable(false);
                conversionLeftText.setPromptText("Type your conversion");
                onRightTyped();
            }
        });

        // Show the Conversion's name rather than a reference to the object
        conversionLeftSelector.setConverter(new StringConverter<>() {
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

    private void loadListRight(Type type) {
        // Add the unique observable list to a local one
        ObservableList<Conversion> conversions = conversionsByType(type);

        conversionRightSelector.setItems(conversions);
        conversionRightSelector.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("Selected Conversion Value: " + newValue.getName());
                conversionRightText.setDisable(false);
                conversionRightText.setPromptText("Type your conversion");
                onLeftTyped();
            }
        });

        // Show the Conversion's name rather than a reference to the object
        conversionRightSelector.setConverter(new StringConverter<>() {
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

    private ObservableList<Conversion> conversionsByType(Type type){
        /* TODO
         *   Take the type of conversion and create a unique observable list of conversions to be used in the left and right ComboBox
         */
        ObservableList<Conversion> conversions = FXCollections.observableArrayList();

        conversions.addAll(type.getConversions());
        return conversions;
    }

    @FXML
    protected void onLeftTyped(){
        try{
            if (!conversionLeftText.getText().equals("")) {
                Double number = Double.parseDouble(conversionLeftText.getText());

                number /= conversionLeftSelector.getSelectionModel().getSelectedItem().getMultiplier();
                number *= conversionRightSelector.getSelectionModel().getSelectedItem().getMultiplier();

                conversionRightText.setText(number.toString());
            }

            else {
                conversionRightText.setText("");
            }
            flashConversionArrow();

        } catch (NumberFormatException e) {
            conversionLeftText.setText(conversionLeftText.getText().substring(0, conversionLeftText.getText().length()-1));
            conversionLeftText.positionCaret(conversionLeftText.getText().length());
        }
    }

    @FXML
    protected void onRightTyped(){
        try{
            if (!conversionRightText.getText().equals("")) {
                Double number = Double.parseDouble(conversionRightText.getText());

                number /= conversionRightSelector.getSelectionModel().getSelectedItem().getMultiplier();
                number *= conversionLeftSelector.getSelectionModel().getSelectedItem().getMultiplier();

                conversionLeftText.setText(number.toString());
            }

            else {
                conversionLeftText.setText("");
            }
            flashConversionArrow();

        } catch (NumberFormatException e) {
            conversionRightText.setText(conversionRightText.getText().substring(0, conversionRightText.getText().length()-1));
            conversionRightText.positionCaret(conversionRightText.getText().length());
        }
    }

    private void flashConversionArrow() {
        conversionArrow.setFill(BLACK);
        FillTransition ft = new FillTransition(Duration.millis(500), conversionArrow, BLACK, GRAY);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }
}
