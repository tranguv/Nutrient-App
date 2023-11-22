package src.main;
import src.model.MainApplication;

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