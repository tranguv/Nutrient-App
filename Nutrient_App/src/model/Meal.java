package src.model;

import java.util.ArrayList;

public class Meal {
    private int mealId;
    private MealType type; // MealType is enum
    private ArrayList<Ingredient> ingredients;

    public Meal() {}
    public Meal(int mealId, MealType type, ArrayList<Ingredient> ingredients) {
        this.mealId = mealId;
        this.type = type;
        this.ingredients = ingredients;
    }

    public int getMealId() {
        return mealId;
    }

    public MealType getType() {
        return type;
    }

    public String getMealTypeName() {
        if (this.type == MealType.DINNER) return MealType.DINNER.toString();
        else if (this.type == MealType.BREAKFAST) return MealType.BREAKFAST.toString();
        else if (this.type == MealType.LUNCH) return MealType.LUNCH.toString();
        else return MealType.SNACK.toString();
        }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public void setType(MealType type) {
        this.type = type;
    }


    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
