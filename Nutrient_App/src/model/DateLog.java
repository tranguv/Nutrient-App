package src.model;

import java.util.ArrayList;
import java.util.Date;

public class DateLog {
    private String username;
    private int dateLogId;
    private Date date;
    private ArrayList<Meal> meals;
    private ArrayList<Exercise> exercises;

    public DateLog(String username, int dateLogId, Date date, ArrayList<Meal> meals, ArrayList<Exercise> exercises) {
        this.username = username;
        this.dateLogId = dateLogId;
        this.date = date;
        this.meals = meals;
        this.exercises = exercises;
    }

    public String getUsername() {
        return username;
    }

    public int getDateLogId() {
        return dateLogId;
    }

    public Date getDate() {
        return date;
    }

    public ArrayList<Meal> getMeals() {
        return meals;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDateLogId(int dateLogId) {
        this.dateLogId = dateLogId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMeals(ArrayList<Meal> meals) {
        this.meals = meals;
    }

    public void setExercises(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }

    public void addMeal(Meal meal) {
        meals.add(meal);
    }

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }

    public void removeMeal(Meal meal) {
        meals.remove(meal);
    }

    public void removeExercise(Exercise exercise) {
        exercises.remove(exercise);
    }

    public void clearMeals() {
        meals.clear();
    }

    public void clearExercises() {
        exercises.clear();
    }

    public void clearAll() {
        clearMeals();
        clearExercises();
    }

    // public double getCalories() {
    //     double calories = 0;
    //     for (Meal meal : meals) {
    //         calories += meal.getCalories();
    //     }
    //     return calories;
    // }
}
