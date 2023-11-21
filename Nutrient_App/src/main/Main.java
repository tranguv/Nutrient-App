package src.main;
import src.client.Authentication.LoginPage;
import src.client.Authentication.SignUpPage;
import src.client.LogData.DatePanel;
import src.model.Exercise;
import src.model.MainApplication;
// import src.model.authLogic.SignupLogic;
import src.client.Authentication.ChooseProfile;
import src.client.Authentication.LoginPage;
import src.client.Authentication.SignUpPage;
import src.model.User;

import src.server.DataServices.*;


//import java.sql.Date;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) throws SQLException {
		MainApplication mainApp = new MainApplication();
		mainApp.run();


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


//		User userTest = new User("an1","pham1","An", "Pham", "M","2003-09-13",60,175,"metric");
//		Exercise exe = new Exercise(userTest, "Frisbee", 50, Exercise.Intensity.medium);
//		System.out.println(Exercise.caloriesBurntExercise(userTest, exe,"medium"));
//		double bmr = Exercise.BMRcalc(userTest);
//		System.out.println(Exercise.TDEEcalc(bmr, "medium"));


//		System.out.println(InputVisualization.getDateListbyUser(UserQueries.getUserIDbyUsername("trangvu")));


		// User userTest = new User("an1","pham1","An", "Pham", "M","2003-09-13",60,175,"metric");
		// Exercise exe = new Exercise(userTest, "Frisbee", 50, Exercise.Intensity.medium);
//		System.out.println(Exercise.caloriesBurntExercise(userTest, exe,"medium"));
		// double bmr = Exercise.BMRcalc(userTest);
		// System.out.println(Exercise.TDEEcalc(bmr, "medium"));

//		WeightPredictionPanel wpp = new WeightPredictionPanel();
		User user = UserQueries.getUserByID(10);
		Date date = DateQueries.getDate(user);
//		System.out.println(user.getUsername());
//		System.out.println(date.toString());
//		DatePanel dp = new DatePanel();
//		dp.setSelectedDate("2023-11-16");
		ArrayList<Double> caloryList = MealQueries.getDailyKcalIntake(10, date);
		for (int i = 0; i < caloryList.size(); i++) {
			System.out.println(caloryList.get(i));
		}


	}
}
