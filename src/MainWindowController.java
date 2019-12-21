import javafx.animation.FillTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Window;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.GRAY;

public class MainWindowController {

    private double minHeight = 0;

    @FXML
    private BorderPane mainWindow;

    @FXML
    private ComboBox<Type> conversionTypeSelector = new ComboBox<>();

    @FXML
    private ComboBox<Conversion> conversionLeftSelector = new ComboBox<>();

    @FXML
    private ComboBox<Conversion> conversionRightSelector = new ComboBox<>();

    @FXML
    private TextField conversionLeftText;

    @FXML
    private TextField conversionRightText;

    @FXML
    private ComboBox<Conversion> conversionLeftSelectorDouble = new ComboBox<>();

    @FXML
    private ComboBox<Conversion> conversionRightSelectorDouble1 = new ComboBox<>();

    @FXML
    private ComboBox<Conversion> conversionRightSelectorDouble2 = new ComboBox<>();

    @FXML
    private TextField conversionLeftTextDouble;

    @FXML
    private TextField conversionRightTextDouble1;

    @FXML
    private TextField conversionRightTextDouble2;

    @FXML
    private SVGPath conversionArrowSingle;

    @FXML
    private SVGPath conversionArrowDouble;

    @FXML
    private Button conversionsDropdown;

    // Save feature not implemented
    // @FXML
    // private Button conversionsSave;

    public void initialize() throws FileNotFoundException {
        ConversionManager conversionManager = new ConversionManager();

        // Call ConversionManager to load the conversion
        conversionManager.initialise();

        // Assign each conversion type to local observable list
        ObservableList<Type> types = conversionManager.getTypes();

        for(Type x: types) {
            System.out.println("Listed type: " + x.getType());
        }

        // Add types to ComboBox
        conversionTypeSelector.setItems(types);
        conversionTypeSelector.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                // Code that runs when ComboBox is changed
                System.out.println("Selected Conversion Type: " + newValue.getType());

                // Single value conversions
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

                //Double value conversions
                conversionLeftSelectorDouble.setDisable(false);
                conversionLeftSelectorDouble.getSelectionModel().clearSelection();
                conversionLeftSelectorDouble.setPromptText("Select Measurement");

                conversionLeftTextDouble.setDisable(true);
                conversionLeftTextDouble.setPromptText("Please Select a Conversion");

                conversionRightSelectorDouble1.setDisable(false);
                conversionRightSelectorDouble1.getSelectionModel().clearSelection();
                conversionRightSelectorDouble1.setPromptText("Select 1st");

                conversionRightTextDouble1.setDisable(true);
                conversionRightTextDouble1.setPromptText("Please Select");

                conversionRightSelectorDouble2.setDisable(false);
                conversionRightSelectorDouble2.getSelectionModel().clearSelection();
                conversionRightSelectorDouble2.setPromptText("Select 2nd");

                conversionRightTextDouble2.setDisable(true);
                conversionRightTextDouble2.setPromptText("Please Select");

                loadListLeftSingle(newValue);
                loadListRightSingle(newValue);

                loadListLeftDouble(newValue);
                loadListRightDouble1(newValue);
                loadListRightDouble2(newValue);
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

    // ----- Load Lists of Conversions -----

    private void loadListLeftSingle(Type type) {
        // Add the unique observable list to a local one
        ObservableList<Conversion> conversions = conversionsByType(type);

        // Load the conversions into the ComboBoxes
        loadList(conversions, conversionLeftSelector, conversionLeftText);
    }

    private void loadListRightSingle(Type type) {
        // Add the unique observable list to a local one
        ObservableList<Conversion> conversions = conversionsByType(type);

        // Load the conversions into the ComboBoxes
        loadList(conversions, conversionRightSelector, conversionRightText);
    }

    private void loadListLeftDouble(Type type) {
        // Add the unique observable list to a local one
        ObservableList<Conversion> conversions = conversionsByType(type);

        // Load the conversions into the ComboBoxes
        loadList(conversions, conversionLeftSelectorDouble, conversionLeftTextDouble);
    }

    private void loadListRightDouble1(Type type) {
        // Add the unique observable list to a local one
        ObservableList<Conversion> conversions = conversionsByType(type);

        // Load the conversions into the ComboBoxes
        loadList(conversions, conversionRightSelectorDouble1, conversionRightTextDouble1);
    }

    private void loadListRightDouble2(Type type) {
        // Add the unique observable list to a local one
        ObservableList<Conversion> conversions = conversionsByType(type);

        // Load the conversions into the ComboBoxes
        loadList(conversions, conversionRightSelectorDouble2, conversionRightTextDouble2);
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
                conversionText.setPromptText("Type value");

                // Update values as if typed when the conversion is loaded..
                if (conversionSelector == conversionLeftSelector){
                    onRightTyped();
                }
                else if (conversionSelector == conversionRightSelector){
                    onLeftTyped();
                }
                else if (conversionSelector == conversionLeftSelectorDouble){
                    onRightDoubleTyped1();
                    onRightDoubleTyped2();
                }
                else if (conversionSelector == conversionRightSelectorDouble1){
                    onLeftDoubleTyped();
                    onRightDoubleTyped2();
                }
                else if (conversionSelector == conversionRightSelectorDouble2){
                    onLeftDoubleTyped();
                    onRightDoubleTyped1();
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

    // ----- When the user interacts with the keyboard ----- //
    @FXML
    protected void onLeftTyped(){
        runConversionSingle(conversionLeftSelector, conversionRightSelector, conversionLeftText, conversionRightText);
    }

    @FXML
    protected void onRightTyped(){
        runConversionSingle(conversionRightSelector, conversionLeftSelector, conversionRightText, conversionLeftText);
    }

    @FXML
    protected void onLeftDoubleTyped(){
        runConversionsDoubleLTR(conversionLeftSelectorDouble, conversionRightSelectorDouble1, conversionRightSelectorDouble2, conversionLeftTextDouble, conversionRightTextDouble1, conversionRightTextDouble2);
    }

    @FXML
    protected void onRightDoubleTyped1(){
        runConversionsDoubleRTL(conversionLeftSelectorDouble, conversionRightSelectorDouble1, conversionRightSelectorDouble2, conversionLeftTextDouble, conversionRightTextDouble1, conversionRightTextDouble2);
    }

    @FXML
    protected void onRightDoubleTyped2(){
        runConversionsDoubleRTL(conversionLeftSelectorDouble, conversionRightSelectorDouble2, conversionRightSelectorDouble1, conversionLeftTextDouble, conversionRightTextDouble2, conversionRightTextDouble1);
    }

    // Run the conversion for the single value conversions
    private void runConversionSingle(ComboBox<Conversion> conversionSelector1, ComboBox<Conversion> conversionSelector2, TextField conversionText1, TextField conversionText2){
        try{
            if (!conversionText1.getText().equals("") && conversionSelector2.getSelectionModel().getSelectedItem() != null) {
                double number = Double.parseDouble(conversionText1.getText());
                BigDecimal bd = BigDecimal.valueOf(number);

                number /= conversionSelector1.getSelectionModel().getSelectedItem().getMultiplier();
                number *= conversionSelector2.getSelectionModel().getSelectedItem().getMultiplier();

                number = round(number, bd.scale());

                conversionText2.setText(Double.toString(number));
            }

            else {
                conversionText2.setText("");
            }
            if (conversionSelector2.getSelectionModel().getSelectedItem() != null) {
                flashConversionArrow();
            }

        } catch (NumberFormatException e) {
            conversionText1.setText(conversionText1.getText().substring(0, conversionText1.getText().length()-1));
            conversionText1.positionCaret(conversionText1.getText().length());
        }
    }

    // Run the conversions for the double value conversions, from left to right.
    private void runConversionsDoubleLTR(ComboBox<Conversion> conversionLeftSelector, ComboBox<Conversion> conversionRightSelector1, ComboBox<Conversion> conversionRightSelector2, TextField conversionLeftText, TextField conversionRightText1, TextField conversionRightText2){
        try{
            if (!conversionLeftText.getText().equals("") && conversionRightSelector1.getSelectionModel().getSelectedItem() != null && conversionRightSelector2.getSelectionModel().getSelectedItem() != null) {
                double number1 = Double.parseDouble(conversionLeftText.getText());
                double number2;
                BigDecimal bd = BigDecimal.valueOf(number1);

                // The way that this conversion works is inefficient due to repeating processes, however it works.
                // First it converts the left value to the first right value
                number1 /= conversionLeftSelector.getSelectionModel().getSelectedItem().getMultiplier();
                number1 *= conversionRightSelector1.getSelectionModel().getSelectedItem().getMultiplier();
                System.out.println(number1);
                // Then it takes the decimal of that result by subtracting the whole number rounded down
                number2 = number1 - (int) number1;
                System.out.println(number2);
                // Then it converts that from the first right value to the second right value
                number2 /= conversionRightSelector1.getSelectionModel().getSelectedItem().getMultiplier();
                number2 *= conversionRightSelector2.getSelectionModel().getSelectedItem().getMultiplier();
                System.out.println(number2);
                // The numbers are adequately rounded, with the first being rounded to it's whole
                number1 = (int) number1;
                number2 = round(number2, bd.scale());
                // The final converted numbers are displayed.
                conversionRightText1.setText(Double.toString(number1));
                conversionRightText2.setText(Double.toString(number2));
            }

            else {
                conversionRightText1.setText("");
                conversionRightText2.setText("");
            }
            if (conversionRightSelector1.getSelectionModel().getSelectedItem() != null || conversionRightSelector1.getSelectionModel().getSelectedItem() != null) {
                flashConversionArrowDouble();
            }

        } catch (NumberFormatException e) {
            conversionLeftText.setText(conversionLeftText.getText().substring(0, conversionLeftText.getText().length()-1));
            conversionLeftText.positionCaret(conversionLeftText.getText().length());
        }
    }

    // Run the conversions for the double value conversions, from left to right.
    private void runConversionsDoubleRTL(ComboBox<Conversion> conversionLeftSelector, ComboBox<Conversion> conversionRightSelector1, ComboBox<Conversion> conversionRightSelector2, TextField conversionLeftText, TextField conversionRightText1, TextField conversionRightText2){
        try{
            if (!conversionRightText1.getText().equals("") && conversionLeftSelector.getSelectionModel().getSelectedItem() != null && conversionRightSelector2.getSelectionModel().getSelectedItem() != null) {
                double number1 = Double.parseDouble(conversionRightText1.getText());
                double number2 = Double.parseDouble(conversionRightText2.getText());
                BigDecimal bd = BigDecimal.valueOf(number1);

                // NOTE: conversionRightSelector1 and conversionRightTest1 are set to the conversion and text field of the value being edited by the user.
                // First it converts the two right values from their multipliers
                number1 /= conversionRightSelector1.getSelectionModel().getSelectedItem().getMultiplier();
                number2 /= conversionRightSelector2.getSelectionModel().getSelectedItem().getMultiplier();
                System.out.println(number1 + " " + number2);
                // Then it adds the values together and convects that to the left value
                number1 += number2;
                number1 *= conversionLeftSelector.getSelectionModel().getSelectedItem().getMultiplier();
                System.out.println(number1);
                // The number's adequately rounded
                number1 = round(number1, bd.scale());
                // The final converted numbers are displayed.
                conversionLeftText.setText(Double.toString(number1));
            }

            else {
                conversionLeftText.setText("");
            }
            if (conversionRightSelector1.getSelectionModel().getSelectedItem() != null || conversionRightSelector1.getSelectionModel().getSelectedItem() != null) {
                flashConversionArrowDouble();
            }

        } catch (NumberFormatException e) {
            conversionLeftText.setText(conversionLeftText.getText().substring(0, conversionLeftText.getText().length()-1));
            conversionLeftText.positionCaret(conversionLeftText.getText().length());
        }
    }
    // ----- ----- //

    // ----- Flash the arrows when the program runs a conversion ----- //
    private void flashConversionArrow() {
        conversionArrowSingle.setFill(BLACK);
        FillTransition ft = new FillTransition(Duration.millis(500), conversionArrowSingle, BLACK, GRAY);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    private void flashConversionArrowDouble() {
        conversionArrowDouble.setFill(BLACK);
        FillTransition ft = new FillTransition(Duration.millis(500), conversionArrowDouble, BLACK, GRAY);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }
    // ----- ----- //

    @FXML
    private void dropDown(){
        Window stage = mainWindow.getScene().getWindow();

        if (minHeight == 0){
            minHeight = stage.getHeight();
        }

        if (stage.getHeight() == minHeight){
            stage.setHeight(900);
            conversionsDropdown.setText("^");
        }
        else {
            stage.setHeight(minHeight);
            conversionsDropdown.setText("V");
        }
    }

    @FXML
    private void saveConversion(){
        // Saving not implemented
    }

    private static double round(double value, int places) {
        if (places < 2) places = 2;

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
