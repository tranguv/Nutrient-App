package src.model;

import java.util.ArrayList;
import java.util.Date;

import src.server.DataServices.MealQueries;

public class DateLog {
    private int userID;
    private int dateLogId;
    private Date date;
    private ArrayList<Meal> meals;
    private ArrayList<Exercise> exercises;


    public DateLog() {}

    public DateLog(int userID, Date date) {
        this.userID = userID;
        this.date = date;
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

    public void addMeals(ArrayList<Meal> meals) {
        this.meals.addAll(meals);
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
