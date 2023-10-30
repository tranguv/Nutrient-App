package model.authLogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignupLogic {

    public static void signUpUser(String username, String password, String dob, double weight, double height) {
        // Establish a connection to your MySQL database
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "username", "password")) {
            String sql = "INSERT INTO users (username, password, dob, weight, height) VALUES (?, ?, ?, ?, ?)";

            // Create a PreparedStatement to safely insert user data
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, dob);
            preparedStatement.setInt(4, weight);
            preparedStatement.setInt(5, height);

            // Execute the query
            preparedStatement.executeUpdate();
            System.out.println("User signed up successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage());
        }
    }
}
