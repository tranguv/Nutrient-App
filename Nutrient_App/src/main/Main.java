package src.main;
import src.model.MainApplication;

// import src.model.authLogic.SignupLogic;
import src.client.Authentication.ChooseProfile;
import src.client.Authentication.LoginPage;
import src.client.Authentication.SignUpPage;
import src.model.User;


import src.server.DataServices.*;


//import java.sql.Date;
import java.sql.Date;

import src.server.DataServices.InputVisualization;
import src.server.DataServices.UserQueries;

import src.model.MainApplication;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Main {
	public static void main(String[] args) throws ParseException {
		try {
			MainApplication mainApp = new MainApplication();
			mainApp.run();
		} catch (Exception e) {
			System.err.println("Unexpected exception: " + e.getMessage());
			// Handle other unexpected exceptions
		}
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date specificDate1 = dateFormat.parse("2023-11-21 12:00:00");

		// Initialize with specific date components
//		java.util.Calendar calendar = java.util.Calendar.getInstance();
//		calendar.set(2023, 10, 21, 12, 0, 0);
//		Date specificDate2 = calendar.getTime();
//		Date date = DateQueries.getDatebyUserID(32);
//		List<DailyNutrientIntakeViz> nutrientdataList = DailyNutrientIntakeViz.getNutrientValConsumed(32, date);
//		List<DailyNutrientIntakeViz> getTop5Nutrient = DailyNutrientIntakeViz.getTop5Nutrient(nutrientdataList);
//		List<DailyNutrientIntakeViz> getRemainNutrient = DailyNutrientIntakeViz.getRemainNutrient(nutrientdataList);
//		for (int i = 0; i < getRemainNutrient.size(); i++ ) {
//			System.out.println(getRemainNutrient.get(i).getTotalNutrientAmt());
//		}

	}
}