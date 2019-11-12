import java.util.ArrayList;

public class Type {
    String type;
    ArrayList<Conversion> conversions = new ArrayList<>();

    public Type(String type) {
        setType(type);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        type = type.substring(0, 1).toUpperCase() + type.substring(1);
        this.type = type;
    }

    public ArrayList<Conversion> getConversions() {
        return conversions;
    }

    public void addConversion(Conversion conversion) {
        this.conversions.add(conversion);
    }
}
