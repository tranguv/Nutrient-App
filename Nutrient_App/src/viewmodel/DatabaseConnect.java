package src.viewmodel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;

public class DatabaseConnect {

	private static DatabaseConnect DATABASE_CONNECT;
	private ResourceBundle reader;

	public DatabaseConnect() {
		this.reader = ResourceBundle.getBundle("src/dbconfig");
	}

	public static DatabaseConnect getInstance() {
		if(DATABASE_CONNECT == null) {
			DATABASE_CONNECT = new DatabaseConnect();
		}
		return DATABASE_CONNECT;
	}

	public static void main(String[] args) {
		System.out.println("Connecting Bao's database...");
		DatabaseConnect instance = DatabaseConnect.getInstance();

		try (
				Connection conn = DriverManager.getConnection(instance.reader.getString("db.url"), instance.reader.getString("db.username"), instance.reader.getString("db.password"));
			) {
			System.out.println("Database connected!");
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}

		System.out.println("Loading driver...");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("Cannot find the driver in the classpath!", e);
		}
	}
}
