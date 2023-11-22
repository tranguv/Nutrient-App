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

import src.server.DataServices.DBQueries;
import src.server.DataServices.InputVisualization;
import src.server.DataServices.UserQueries;

import src.model.MainApplication;

import java.sql.SQLException;
import java.util.ArrayList;

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