package src.model;

public class FoodItem {
    private int foodID;
    private int foodGroupID;
    private String foodDescription;
    private String foodGroupName;

    public FoodItem(int foodID, int foodGroupID, String foodDescription, String foodGroupName) {
        this.foodID = foodID;
        this.foodGroupID = foodGroupID;
        this.foodDescription = foodDescription;
        this.foodGroupName = foodGroupName;
    }
    
    public int getFoodID() {
        return foodID;
    }
}
