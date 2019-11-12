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
        this.type = type;
    }

    public ArrayList<Conversion> getConversions() {
        return conversions;
    }

    public void setConversions(ArrayList<Conversion> conversions) {
        this.conversions = conversions;
    }
}
