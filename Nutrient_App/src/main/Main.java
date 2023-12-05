package src.main;
// import src.model.authLogic.SignupLogic;


import src.server.DataServices.*;


//import java.sql.Date;
import java.sql.Date;


		import java.text.ParseException;


public class Main {
	public static void main(String[] args) throws ParseException {
//		try {
//			MainApplication mainApp = new MainApplication();
//			mainApp.run();
//		} catch (Exception e) {
//			System.err.println("Unexpected exception: " + e.getMessage());
//			// Handle other unexpected exceptions
//		}
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date specificDate1 = dateFormat.parse("2023-11-21 12:00:00");

		// Initialize with specific date components
//		java.util.Calendar calendar = java.util.Calendar.getInstance();
//		calendar.set(2023, 10, 21, 12, 0, 0);
//		Date specificDate2 = calendar.getTime();
		Date date = DateQueries.getDatebyUserID(32);
//		List<DailyNutrientIntakeViz> nutrientdataList = DailyNutrientIntakeViz.getNutrientValConsumed(32, date);
//		List<DailyNutrientIntakeViz> top5NutrientList = DailyNutrientIntakeViz.getTop5Nutrient(nutrientdataList);
//		List<DailyNutrientIntakeViz> remainNutrientList = DailyNutrientIntakeViz.getRemainNutrient(nutrientdataList);

		double totalQuantity = 0;
		double totalNutAmt = 0;

//		for (int i = 0; i < top5NutrientList.size(); i++ ) {
//			System.out.printf("%.2f", (top5NutrientList.get(i).getTotalNutrientAmt() / top5NutrientList.get(i).getTotalQuantity()) * 100);
//			System.out.print(" %");
//			System.out.println();
//		}

//		for (int i = 0; i < remainNutrientList.size(); i++) {
//			totalQuantity = remainNutrientList.get(i).getTotalQuantity();
//			totalNutAmt = remainNutrientList.get(i).getTotalNutrientAmt();
//		}
//		System.out.println((totalNutAmt / totalQuantity) * 100);


//		for (int i = 0; i < getRemainNutrient.size(); i++ ) {
//			System.out.println(getRemainNutrient.get(i).getTotalNutrientAmt());
//		}


	}
}