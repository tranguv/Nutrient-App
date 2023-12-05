package src.model;

import src.server.DataServices.ExerciseQueries;

import java.sql.SQLException;
public class CaloriesCalculation {
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
