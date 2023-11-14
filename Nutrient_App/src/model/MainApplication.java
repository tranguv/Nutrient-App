package src.model;

import javax.swing.SwingUtilities;
import java.util.List;

import src.client.Dashboard;
import src.client.Authentication.SignUpPage;
import src.model.models.User;

public class MainApplication {
	private List<User> users;
	
	public MainApplication() {
		// TODO Auto-generated constructor stub
	}

	public void run(){
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				// Dashboard db = new Dashboard();
				SignUpPage signup = new SignUpPage();
			}
		});
	}
}
