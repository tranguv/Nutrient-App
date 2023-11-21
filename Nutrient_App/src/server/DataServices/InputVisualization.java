package src.server.DataServices;

import src.main.WeightPredictionPanel;
import src.model.DateLog;
import src.model.User;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

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

    public static int TimePeriodbyUser(LocalDate start, LocalDate end) {
//        WeightPredictionPanel wpp = new WeightPredictionPanel();
//        String txt_start = wpp.getStartDateField().getText();
//        String txt_end = wpp.getEndDateField().getText();
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
////        Date end_date = null;
////        Date start_date = null;
//        try {
//            start_date = dateFormat.parse(txt_start);
//            end_date = dateFormat.parse(txt_end);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        days = (int) Math.abs(end_date.getTime() - start_date.getTime());
        int days = 0;
        days = (int) ChronoUnit.DAYS.between(start, end);
        return days;
    }
//    public static ArrayList<Double> getCaloriesIntake(int userID) {
//        ArrayList<Double> kcalIntakeList = new ArrayList<>();
//        try (Connection connection = DBConfig.getConnection()) {
//            String sql = "SELECT date_log FROM DATE_LOG WHERE userID = ?";
//            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//                preparedStatement.setInt(1,    userID);
//                try (ResultSet rs = preparedStatement.executeQuery()) {
//                    while (rs.next()) {
//                        dateListbyUser.add(rs.getDate("date_log"));
//
//                    }
//                }
//                return dateListbyUser;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Error accessing the database", e);
//        }
//    }

}
