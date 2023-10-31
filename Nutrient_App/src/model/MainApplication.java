package src.model;

import javax.swing.SwingUtilities;

import src.view.Dashboard;

public class MainApplication {
	
	public MainApplication() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
//				LoginPage l = new LoginPage();
				Dashboard db = new Dashboard();
			}
		});
	}
}
