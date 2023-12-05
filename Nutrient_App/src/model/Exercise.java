package src.model;

import java.sql.SQLException;

import src.server.DataServices.ExerciseQueries;

public class Exercise {
    private String exerciseType;
    private int duration;
    private Intensity intensity;

    private int id;
    private double caloriesBurnt;
    private String date;
    public Exercise(String exerciseType, int duration, Intensity intensity) {
        this.exerciseType = exerciseType;
        this.duration = duration;
        this.intensity = intensity;
    }

    public Exercise(){}

    public String getName() {
        return exerciseType;
    }

    public int getDuration() {
        return duration;
    }

    public Intensity getIntensity() {return intensity;}

    public String getIntensityName() {
        if (this.intensity == Intensity.low) return Intensity.low.toString();
        else if (this.intensity == Intensity.medium) return Intensity.medium.toString();
        else return Intensity.high.toString();
    }

    public double getCaloriesBurnt() {return caloriesBurnt;}

    public String getDate() {return date;}

    public void setDate(String date) {this.date = date;}

    public void setCaloriesBurnt(double caloriesBurnt) {this.caloriesBurnt = caloriesBurnt;}

    public void setName(String exerciseType) {this.exerciseType = exerciseType;}

    public void setDuration(int duration) {this.duration = duration;}

    public void setIntensity(Intensity intensity) {this.intensity = intensity;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
