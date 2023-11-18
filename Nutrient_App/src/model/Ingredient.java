package src.model;

public class Ingredient {
    private String name;
    private int quantity;
    private String unit;
    private User user;

    public Ingredient(String name, int quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = user.getUnits();
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setName(String name) {this.name = name;}

    public void setQuantity(int quantity) {this.quantity = quantity;}

}
