package src.model;

import java.util.List;

import src.server.DataServices.MealQueries;

import java.util.ArrayList;

public class Meal {
    private int mealId;
    private MealType type; // MealType is enum
    private List<Ingredient> ingredients;


    public Meal() {}


    public Meal(MealType type) {
        this.type = type;
        this.ingredients = new ArrayList<>();
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

    //GET INGREDIENTS FROM DATABASE BY MEAL ID
    public void getIngredientsFromDB() {
        this.ingredients = MealQueries.getIngredientsFromMealID(this.mealId);
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public void setType(MealType type) {
        this.type = type;
    }


    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredients(List<Ingredient> ingredient) {
        this.ingredients.addAll(ingredient);
    }
}
