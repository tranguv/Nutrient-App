package src.model;

import java.util.ArrayList;
import java.util.Date;

public class DateLog {
    private int userID;
    private int dateLogId;
    private Date date;
    private ArrayList<Meal> meals;
    private ArrayList<Exercise> exercises;


    public DateLog() {}

    public DateLog(int userID, int dateLogId, Date date, ArrayList<Meal> meals, ArrayList<Exercise> exercises) {
        this.userID = userID;

        this.dateLogId = dateLogId;
        this.date = date;
        this.meals = meals;
        this.exercises = exercises;
    }

    public int getUserID() {
        return userID;
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

    public void setUserID(int userID) {
        this.userID = userID;
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
