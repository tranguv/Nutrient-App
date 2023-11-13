// package src.model.authLogic;

// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.sql.SQLIntegrityConstraintViolationException;

// import src.server.DataServices.DBConfig;

// public class LoginLogic {
//     public static boolean validateUser(String username, String password) {
//         DBConfig dbConfig = new DBConfig();

// 		try (Connection connection = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUsername(), dbConfig.getPassword())) {
// 			String sql = "SELECT * FROM USER WHERE username = ? AND user_password = ?";
// 			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
// 				preparedStatement.setString(1, username);
// 				preparedStatement.setString(2, password);

// 				try (ResultSet resultSet = preparedStatement.executeQuery()) {
// 					if (resultSet.next()) { // If a row is returned, the credentials are valid
// 						return true;
// 					} else {
// 						return false;
// 					}
// 				}
// 			}
// 		} catch (SQLException e) {
// 			e.printStackTrace();
// 			throw new RuntimeException("Error accessing the database", e);
// 		}
//     }
// }