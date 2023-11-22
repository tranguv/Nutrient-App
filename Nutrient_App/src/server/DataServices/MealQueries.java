package src.server.DataServices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.sql.Date;

import src.model.FoodItem;
import src.model.Ingredient;
import src.model.User;
import src.model.DateLog;
import src.model.Meal;

public class MealQueries {
	//GET NUMBER OF MEALS ALL THE TIME
	public static int getNumOfMeals(int userID) {
		try (Connection connection = DBConfig.getConnection()) {
			String sql = String.format("SELECT COUNT(*) AS total_exercises\n" +
					"FROM EXERCISE_LOG E\n" +
					"JOIN DATE_LOG D ON E.date_log_id = D.date_log_id\n" +
					"WHERE D.userID = %d", userID);
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				try (ResultSet rs = preparedStatement.executeQuery()) {
					if (rs.next()) {
						return rs.getInt("total_exercises");
					} else {
						return 0;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error accessing the database", e);
		}
	}

	//CHECK IF MEAL EXISTS, IF YES RETURN MEAL ID
	public static int getMealID(Meal meal) {
		try (Connection connection = DBConfig.getConnection()) {
			String sql = "SELECT meal_id FROM MEAL_DETAILS WHERE meal_type = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setString(1, meal.getType().toString());
				try (ResultSet rs = preparedStatement.executeQuery()) {
					if (rs.next()) {
						return rs.getInt("meal_id");
					}
				}
				return -1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error accessing the database", e);
		}
	}

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

	// for inserting meal log
	public static int addMeal(DateLog date, Meal meal) {
		String sqlInsert = "INSERT INTO MEAL_DETAILS (meal_type, date_log_id) VALUES(?, ?)";
		String sqlSelect = "SELECT LAST_INSERT_ID()";

		try (Connection connection = DBConfig.getConnection()) {
			try (PreparedStatement insertStatement = connection.prepareStatement(sqlInsert)) {
				insertStatement.setString(1, meal.getType().toString());
				insertStatement.setInt(2, date.getDateLogId());
				insertStatement.executeUpdate();
			}

			try (PreparedStatement selectStatement = connection.prepareStatement(sqlSelect)) {
				try (ResultSet rs = selectStatement.executeQuery()) {
					if (rs.next()) {
						return rs.getInt(1); // Use the appropriate column index
					} else {
						throw new RuntimeException("Cannot find last inserted meal_id");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error accessing the database", e);
		}

	}

	//	LOG INGREDIENTS
	public static void addIngredients(int mealID, Ingredient ingre) {
		String sql = "INSERT INTO INGREDIENTS (meal_id, FoodID, quantity, unit) VALUES(?, ?, ?, ?);";
		// String sqlSelect = "SELECT LAST_INSERT_ID()";
		try (Connection connection = DBConfig.getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setInt(1, mealID);
				preparedStatement.setInt(2, ingre.getFoodItem().getFoodID());
				preparedStatement.setDouble(3, ingre.getQuantity());
				preparedStatement.setString(4, ingre.getUnit());
				preparedStatement.executeUpdate();
			}
			// try (PreparedStatement selectStatement = connection.prepareStatement(sqlSelect)) {
			// 	try (ResultSet rs = selectStatement.executeQuery()) {
			// 		if (rs.next()) {
			// 			return rs.getInt(1); // Use the appropriate column index
			// 		} else {
			// 			throw new RuntimeException("Cannot find last inserted meal_id");
			// 		}
			// 	}
			// }

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

	// GET FOOD ITEM BY ID
	public static FoodItem getFoodItemByID(int foodID){
		FoodItem foodItem = null;
		try (Connection connection = DBConfig.getConnection()) {
			String sql = "SELECT * FROM `FOOD_NAME` fn JOIN `FOOD_GROUP` fg ON fg.FoodGroupID = fn.FoodGroupID JOIN `FOOD_SOURCE` fs ON fs.FoodSourceId = fn.FoodSourceId WHERE fn.FoodID = ?";
			try (PreparedStatement pState = connection.prepareStatement(sql)) {
				pState.setInt(1, foodID);
				try (ResultSet resultSet = pState.executeQuery()) {
					while (resultSet.next()) {
						//FOOD NAME TABLE
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

						foodItem = new FoodItem(foodID, foodGroupID, foodSourceID, foodDescription, foodDescriptionF, foodGroupName, foodGroupNameF, foodSourceDescription, foodSourceDescriptionF);
					}
				}
				return foodItem;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error accessing the database", e);
		}
	}

	//GET INGREDIENTS BY MEAL ID
	public static List<Ingredient> getIngredientsFromMealID(int mealID){
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		try (Connection connection = DBConfig.getConnection()) {
			String sql = "SELECT * FROM `INGREDIENT` WHERE meal_id = ?";
			try (PreparedStatement pState = connection.prepareStatement(sql)) {
				pState.setInt(1, mealID);
				try (ResultSet resultSet = pState.executeQuery()) {
					while (resultSet.next()) {
						int ingredientID = resultSet.getInt("ingredient_id");
						String name = resultSet.getString("name");
						double quantity = resultSet.getDouble("quantity");
						String unit = resultSet.getString("unit");
						FoodItem foodItem = getFoodItemByID(ingredientID);
						Ingredient ingre = new Ingredient(foodItem, quantity, unit);
						ingredients.add(ingre);
					}
				}
				return ingredients;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error accessing the database", e);
		}
	}

	//GET FOOD ITEM BY FOOD DESCRIPTION
	public static FoodItem getFoodItem(String foodDescription){
		FoodItem foodItem = null;
		try (Connection connection = DBConfig.getConnection()) {
			String sql = "SELECT * FROM `FOOD_NAME` fn JOIN `FOOD_GROUP` fg ON fg.FoodGroupID = fn.FoodGroupID JOIN `FOOD_SOURCE` fs ON fs.FoodSourceId = fn.FoodSourceId WHERE fn.FoodDescription = ?";
			try (PreparedStatement pState = connection.prepareStatement(sql)) {
				pState.setString(1, foodDescription);
				try (ResultSet resultSet = pState.executeQuery()) {
					while (resultSet.next()) {
						//FOOD NAME TABLE
						int foodID = resultSet.getInt("FoodID");
						String foodDescriptionF = resultSet.getString("FoodDescription");


						//FOOD GROUP TABLE
						int foodGroupID = resultSet.getInt("FoodGroupID");
						String foodGroupName = resultSet.getString("FoodGroupName");
						String foodGroupNameF = resultSet.getString("FoodGroupNameF");

						//FOOD SOURCE TABLE
						int foodSourceID = resultSet.getInt("FoodSourceID");
						String foodSourceDescription = resultSet.getString("FoodSourceDescription");
						String foodSourceDescriptionF = resultSet.getString("FoodSourceDescriptionF");

						foodItem = new FoodItem(foodID, foodGroupID, foodSourceID, foodDescription, foodDescriptionF, foodGroupName, foodGroupNameF, foodSourceDescription, foodSourceDescriptionF);
					}
				}
				return foodItem;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error accessing the database", e);
		}
	}

	// GET TOP 6 FOOD GROUP BY PERCENTAGE List<FoodGroupName, Percentage>
	public static HashMap<String, Double> getTop6FoodGroupByPercentage(int userID){
		HashMap<String, Double> foodGroups = new HashMap<>();
		try (Connection connection = DBConfig.getConnection()) {
			String sql = String.format("SELECT\n" +
					"  FG.FoodGroupName,\n" +
					"  COUNT(FG.FoodGroupID) AS FoodGroupCount,\n" +
					"  (COUNT(FG.FoodGroupID) / (SELECT COUNT(*) FROM FOOD_GROUP) * 100) AS Percentage\n" +
					"FROM USER U\n" +
					"JOIN DATE_LOG D ON D.userID = U.userID\n" +
					"JOIN MEAL_DETAILS M ON D.date_log_id = M.date_log_id\n" +
					"JOIN INGREDIENTS I ON I.meal_id = M.meal_id\n" +
					"JOIN FOOD_NAME FN ON I.FoodID = FN.FoodID\n" +
					"JOIN FOOD_GROUP FG ON FN.FoodGroupID = FG.FoodGroupID\n" +
					"WHERE U.userID = %d\n" +
					"GROUP BY FG.FoodGroupID\n" +
					"ORDER BY Percentage DESC LIMIT 6;\n", userID);
			try (PreparedStatement pState = connection.prepareStatement(sql)) {
				try (ResultSet resultSet = pState.executeQuery()) {
					while (resultSet.next()) {
						String foodGroupName = resultSet.getString("FoodGroupName");
						double percentage = resultSet.getDouble("Percentage");
						foodGroups.put(foodGroupName, percentage);
					}
				}
				return foodGroups;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error accessing the database", e);
		}
	}
	public static boolean userHasRecords(int userID) {
		try (Connection connection = DBConfig.getConnection()) {
			// This SQL query checks across multiple tables. You may need to adjust it based on your schema.
			String sql = "SELECT COUNT(*) AS total_records FROM ("
					+ "SELECT 1 FROM MEAL_DETAILS M JOIN DATE_LOG D ON M.date_log_id = D.date_log_id WHERE D.userID = ? "
					+ "UNION ALL "
					+ "SELECT 1 FROM EXERCISE_LOG E JOIN DATE_LOG D ON E.date_log_id = D.date_log_id WHERE D.userID = ? "
					// Add more UNION ALL SELECT 1 FROM ... WHERE D.userID = ? for other tables where user data might be stored
					+ ") AS combined";

			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setInt(1, userID);
				preparedStatement.setInt(2, userID);
				// Add more preparedStatement.setInt(n, userID); for additional UNION ALL queries

				try (ResultSet rs = preparedStatement.executeQuery()) {
					if (rs.next()) {
						return rs.getInt("total_records") > 0;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error accessing the database", e);
		}
		return false;
	}
}
