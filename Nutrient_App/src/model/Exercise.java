package src.model;

import java.sql.SQLException;

import src.server.DataServices.ExerciseQueries;

public class Exercise {
    private String exerciseType;
    private int duration;
    private int id;
    private Intensity intensity;
    private double caloriesBurnt;
    private String date;

    public Exercise() {}
    public Exercise(String exerciseType, int duration, Intensity intensity) {
        this.exerciseType = exerciseType;
        this.duration = duration;
        this.intensity = intensity;
    }

    public int getID() {return id;}

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

    public void setID(int id) {this.id = id;}
    
    public void setName(String exerciseType) {this.exerciseType = exerciseType;}

    public void setDuration(int duration) {this.duration = duration;}

    public void setIntensity(Intensity intensity) {this.intensity = intensity;}

    public static double caloriesBurntExercise(User user, Exercise exe, String intensity) throws SQLException {
        double kcal_burnt = 0;
        if (intensity.equals("low")) kcal_burnt = ((ExerciseQueries.getMETLow(exe.getName()) * 3.5 * user.getWeight() * exe.getDuration())) / 200 ;
        else if (intensity.equals("medium")) kcal_burnt = ((ExerciseQueries.getMETMed(exe.getName()) * 3.5 * user.getWeight()) * exe.getDuration()) / 200 ;
        else if (intensity.equals("high")) kcal_burnt = ((ExerciseQueries.getMETHigh(exe.getName()) * 3.5 * user.getWeight())* exe.getDuration()) / 200 ;
        return kcal_burnt;
    }


    public static double BMRcalc(User user) {
        double bmrIndex = 0;
        double w = user.getWeight();
        double h = user.getHeight();
        int age = user.getAge(user.getDateOfBirth());
        String sex = user.getSex();

        if (sex.equals("M")) {
            bmrIndex = 10*w + 6.25*h - 5*age + 5;
        }
        else if (sex.equals("F")) {
            bmrIndex = 10*w + 6.25*h - 5*age - 161;
        }
        return bmrIndex;
    }

    public static double TDEEcalc(User user, String inten) {
        double tdee = 0;
        double bmr = BMRcalc(user);
        if (inten.equals(Intensity.low.toString())) {tdee = bmr * 1.375;}
        else if (inten.equals(Intensity.medium.toString())) {tdee = bmr * 1.55;}
        else if (inten.equals(Intensity.high.toString())) {tdee = bmr * 1.725;}
        return tdee;
    }
}
