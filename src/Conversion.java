public class Conversion {
    private Type type;
    private String name;
    private double multiplier;

    public Conversion(Type type){
        setType(type);
    }

    Type getType() {
        return type;
    }

    private void setType(Type type) {
        this.type = type;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    double getMultiplier() {
        return multiplier;
    }

    void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }
}
