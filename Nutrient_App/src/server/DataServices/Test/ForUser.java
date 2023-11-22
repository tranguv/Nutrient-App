package src.server.DataServices.Test;

import src.model.User;
import src.server.DataServices.DBConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ForUser {
    public ArrayList<User> getAllUser() throws SQLException {
        ArrayList<User> allUsers = new ArrayList<>();
        try (Connection connection = DBConfig.getConnection()) {
            String sql = "SELECT * FROM USER";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = ps.executeQuery()) {
                    User user;
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
                        user = new User(user_username,user_userPassword,firstName,lastname,sex,dob,weight,height,unit);
                        allUsers.add(user);
                    }
                }
            }
            return allUsers;
        }
    }
}
