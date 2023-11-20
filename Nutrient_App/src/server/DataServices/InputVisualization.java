package src.server.DataServices;

import src.model.DateLog;
import src.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InputVisualization {
    private Date date;
    private String nutrientName;
    private double kcalAmt;
    private double kcalExercise;

    public InputVisualization() {

    }

    public static ArrayList<Date> getDateListbyUser(int userID) {
        ArrayList<Date> dateListbyUser = new ArrayList<>();
        try (Connection connection = DBConfig.getConnection()) {
            String sql = "SELECT date_log FROM DATE_LOG WHERE userID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1,     userID);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        dateListbyUser.add(rs.getDate("date_log"));

                    }
                }
                return dateListbyUser;
//                throw new RuntimeException("Cannot find date_log");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error accessing the database", e);
        }
    }

    public static int TimePeriodbyUser(User user) {
        int days = 0;
        Date start_date = DateQueries.getDate(user);

        try (Connection connection = DBConfig.getConnection()) {
            String sql
        }catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error accessing the database", e);
        }
    }
    public static ArrayList<Double> getCaloriesIntake(int userID) {
        ArrayList<Double> kcalIntakeList = new ArrayList<>();
        try (Connection connection = DBConfig.getConnection()) {
            String sql = "SELECT date_log FROM DATE_LOG WHERE userID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1,    userID);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        dateListbyUser.add(rs.getDate("date_log"));

                    }
                }
                return dateListbyUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error accessing the database", e);
        }
    }

}
