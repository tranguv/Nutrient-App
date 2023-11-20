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
import java.sql.Date;

import src.model.FoodItem;
import src.model.User;
import src.model.DateLog;
import src.model.Meal;

public class MealQueries {


	//CHECK IF MEAL EXISTS, IF YES RETURN MEAL ID

	// GET THE LAST INSERTED MEAL ID
	public static int getMealID() {
		try (Connection connection = DBConfig.getConnection()) {
			String sql = "SELECT meal_id FROM MEAL_DETAILS ORDER BY meal_id DESC LIMIT 1";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				try (ResultSet rs = preparedStatement.executeQuery()) {
					if (rs.next()) {
						return rs.getInt("meal_id");
					}
				}
				throw new RuntimeException("Cannot find last inserted meal_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error accessing the database", e);
		}
	}
	

	//for inserting new meal log
	public static void addMeal(DateLog date, Meal meal) {
		try (Connection connection = DBConfig.getConnection()) {
			String insertMealQuery = "INSERT INTO MEAL_DETAILS (meal_type, date_log_id) VALUES (?, ?)";
			String insertIngredientQuery = "INSERT INTO INGREDIENT (meal_id, quantity, unit) VALUES (?, ?, ?)";
			int dateLogId = date.getDateLogId();
			try (PreparedStatement insertMealStatement = connection.prepareStatement(insertMealQuery)) {
				insertMealStatement.setString(1, meal.getType().toString());
				insertMealStatement.setInt(2, dateLogId);
				insertMealStatement.executeUpdate();
			}

			int mealId = meal.getMealId();
			for (int i = 0; i < meal.getIngredients().size(); i++) {
				try (PreparedStatement insertIngredientStatement = connection.prepareStatement(insertIngredientQuery)) {
					insertIngredientStatement.setInt(1, mealId);
					insertIngredientStatement.setDouble(2, meal.getIngredients().get(i).getQuantity());
					insertIngredientStatement.setString(3, meal.getIngredients().get(i).getUnit());
					insertIngredientStatement.executeUpdate();
				}
			}
		} catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error accessing the database", e);
        }
	}

	//ADD INGREDIENTS TO MEAL
	public static void addIngredient(Meal meal, FoodItem foodItem, double quantity) {
		try (Connection connection = DBConfig.getConnection()) {
			String insertIngredientQuery = "INSERT INTO INGREDIENT (food_id, meal_id, quantity) VALUES (?, ?, ?)";
			int foodId = foodItem.getFoodID();
			int mealId = meal.getMealId();
			try (PreparedStatement insertIngredientStatement = connection.prepareStatement(insertIngredientQuery)) {
				insertIngredientStatement.setInt(1, foodId);
				insertIngredientStatement.setInt(2, mealId);
				insertIngredientStatement.setDouble(3, quantity);
				insertIngredientStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error accessing the database", e);
		}
	}

	//GET FOOD GROUP
	public static String[] getFoodGroup(){
		String[] foodGroup = new String[23];
		try (Connection connection = DBConfig.getConnection()) {
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
		try (Connection connection = DBConfig.getConnection()) {
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

	//GET FOOD DESCRIPTION JOIN FOOD GROUP, FOOD NAME AND FOOD SOURCE TABLES
	public static List<String> getFoodDescription(){
		List<String> foodItem = new ArrayList<>();
		try (Connection connection = DBConfig.getConnection()) {
			String sql = "SELECT * FROM `FOOD_NAME` fn JOIN `FOOD_GROUP` fg ON fg.FoodGroupID = fn.FoodGroupID JOIN `FOOD_SOURCE` fs ON fs.FoodSourceId = fn.FoodSourceId";
			try (PreparedStatement pState = connection.prepareStatement(sql)) {
				try (ResultSet resultSet = pState.executeQuery()) {
					// int index = 0;
					while (resultSet.next()) {
						String foodDescription = resultSet.getString("FoodDescription");
						String foodDescriptionF = resultSet.getString("FoodDescriptionF");
						foodItem.add(foodDescription);
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
		try (Connection connection = DBConfig.getConnection()) {
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
