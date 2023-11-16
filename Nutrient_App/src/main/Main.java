package src.main;
import src.client.Authentication.LoginPage;
import src.client.Authentication.SignUpPage;
import src.model.authLogic.SignupLogic;
import src.client.Authentication.ChooseProfile;
import src.client.Authentication.LoginPage;
import src.client.Authentication.SignUpPage;

public class Main {
	public static void main(String[] args) {
		// SignUpPage loginPage = new SignUpPage();
		LoginPage loginPage = new LoginPage();
		loginPage.login();
	}
}
