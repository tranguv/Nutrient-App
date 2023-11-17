package src.server.DataServices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import src.model.FoodItem;
import src.model.User;
import src.model.DateLog;

public class MealQueries {
    private static DBConfig dbConfig = new DBConfig();

	private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUsername(), dbConfig.getPassword());
    }

	//INSERT NEW DATE LOG
	public static void addDateLog(DateLog dateLog) {
		try (Connection connection = getConnection()) {
			String sql = "INSERT INTO DATE_LOG (userID, date_log) VALUES (?, ?)";
			try (PreparedStatement pState = connection.prepareStatement(sql)) {
				pState.setInt(1, UserQueries.getUserIDbyUsername(dateLog.getUsername()));
				// System.out.println("User ID trong mealqueirues: " + UserQueries.getUserIDbyUsername(user.getUsername()));
				pState.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
				pState.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error accessing the database", e);
		}
	}

	//for inserting new meal log
	public static void addMeal(User user, String mealType) {
        try (Connection connection = getConnection()) {
            // Insert date log
            String logDateQuery = "INSERT INTO DATE_LOG (userID, date_log) VALUES (?, ?)";
            try (PreparedStatement logDateStatement = connection.prepareStatement(logDateQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                logDateStatement.setInt(1, UserQueries.getUserIDbyUsername(user.getUsername()));
				System.out.println("User ID trong mealqueirues: " + UserQueries.getUserIDbyUsername(user.getUsername()));
				logDateStatement.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
                logDateStatement.executeUpdate();

                // Retrieve the generated date_log_id
                ResultSet generatedKeys = logDateStatement.getGeneratedKeys();
                int dateLogId = -1;
                if (generatedKeys.next()) {
                    dateLogId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to retrieve date_log_id.");
                }

                // Insert meal details
                String insertMealQuery = "INSERT INTO MEAL_DETAILS (meal_type, date_log_id) VALUES (?, ?)";
                try (PreparedStatement insertMealStatement = connection.prepareStatement(insertMealQuery)) {
                    insertMealStatement.setString(1, mealType);
                    insertMealStatement.setInt(2, dateLogId);
                    insertMealStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error accessing the database", e);
        }
    }

	//GET FOOD GROUP
	public static String[] getFoodGroup(){
		String[] foodGroup = new String[23];
		try (Connection connection = getConnection()) {
			String sql = "SELECT FoodGroupName FROM `FOOD GROUP`";
			try (PreparedStatement pState = connection.prepareStatement(sql)) {
				try (ResultSet resultSet = pState.executeQuery()) {
					int index = 0;
					while (resultSet.next()) {
						System.out.println(Arrays.toString(foodGroup));	
						System.out.println(resultSet.getString("FoodGroupName"));	
						foodGroup[index++] = resultSet.getString("FoodGroupName");
					}
				}
				return foodGroup;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error accessing the database", e);
		}
	}

	//GET FOOD ITEM JOIN FOOD GROUP, FOOD NAME AND FOOD SOURCE TABLES
	public static List<FoodItem> getFoodItem(){
		List<FoodItem> foodItem = new ArrayList<FoodItem>();
		try (Connection connection = getConnection()) {
			String sql = "SELECT * FROM `FOOD_NAME` fn JOIN `FOOD_GROUP` fg ON fg.FoodGroupID = fn.FoodGroupID JOIN `FOOD_SOURCE` fs ON fs.FoodSourceId = fn.FoodSourceId";
			try (PreparedStatement pState = connection.prepareStatement(sql)) {
				try (ResultSet resultSet = pState.executeQuery()) {
					// int index = 0;
					while (resultSet.next()) {
						//FOOD NAME TABLE
						int foodID = resultSet.getInt("FoodID");
						String foodDescription = resultSet.getString("FoodDescriptionF");
						String foodDescriptionF = resultSet.getString("FoodDescription");
						

						//FOOD GROUP TABLE
						int foodGroupID = resultSet.getInt("FoodGroupID");
						String foodGroupName = resultSet.getString("FoodGroupName");
						String foodGroupNameF = resultSet.getString("FoodGroupNameF");

						//FOOD SOURCE TABLE
						int foodSourceID = resultSet.getInt("FoodSourceID");
						String foodSourceDescription = resultSet.getString("FoodSourceDescription");
						String foodSourceDescriptionF = resultSet.getString("FoodSourceDescriptionF");
						
						FoodItem fi = new FoodItem(foodID, foodGroupID, foodSourceID, foodDescription, foodDescriptionF, foodGroupName, foodGroupNameF, foodSourceDescription, foodSourceDescriptionF);
						foodItem.add(fi);
						// foodItem[index++] = fi;
					}
				}
				return foodItem;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error accessing the database", e);
		}
	}

	//GET FOOD GROUP NAME BY FOOD DESCRIPTION
	//USER WILL SEARCH FOR FOOD DESCRIPTION AND THE FOOD GROUP NAME WILL BE RETURNED
	//FOOD DESCRIPTION IS IN ENGLISH
	public static String getFoodGroupName(String foodDescription){
		String foodGroupName = "";
		try (Connection connection = getConnection()) {
			String sql = "SELECT FoodGroupName FROM `FOOD_GROUP` fg JOIN `FOOD_NAME` fn ON fg.FoodGroupID = fn.FoodGroupID WHERE fn.FoodDescription = ?";
			try (PreparedStatement pState = connection.prepareStatement(sql)) {
				pState.setString(1, foodDescription);
				try (ResultSet resultSet = pState.executeQuery()) {
					while (resultSet.next()) {
						foodGroupName = resultSet.getString("FoodGroupName");
					}
				}
				return foodGroupName;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error accessing the database", e);
		}
	}
}
