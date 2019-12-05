import java.util.ArrayList;

public class Conversion {
    Type type;
    String name;
    ArrayList<Double> multiplier = new ArrayList<Double>();

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

    public double getMultiplier() {
        return multiplier.get(0);
    }

    public void setMultiplier(Double multiplier) {
        this.multiplier.add(multiplier);
    }
}
