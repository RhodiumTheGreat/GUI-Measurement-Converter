/* TODO
 *  Create a new type class to store all of the conversions to allow the use of an ObservableList in the ComboBoxes
 */

public class Conversion {
    String type;
    String name;
    Double multiplier;

    public Conversion(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(Double multiplier) {
        this.multiplier = multiplier;
    }
}
