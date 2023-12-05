package src.main;

import src.model.MainApplication;
import src.server.DataServices.DailyNutrientIntakeViz;
import src.server.DataServices.ExerciseQueries;
import src.server.DataServices.MealQueries;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class WeightLossCalculation {
    public static String calculateWeightLossForDateRange(LocalDate startDate, LocalDate endDate) {
        // Your weight loss calculation logic here
        long numberDay = startDate.datesUntil(endDate).count();
//        double calorieIntake = ExerciseQueries.getCaloriesIntake(2); // Replace with the actual user id
        Date start = Date.valueOf(startDate);
        Date end = Date.valueOf(endDate);
        List<DailyNutrientIntakeViz> listNutrient = DailyNutrientIntakeViz.getNutrientValConsumed(MainApplication.getUser().getId(), start, end);
        double calorieIntake = 0.0;
        for (DailyNutrientIntakeViz d: listNutrient){
            calorieIntake += d.getTotalNutrientAmt();
        }
        double caloriesBurned = ExerciseQueries.getCaloriesExpended(MainApplication.getUser().getId()); // Replace with the actual user i
        double calorieDeficit = caloriesBurned - calorieIntake;
        double fatLoss = calorieDeficit / 7700;
        double averageCalorieIntake = calorieIntake / MealQueries.getNumOfMeals(2);
        double averageCalorieBurned = caloriesBurned / ExerciseQueries.getNumberOfExercises(2);
        double projectedWeightLoss = fatLoss * numberDay;

        // This is a placeholder method. You should replace it with your actual calculation.
        return String.format("%.2f", projectedWeightLoss); // Replace with the actual weight loss calculation
    }
}
