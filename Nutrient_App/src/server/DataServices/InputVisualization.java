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

//    public static int TimePeriodbyUser(User user) {
//        int days = 0;
//        Date start_date = DateQueries.getDate(user);
//
//        try (Connection connection = DBConfig.getConnection()) {
//            String sql
//        }catch (SQLException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Error accessing the database", e);
//        }
//    }

    // GET CALORIES INTAKE BY USER FOR A DATE RANGE
//    public static ArrayList<Double> getCaloriesIntake(int userID, Date start_date, Date end_date) {
//        ArrayList<Double> kcalIntakeList = new ArrayList<>();
//
//        try (Connection connection = DBConfig.getConnection()) {
//            String sql = String.format("SELECT u.username,\n" +
//                    "    md.meal_type,\n" +
//                    "    dl.date_log AS start_date,\n" +
//                    "    DATE_ADD(dl.date_log, INTERVAL 1 DAY) AS end_date,\n" +
//                    "    SUM(na.NutrientValue / 100 * i.quantity) AS total_nutrient\n" +
//                    "FROM USER u\n" +
//                    "JOIN DATE_LOG dl ON dl.userID = u.userID\n" +
//                    "JOIN MEAL_DETAILS md ON dl.date_log_id = md.date_log_id\n" +
//                    "JOIN INGREDIENTS i ON i.meal_id = md.meal_id\n" +
//                    "JOIN FOOD_NAME fn ON fn.FoodID = i.FoodID\n" +
//                    "JOIN NUTRIENT_AMOUNT na ON na.FoodID = fn.FoodID\n" +
//                    "JOIN NUTRIENT_NAME nn ON nn.NutrientNameID = na.NutrientNameID\n" +
//                    "WHERE dl.date_log >= %s\n" +
//                    "    AND dl.date_log <= %s\n" +
//                    "    AND u.userID = %d\n" +
//                    "    AND nn.NutrientCode = 208", start_date, end_date, userID);
//            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//                preparedStatement.setInt(1,    userID);
//                try (ResultSet rs = preparedStatement.executeQuery()) {
//                    while (rs.next()) {
//
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Error accessing the database", e);
//        }
//    }

}