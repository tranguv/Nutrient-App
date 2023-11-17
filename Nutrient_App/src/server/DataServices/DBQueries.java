package src.server.DataServices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import src.model.User;

public class DBQueries {
	private static DBConfig dbConfig = new DBConfig();

	private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUsername(), dbConfig.getPassword());
    }

    //for log in validation
    public static boolean validateUser(String username, String password) {
        // DBConfig dbConfig = new DBConfig();

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

	public static boolean signUpUser(String username, String password) {
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

	//for sign up validation
	public static boolean createUser(User user) throws SQLIntegrityConstraintViolationException {
		// DBConfig dbConfig = new DBConfig();
	
        try (Connection connection = getConnection()) {
			String sql = "INSERT INTO USER (username, user_password, fname, lname, sex, dob, weight, height, units) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			System.out.println("SQL Query: " + sql);  // Debugging statement
			System.out.println("User sex: " + user.getSex());

			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				// preparedStatement.setInt(1, user.getId());
				preparedStatement.setString(1, user.getUsername());
				preparedStatement.setString(2, String.valueOf(user.getPassword()));
				preparedStatement.setString(3, user.getFirstName());
				preparedStatement.setString(4, user.getLastName());
				preparedStatement.setString(5, user.getSex());
				preparedStatement.setString(6, user.getDateOfBirth());
				preparedStatement.setDouble(7, user.getWeight());
				preparedStatement.setDouble(8, user.getHeight());
				preparedStatement.setString(9, user.getUnits());
	
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

	//for updating user profile

	//for deleting user profile

	//for getting user profile

	//for getting user log

	//for inserting meal log
	public static void addMeal(User user){
		try (Connection connection = getConnection()) {
			String sql = "INSERT INTO MEAL_DETAILS (mealID, meal_type, date_log_id) VALUES(?, ?, ?)";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setInt(0, 0);
				preparedStatement.setString(1, "Breakfast");
				preparedStatement.setInt(2, 0);
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error accessing the database", e);
		}
	}

	//GET FOOD GROUP
	public static String[] getFoodGroup(){
		String[] foodGroup = new String[25];
		try (Connection connection = getConnection()) {
			String sql = "SELECT FoodGroupName FROM `FOOD GROUP`";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						foodGroup[resultSet.getRow()] = resultSet.getString("FoodGroupName");
					}
				}
				return foodGroup;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error accessing the database", e);
		}
	}

}
