package src.viewmodel;

import src.viewmodel.DataServices.DBConfig;

import java.sql.*;
import src.viewmodel.DataServices.DBConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

public class DatabaseInsert {
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
    public static void signUpUser(String username, String user_password, String dob, double weight, double height) throws SQLIntegrityConstraintViolationException {
//        DBConfig dbConfig = new DBConfig();
        // JDBC URL, username, and password of MySQL server
        String url = "jdbc:mysql://localhost:3306/nutrientapp";
        String username1 = "root";
        String password1 = "anph1!";

        // Try-with-resources statement will auto-close Connection and PreparedStatement
        try (Connection connection = DriverManager.getConnection(url, username1, password1)) {

            String sql = "INSERT INTO USER (username, user_password, dob, weight, height) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username); // Use variables, not hard-coded values
                preparedStatement.setString(2, user_password); // Same here, and consider hashing before sending to database
                preparedStatement.setString(3, dob);
                preparedStatement.setDouble(4, weight);
                preparedStatement.setDouble(5, height);

                preparedStatement.executeUpdate();

                System.out.println("User signed up successfully!");

            }
            catch (SQLIntegrityConstraintViolationException e) {
                if (e.getSQLState().equals("23000")) {
                    // Could also log this to the server logs if appropriate
                    System.out.println("Error: Username already exists. Please choose a different username.");
                    throw e;
                }
            }

        } catch (SQLException e) {
            // General SQL exception handling
            e.printStackTrace(); // For development time debugging
            throw new RuntimeException("Error accessing the database", e);
        }
    }
}
