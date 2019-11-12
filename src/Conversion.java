/* TODO
 *  Create a new type class to store all of the conversions to allow the use of an ObservableList in the ComboBoxes
 */

public class Conversion {
    Type type;
    String name;
    Double multiplier;

    public Conversion(Type type){
        setType(type);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
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
