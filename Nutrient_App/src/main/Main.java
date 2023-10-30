package main;

import model.MainApplication;
import model.authLogic.SignupLogic;
import view.Dashboard;
import view.Authentication.SignUpPage;

public class Main {
	public static void main(String[] args) {
		SignupLogic a = new SignupLogic();
		a.signUpUser("Boa","cakcak","120503",123,132);
	}
}
