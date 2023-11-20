package src.server.DataServices;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import src.model.*;

public class DBQueries {
    //for log in validation
    public static boolean validateUser(String username, String password) {
        // DBConfig dbConfig = new DBConfig();

        try (Connection connection = DBConfig.getConnection()) {
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

    //for sign up validation
    public static boolean createUser(User user) throws SQLIntegrityConstraintViolationException {
        // DBConfig dbConfig = new DBConfig();

        try (Connection connection = DBConfig.getConnection()) {
            String sql = "INSERT INTO USER (username, user_password, fname, lname, sex, dob, weight, height, units) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            System.out.println("SQL Query: " + sql);  // Debugging statement
            System.out.println("User sex: " + user.getSex());

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // preparedStatement.setInt(1, user.getId());
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, String.valueOf(user.getPassword()));
                preparedStatement.setString(3, user.getFirstName());
                preparedStatement.setString(4, user.getLastName());
                preparedStatement.setString(5, user.getSex());
                preparedStatement.setString(6, user.getDateOfBirth());
                preparedStatement.setDouble(7, user.getWeight());
                preparedStatement.setDouble(8, user.getHeight());
                preparedStatement.setString(9, user.getUnits());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("User signed up successfully!");
                    return true;
                } else {
                    return false;
                }
            } catch (SQLIntegrityConstraintViolationException e) {
                // Rethrow the exception if it's a unique constraint violation
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error accessing the database", e);
        }
    }

    //for updating user profile

    //for deleting user profile

    //for getting user profile

    //for getting user log

    //	GETTING NUTRIENT INFO



    // for inserting meal log
    public static void addMeal() {
        Meal meal = new Meal();
        try (Connection connection = DBConfig.getConnection()) {
            String sql = "INSERT INTO MEAL_DETAILS (meal_type, date_log_id) VALUES(?, ?, ?)" +
                    "SELECT LAST_INSERT_ID();";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(0, meal.getMealTypeName());
                preparedStatement.setInt(1, 0);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error accessing the database", e);
        }
    }

    //	LOG DATE
    public static int addDate() {
        Date curr_date = java.sql.Date.valueOf(LocalDate.now());
        try (Connection connection = DBConfig.getConnection()) {
            String sql = "INSERT INTO DATE_LOG (userID, date_log) VALUES(?, ?, ?)" +
                    "SELECT LAST_INSERT_ID() AS date_log_id;";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(0, 0);
                preparedStatement.setDate(1, curr_date);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("date_log_id");
                    }
                }
                throw new RuntimeException("Cannot find last inserted date_log_id");
//				return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error accessing the database", e);
        }
    }

    //	LOG EXERCISE
    public static void addExercise(Exercise exe, int dateLogId) {
        try (Connection connection = DBConfig.getConnection()) {
            String sql = "INSERT INTO EXERCISE_LOG (date_log_id, exercise_type, duration, intensity) VALUES(?, ?, ?);" +
                    "SELECT LAST_INSERT_ID();";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(0, dateLogId);
                preparedStatement.setString(1, exe.getName());
                preparedStatement.setInt(2, exe.getDuration());
                preparedStatement.setString(3, exe.getIntensityName());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error accessing the database", e);
        }
    }

    //	LOG INGREDIENTS
    public static void addIngredients(int mealID, Ingredient ingre) {
        try (Connection connection = DBConfig.getConnection()) {
            String sql = "INSERT INTO EXERCISE_LOG (meal_id, name, quantity, unit) VALUES(?, ?, ?);" +
                    "SELECT LAST_INSERT_ID();";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(0, mealID);
                preparedStatement.setString(1, ingre.getName());
                preparedStatement.setInt(2, ingre.getQuantity());
                preparedStatement.setString(3, ingre.getUnit());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error accessing the database", e);
        }
    }


    //	GET INGREDIENTS NUTRIENT INFO
//	public static


    //	GET FOOD GROUP
    public static String[] getFoodGroup() {
        String[] foodGroup = new String[25];
        try (Connection connection = DBConfig.getConnection()) {
            String sql = "SELECT FoodGroupName FROM `FOOD_GROUP`";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        foodGroup[resultSet.getRow()] = resultSet.getString("FoodGroupName");
                    }
                }
                return foodGroup;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error accessing the database", e);
        }
    }

//	public static double getNutrientInfo() {
//		try (Connection connection = getConnection()) {
//			String sql = ""
//		}
//	}


    //	GET Exercise List
    public static String[] getExerciseList() {
        String[] exercisetypes = new String[35];
        try (Connection connection = DBConfig.getConnection()) {
            String sql = "SELECT CATEGORIES FROM `METvalues`";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        exercisetypes[resultSet.getRow()] = resultSet.getString("CATEGORIES");
                    }
                }
                return exercisetypes;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error accessing the database", e);
        }
    }


    //	GET MET VALUES BASED ON EXERCISE INTENSITY
    public static double getMETLow(String exercisetype) throws SQLException {
        double metLow = 0;
        Connection connection = DBConfig.getConnection();
        String sql = String.format("SELECT LOW FROM `METvalues` WHERE CATEGORIES = '%s'", exercisetype);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            metLow = resultSet.getDouble("LOW");
        }
        preparedStatement.close();
        resultSet.close();
        return metLow;
    }

    public static double getMETMed(String exercisetype) throws SQLException {
        double metMed = 0;
        Connection connection = DBConfig.getConnection();
        String sql = String.format("SELECT MEDIUM FROM `METvalues` WHERE CATEGORIES = '%s'", exercisetype);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            metMed = resultSet.getDouble("MEDIUM");
        }
        preparedStatement.close();
        resultSet.close();
        return metMed;
    }

    public static double getMETHigh(String exercisetype) throws SQLException {
        double metHigh = 0;
        Connection connection = DBConfig.getConnection();
        String sql = String.format("SELECT HIGH FROM `METvalues` WHERE CATEGORIES = '%s'", exercisetype);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            metHigh = resultSet.getDouble("HIGH");
        }
        preparedStatement.close();
        resultSet.close();
        return metHigh;
    }




    //GET MET VALUES BASED ON INTENSITY
//	public static double getMETvalue(String intensity, String exercisetype) {
//		System.out.println("Intensity: " + intensity + ", Exercise Type: " + exercisetype);
//
//		try (Connection connection = getConnection()) {
////			Exercise e = new Exercise();
////			exercisetype = e.getName();
//
//			if (intensity.equals(Exercise.Intensity.low)) {
//
//				double metLow = 0;
////				String sql = String.format("SELECT 'LOW' FROM `METvalues` WHERE CATEGORIES = '%s'", exercisetype);
//				String sql = String.format("SELECT `LOW` FROM METvalues WHERE CATEGORIES='Climbing'");
//				try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//					try (ResultSet resultSet = preparedStatement.executeQuery()) {
//						while (resultSet.next()) {
//							metLow = resultSet.getDouble("LOW");
//							System.out.println(metLow);
//						}
//					}
//
//					return metLow;
//				}
//			} else if (intensity.equals(Exercise.Intensity.medium)) {
//				double metMed = 0;
//				String sql = String.format("SELECT 'MEDIUM' FROM `METvalues` WHERE CATEGORIES = '%s'", exercisetype);
//				try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//					try (ResultSet resultSet = preparedStatement.executeQuery()) {
//						while (resultSet.next()) {
//							metMed = resultSet.getDouble("MEDIUM");
//						}
//					}
//					return metMed;
//				}
//			} else if (intensity.equals(Exercise.Intensity.high)) {
//				double metHigh = 0;
//				String sql = String.format("SELECT 'HIGH' FROM `METvalues` WHERE CATEGORIES = '%s'", exercisetype);
//				try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//					try (ResultSet resultSet = preparedStatement.executeQuery()) {
//						while (resultSet.next()) {
//							metHigh = resultSet.getDouble("HIGH");
//						}
//					}
//					return metHigh;
//				}
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new RuntimeException("Error accessing the database", e);
//		}
//		return 2;
//	}


}

//		if (intensity.equals(Exercise.Intensity.low)) {
//			double metLow = 0;
//			try (Connection connection = getConnection()) {
//				Exercise e = new Exercise();
//				exercisetype = e.getName();
//				String sql = String.format("SELECT LOW FROM `METvalues` WHERE CATEGORIES = '%s'", exercisetype);
//				try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//					try (ResultSet resultSet = preparedStatement.executeQuery()) {
//						while (resultSet.next()) {
//							metLow = resultSet.getDouble("LOW");
//						}
//					}
//					System.out.println(metLow);
//					return metLow;
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//				throw new RuntimeException("Error accessing the database", e);
//			}
//
//		}
//
//		else if (intensity.equals(Exercise.Intensity.medium)) {
//			double metMed = 0;
//			try (Connection connection = getConnection()) {
//				Exercise e = new Exercise();
//				exercisetype = e.getName();
//				String sql = String.format("SELECT MEDIUM FROM `METvalues` WHERE CATEGORIES = '%s'", exercisetype);
//				try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//					try (ResultSet resultSet = preparedStatement.executeQuery()) {
//						while (resultSet.next()) {
//							metMed = resultSet.getDouble("MEDIUM");
//						}
//					}
//					return metMed;
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//				throw new RuntimeException("Error accessing the database", e);
//			}
//		}
//
//		else if (intensity.equals(Exercise.Intensity.high)) {
//			double metHigh = 0;
//			try (Connection connection = getConnection()) {
//				Exercise e = new Exercise();
//				exercisetype = e.getName();
//				String sql = String.format("SELECT HIGH FROM `METvalues` WHERE CATEGORIES = '%s'", exercisetype);
//				try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//					try (ResultSet resultSet = preparedStatement.executeQuery()) {
//						while (resultSet.next()) {
//							metHigh = resultSet.getDouble("HIGH");
//						}
//					}
//					return metHigh;
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//				throw new RuntimeException("Error accessing the database", e);
//			}
//		}
//		return 1;
//	}

