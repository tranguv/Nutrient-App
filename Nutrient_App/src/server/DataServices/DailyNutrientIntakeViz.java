package src.server.DataServices;

import src.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class DailyNutrientIntakeViz {
    private double totalQuantity;
    private String nutrientName;
    private double totalNutrientAmt;

    public DailyNutrientIntakeViz(double totalQuantity, String nutrientName, double totalNutrientAmt) {
        this.totalQuantity = totalQuantity;
        this.nutrientName = nutrientName;
        this.totalNutrientAmt = totalNutrientAmt;
    }

    public double getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(double totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getNutrientName() {
        return nutrientName;
    }

    public void setNutrientName(String nutrientName) {
        this.nutrientName = nutrientName;
    }

    public double getTotalNutrientAmt() {
        return totalNutrientAmt;
    }

    public void setTotalNutrientAmt(double totalNutrientAmount) {
        this.totalNutrientAmt = totalNutrientAmount;
    }


    public static List<DailyNutrientIntakeViz> getNutrientValConsumed(int userID, Date date) {
        List<DailyNutrientIntakeViz> nutritionDataList = new ArrayList<>();
        try (Connection connection = DBConfig.getConnection()) {
            String sql = "SELECT SUM(i.quantity) AS total_quantity, nn.NutrientName, round(((na.NutrientValue / 100) * SUM(i.quantity)), 2) AS total_nutrient_amt\n" +
                    "FROM `USER` u\n" +
                    "JOIN DATE_LOG dl ON dl.userID = u.userID\n" +
                    "JOIN MEAL_DETAILS md ON md.date_log_id = dl.date_log_id\n" +
                    "JOIN INGREDIENTS i ON i.meal_id = md.meal_id\n" +
                    "JOIN FOOD_NAME fn ON fn.FoodID = i.FoodID\n" +
                    "JOIN NUTRIENT_AMOUNT na ON na.FoodID = fn.FoodID\n" +
                    "JOIN NUTRIENT_NAME nn ON nn.NutrientNameID = na.NutrientNameID\n" +
                    "WHERE dl.userID = ?\n" +
                    "\tAND dl.date_log = ?\n" +
                    "\tAND i.FoodID <= 71\n" +
                    "    AND nn.NutrientName != 'ENERGY (KILOJOULES)'\n" +
                    "    AND nn.Unit = 'g'\n" +
                    "GROUP BY nn.NutrientName\n" +
                    "HAVING total_nutrient_amt > 0\n" +
                    "ORDER BY total_nutrient_amt DESC";
            try (PreparedStatement pState = connection.prepareStatement(sql)) {
                pState.setInt(1, userID);
                pState.setDate(2, date);
                try (ResultSet resultSet = pState.executeQuery()) {
                    while (resultSet.next()) {
                        double totalQuantity = resultSet.getDouble("total_quantity");
                        String nutrientName = resultSet.getString("NutrientName");
                        double totalNutrientAmount = resultSet.getDouble("total_nutrient_amt");

                        DailyNutrientIntakeViz nutritionData = new DailyNutrientIntakeViz(totalQuantity, nutrientName, totalNutrientAmount);
                        nutritionDataList.add(nutritionData);
                    }
                }
            }

        }catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error accessing the database", e);
        }
        return nutritionDataList;
    }

    public static List<DailyNutrientIntakeViz> getTop5Nutrient(List<DailyNutrientIntakeViz> nutritionDataList) {
        Collections.sort(nutritionDataList, (data1, data2) -> Double.compare(data2.getTotalNutrientAmt(), data1.getTotalNutrientAmt()));
        List<DailyNutrientIntakeViz> top5Nutrients = nutritionDataList.subList(0, 5);
        return top5Nutrients;
    }

    public static List<DailyNutrientIntakeViz> getRemainNutrient(List<DailyNutrientIntakeViz> nutritionDataList) {
        Collections.sort(nutritionDataList, (data1, data2) -> Double.compare(data2.getTotalNutrientAmt(), data1.getTotalNutrientAmt()));
        List<DailyNutrientIntakeViz> remainNutrient = nutritionDataList.subList(5, nutritionDataList.size());
        return remainNutrient;
    }

}