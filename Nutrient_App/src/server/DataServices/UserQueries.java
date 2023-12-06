package src.server.DataServices;

import src.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;

public class UserQueries {
	//for log in validation
	public static boolean validateUser(String username, String password) {
		try (Connection connection = DBConfig.getConnection()) {
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
		try (Connection connection = DBConfig.getConnection()) {
			String sql = "INSERT INTO USER (username, user_passwordsex, dob, weight, height, units, age) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

			try (PreparedStatement pState = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				pState.setString(1, user.getUsername());
				pState.setString(2, user.getPassword());
				pState.setString(3, user.getSex());
				pState.setString(4, user.getDateOfBirth());
				pState.setDouble(5, user.getWeight());
				pState.setDouble(6, user.getHeight());
				pState.setString(7, user.getUnits());
				pState.setInt(8, user.getAge());

				int rowsAffected = pState.executeUpdate();
				if (rowsAffected > 0) {
					try (ResultSet generatedKeys = pState.getGeneratedKeys()) {
						if (generatedKeys.next()) {
							user.setId(generatedKeys.getInt(1));
						} else {
							throw new SQLException("Failed to retrieve user ID.");
						}
					}
					return true;
				} else {
					return false;
				}
			} catch (SQLIntegrityConstraintViolationException e) {
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error accessing the database", e);
		}
	}

	//GET USER ID BY USERNAME
	public static int getUserIDbyUsername(String username) throws SQLException {
		int userID = 0;
		Connection connection = DBConfig.getConnection();
		String sql = "SELECT userID FROM USER WHERE username = ?";
		PreparedStatement pState = connection.prepareStatement(sql);
		pState.setString(1, username);
		ResultSet resultSet = pState.executeQuery();
		while (resultSet.next()) {
			userID = resultSet.getInt("userID");
		}
		return userID;
	}

	public static User getUserByID(int id){
		User user = null;
		try (Connection connection = DBConfig.getConnection()) {
			String sql = "SELECT * FROM USER WHERE userID = ?";
			try (PreparedStatement pState = connection.prepareStatement(sql)) {
				pState.setInt(1, id);

				try (ResultSet resultSet = pState.executeQuery()) {
					if (resultSet.next()) {
						String user_username = resultSet.getString("username");
						String user_userPassword = resultSet.getString("user_password");
						String firstName = resultSet.getString("fname");
						String lastname = resultSet.getString("lname");
						String sex = resultSet.getString("sex");
						String dob = resultSet.getString("dob");
						double weight = Double.parseDouble(resultSet.getString("weight"));
						double height = Double.parseDouble(resultSet.getString("height"));
						String unit = resultSet.getString("units");
						int age = resultSet.getInt("age");
						user = new User(user_username,user_userPassword,dob,weight,height,unit);
						user.setFirstName(firstName);
						user.setLastName(lastname);
						user.setSex(sex);

						return user;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error accessing the database", e);
		}
		return user;
	}

	public static boolean updateUserDetails(String username, String fname, String lname, String sex, String dob,double height, double weight) {
		try (Connection connection = DBConfig.getConnection()) {
			// Update SQL statement to include height and weight
			String sql = "UPDATE USER SET fname = ?, lname = ?, sex = ?, dob = ?,height = ?, weight = ?, age = ? WHERE username = ?";

			try (PreparedStatement pState = connection.prepareStatement(sql)) {
				pState.setString(1, fname);
				pState.setString(2, lname);
				pState.setString(3, sex);
				pState.setString(4, dob);
				pState.setDouble(5, height); // Assuming height is a double
				pState.setDouble(6, weight); // Assuming weight is a double

				LocalDate dateOfBirth = LocalDate.parse(dob);
				LocalDate curDate = LocalDate.now();
				int age = 0;
				if(dateOfBirth != null && curDate != null){
					age = Period.between(dateOfBirth, curDate).getYears();
				}

				pState.setInt(7, age);
				pState.setString(8, username);

				int rowsAffected = pState.executeUpdate();
				return rowsAffected > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error accessing the database", e);
		}
	}
}





