package src.server.DataServices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import src.model.models.User;

public class DBQueries {

    //for log in validation
    public static boolean validateUser(String username, String password) {
        DBConfig dbConfig = new DBConfig();

		try (Connection connection = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUsername(), dbConfig.getPassword())) {
			String sql = "SELECT * FROM USER WHERE username = ? AND user_password = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setString(1, username);
				preparedStatement.setString(2, password);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
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

	public static boolean createUser(User user) throws SQLIntegrityConstraintViolationException {
		DBConfig dbConfig = new DBConfig();
	
		try (Connection connection = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUsername(), dbConfig.getPassword())) {
			String sql = "INSERT INTO USER (userID, username, user_password, fname, lname, sex, dob, weight, height, units) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			System.out.println("SQL Query: " + sql);  // Debugging statement
            System.out.println("User id: " + user.getId()); 
			System.out.println("User sex: " + user.getSex());

			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setInt(1, user.getId());
				preparedStatement.setString(2, user.getUsername());
				preparedStatement.setString(3, String.valueOf(user.getPassword()));
				preparedStatement.setString(4, user.getFirstName());
				preparedStatement.setString(5, user.getLastName());
				preparedStatement.setString(6, user.getSex());
				preparedStatement.setString(7, user.getDateOfBirth());
				preparedStatement.setDouble(8, user.getWeight());
				preparedStatement.setDouble(9, user.getHeight());
				preparedStatement.setString(10, user.getUnits());
	
				int rowsAffected = preparedStatement.executeUpdate();
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

}
