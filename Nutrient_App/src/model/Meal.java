package src.model;

import java.util.List;
import java.util.ArrayList;

public class Meal {
    private int mealId;
    private MealType type; // MealType is enum
    private List<Ingredient> ingredients;


    public Meal() {}


    public Meal(int mealId, MealType type, List<Ingredient> ingredients) {

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



    public List<Ingredient> getIngredients() {

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
