import java.util.ArrayList;

public class Type {
    private String type;
    private ArrayList<Conversion> conversions = new ArrayList<>();

    public Type(String type) {
        setType(type);
    }

    String getType() {
        return type;
    }

    private void setType(String type) {
        type = type.substring(0, 1).toUpperCase() + type.substring(1);
        this.type = type;
    }

    ArrayList<Conversion> getConversions() {
        return conversions;
    }

    void addConversion(Conversion conversion) {
        this.conversions.add(conversion);
    }
}
