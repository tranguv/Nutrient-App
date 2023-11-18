package src.model;

public class FoodItem {
    private int foodID;
    private int foodGroupID;
    private int foodSourceID;
    private String foodDescription;
    private String foodDescriptionF;
    private String foodGroupName;
    private String foodGroupNameF;
    private String foodSourceDescription;
    private String foodSourceDescriptionF;

    public FoodItem(int foodID, int foodGroupID, int foodSourceID, String foodDescription, String foodDescriptionF, String foodGroupName, String foodGroupNameF, String foodSourceDescription, String foodSourceDescriptionF) {
        this.foodID = foodID;
        this.foodGroupID = foodGroupID;
        this.foodSourceID = foodSourceID;
        this.foodDescription = foodDescription;
        this.foodDescriptionF = foodDescriptionF;
        this.foodGroupName = foodGroupName;
        this.foodGroupNameF = foodGroupNameF;
        this.foodSourceDescription = foodSourceDescription;
        this.foodSourceDescriptionF = foodSourceDescriptionF;
    }
    
    public int getFoodID() {
        return foodID;
    }

    public int getFoodGroupID() {
        return foodGroupID;
    }

    public int getFoodSourceID() {
        return foodSourceID;
    }

    public String getDescription() {
        return foodDescription;
    }

    public String getDescriptionF() {
        return foodDescriptionF;
    }

    public String getGroupName() {
        return foodGroupName;
    }

    public String getGroupNameF() {
        return foodGroupNameF;
    }

    public String getSourceDescription() {
        return foodSourceDescription;
    }

    public String getSourceDescriptionF() {
        return foodSourceDescriptionF;
    }
}
