package src.server.DataServices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import src.model.User;

public class UserQueries {
    private static DBConfig dbConfig = new DBConfig();

	private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUsername(), dbConfig.getPassword());
    }

    //for log in validation
    public static boolean validateUser(String username, String password) {
        // DBConfig dbConfig = new DBConfig();

        try (Connection connection = getConnection()) {
			String sql = "SELECT * FROM USER WHERE username = ? AND user_password = ?";
			try (PreparedStatement pState = connection.prepareStatement(sql)) {
				pState.setString(1, username);
				pState.setString(2, password);

				try (ResultSet resultSet = pState.executeQuery()) {
					if (resultSet.next()) { // If a row is returned, the credentials are valid
						return true;
					} else {
						return false;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error accessing the database", e);
		}
    }

	//for sign up validation
	public static boolean createUser(User user) throws SQLIntegrityConstraintViolationException {
		// DBConfig dbConfig = new DBConfig();
	
        try (Connection connection = getConnection()) {
			String sql = "INSERT INTO USER (username, user_password, fname, lname, sex, dob, weight, height, units) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			System.out.println("SQL Query: " + sql);  // Debugging statement
			System.out.println("User sex: " + user.getSex());

			try (PreparedStatement pState = connection.prepareStatement(sql)) {
				// pState.setInt(1, user.getId());
				pState.setString(1, user.getUsername());
				pState.setString(2, String.valueOf(user.getPassword()));
				pState.setString(3, user.getFirstName());
				pState.setString(4, user.getLastName());
				pState.setString(5, user.getSex());
				pState.setString(6, user.getDateOfBirth());
				pState.setDouble(7, user.getWeight());
				pState.setDouble(8, user.getHeight());
				pState.setString(9, user.getUnits());
	
				int rowsAffected = pState.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("User signed up successfully!");
					return true;
				} else {
					return false;
				}
			} catch (SQLIntegrityConstraintViolationException e) {
				// Rethrow the exception if it's a unique constraint violation
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error accessing the database", e);
		}
	}	

	//GET CURRENT USER ID
	public static int getUserID(){
		int userID = 0;
		try (Connection connection = getConnection()) {
			String sql = "SELECT LAST_INSERT_ID() AS current_user_id";
			try (PreparedStatement pState = connection.prepareStatement(sql)) {
				try (ResultSet resultSet = pState.executeQuery()) {
					while (resultSet.next()) {
						userID = resultSet.getInt("current_user_id");
					}
				}
				return userID;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error accessing the database", e);
		}
	}

	//for updating user profile

	//for deleting user profile

	//for getting user profile by username
	// public static User getUser(String username){
	// 	try (Connection connection = getConnection()) {
	// 		String sql = "SELECT * FROM USER WHERE username = ?";
	// 		try (PreparedStatement pState = connection.prepareStatement(sql)) {
	// 			pState.setString(1, username);
	// 			pState.executeQuery();

	// 			try (ResultSet)
	// 		}
	// 	} catch (SQLException e) {
	// 		e.printStackTrace();
	// 		throw new RuntimeException("Error accessing the database", e);
	// 	}

	// }
}