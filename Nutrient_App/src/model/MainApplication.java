package src.model;

import javax.swing.SwingUtilities;
import java.util.List;

import src.client.LogData.Dashboard;
import src.client.Authentication.LoginPage;
import src.client.Authentication.SignUpPage;

public class MainApplication {
	private static User currentUser;
	
	public MainApplication() {
		// TODO Auto-generated constructor stub
	}

	public void run(){
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				LoginPage login = new LoginPage();

			}
		});
	}

	public static User getUser(){
		return currentUser;
	}

	public static void setUser(User user){
		currentUser = user;
	}
}
