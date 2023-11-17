package src.model;

public class Ingredient {
    private FoodItem foodItem;
    private String name;
    private int quantity;
    private String unit;

    public Ingredient(FoodItem item, int quantity, String unit) {
        this.foodItem = item;
        this.quantity = quantity;
        this.unit = unit;
    }

    public Ingredient(String name, int quantity, String unit) {
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

    public int getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }
}
