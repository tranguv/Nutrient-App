package src.model;

public class Ingredient {
    private FoodItem foodItem;
    private String name;
    private double quantity;
    private String unit;

    public Ingredient(FoodItem item, double quantity, String unit) {
        this.foodItem = item;
        this.quantity = quantity;
        this.unit = unit;
    }

    public Ingredient(String name, double quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public String getName() {
        return name;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setName(String name) {this.name = name;}

    public void setQuantity(double quantity) {this.quantity = quantity;}

}
