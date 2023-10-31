package src.viewmodel.DataServices;

import java.util.ResourceBundle;

import src.viewmodel.DatabaseConnect;

public class validateUserQuery {
	private static DatabaseConnect DATABASE_CONNECT;
	private ResourceBundle reader;

	private validateUserQuery() {
		this.reader = ResourceBundle.getBundle("src/dbconfig");
	}

	public static DatabaseConnect getInstance() {
		if(DATABASE_CONNECT == null) {
			DATABASE_CONNECT = new DatabaseConnect();
		}
		return DATABASE_CONNECT;
	}
}
