package src.main;

import src.model.MainApplication;
import java.sql.SQLException;

public class Main {
	public static void main(String[] args) {
		try {
			MainApplication mainApp = new MainApplication();
			mainApp.run();
		} catch (Exception e) {
			System.err.println("Unexpected exception: " + e.getMessage());
			// Handle other unexpected exceptions
		}
	}
}


//		System.out.println(userTest.getAge(userTest.getDateOfBirth()));
//		System.out.println(Exercise.BMRcalc(userTest));

//		for (String e: DBQueries.getExerciseList()) {
//			System.out.println(e);
//		}
//
//		for (String e: DBQueries.getFoodGroup()) {
//			System.out.println(e);
//		}

//		System.out.println(DBQueries.getMETLow("Jumprope"));
//		System.out.println(DBQueries.getMETMed("Aerobics"));
//		System.out.println(DBQueries.getMETHigh("Martial Arts"));

		// User userTest = new User("an1","pham1","An", "Pham", "M","2003-09-13",60,175,"metric");
		// Exercise exe = new Exercise(userTest, "Frisbee", 50, Exercise.Intensity.medium);
//		System.out.println(Exercise.caloriesBurntExercise(userTest, exe,"medium"));
		// double bmr = Exercise.BMRcalc(userTest);
		// System.out.println(Exercise.TDEEcalc(bmr, "medium"));


