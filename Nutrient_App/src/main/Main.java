package src.main;

import src.model.authLogic.LoginLogic;
import src.model.authLogic.SignupLogic;
import src.view.Authentication.LoginPage;
import src.view.Authentication.SignUpPage;
import src.viewmodel.DatabaseConnect;
import src.viewmodel.DatabaseInsert;

import java.sql.SQLIntegrityConstraintViolationException;

public class Main {
	public static void main(String[] args) throws SQLIntegrityConstraintViolationException {

//		DatabaseConnect testDB_connect = new DatabaseConnect();

//		SignupLogic testDB = new SignupLogic();
//		testDB.signUpUser("An", "Pham", "130911", 70, 175);
//		testDB.signUpUser("Bao", "Gia", "12342", 65, 170);

		DatabaseInsert dbInsert = new DatabaseInsert();
		DatabaseInsert.signUpUser("Bi", "Minh", "43453", 67, 168);

		System.out.println(DatabaseInsert.validateUser("An", "tran"));



	}
}
