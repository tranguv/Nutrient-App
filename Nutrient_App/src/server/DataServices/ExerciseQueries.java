package src.server.DataServices;

import java.util.List;

import src.model.DateLog;
import src.model.Exercise;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExerciseQueries {
    //	GET Exercise List
	public static List<String> getExerciseList() {
		List<String> exercisetypes = new ArrayList<>();
		try (Connection connection = DBConfig.getConnection()) {
			String sql = "SELECT CATEGORIES FROM `METvalues`";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						exercisetypes.add(resultSet.getString("CATEGORIES"));
					}
				}
				return exercisetypes;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error accessing the database", e);
		}
	}

	// LOG EXERCISE
	public static int logExercise(DateLog date, Exercise exercise) {
		String sqlInsert = "INSERT INTO `EXERCISE_LOG` (`date_log_id`, `exercise_type`, `duration`, `intensity`) VALUES (?, ?, ?, ?)";
		String sqlSelect = "SELECT LAST_INSERT_ID()";

		try (Connection connection = DBConfig.getConnection()) {
			try (PreparedStatement insertStatement = connection.prepareStatement(sqlInsert)) {
				insertStatement.setInt(1, date.getDateLogId());
				insertStatement.setString(2, exercise.getName());
				insertStatement.setInt(3, exercise.getDuration());
				insertStatement.setString(4, exercise.getIntensity().toString());
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

	// GET CALORIES EXPENDED FOR 1 EXERCISE
	public static double getCaloriesExpended(String exercisetype, int duration, String intensity) throws SQLException {
		double caloriesExpended = 0;
		double met = 0;
		if (intensity.equals("LOW")) {
			met = getMETLow(exercisetype);
		} else if (intensity.equals("MEDIUM")) {
			met = getMETMed(exercisetype);
		} else if (intensity.equals("HIGH")) {
			met = getMETHigh(exercisetype);
		}
		caloriesExpended = met * duration;
		return caloriesExpended;
	}

	// GET CALORIES EXPENDED FOR ALL EXERCISES LAST 30 DAYS
	public static double getCaloriesExpended(int userId){
		double caloriesExpended = 0;
		String sql = String.format("SELECT SUM(CASE WHEN UPPER(E.intensity) = 'MEDIUM' THEN M.MEDIUM\n" +
						"                WHEN UPPER(E.intensity) = 'HIGH' THEN M.HIGH\n" +
						"                WHEN UPPER(E.intensity) = 'LOW' THEN M.LOW\n" +
						"                ELSE 0 END * E.duration) AS total_met_values\n" +
						"FROM EXERCISE_LOG E\n" +
						"JOIN METvalues M ON M.CATEGORIES = E.exercise_type\n" +
						"WHERE date_log_id IN (\n" +
						"    SELECT date_log_id\n" +
						"    FROM DATE_LOG\n" +
						"    WHERE userID = %d AND date_log BETWEEN DATE_SUB(NOW(), INTERVAL 30 DAY) AND NOW()\n" +
						");\n", userId);
		try(Connection connection = DBConfig.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				caloriesExpended = resultSet.getDouble("total_met_values");
			}
			preparedStatement.close();
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return caloriesExpended;
	}

	//GET ALL CALORIES INTAKE FOR LAST 30 DAYS
	public static double getCaloriesIntake(int userId){
		double caloriesIntake = 0;
		String sql = "SELECT SUM(C.calories) AS total_calories\n" +
						"FROM MEAL_LOG M\n" +
						"JOIN MEAL M2 ON M.meal_id = M2.meal_id\n" +
						"JOIN CALORIES C ON M2.meal_type = C.meal_type\n" +
						"WHERE date_log_id IN (\n" +
						"    SELECT date_log_id\n" +
						"    FROM DATE_LOG\n" +
						"    WHERE userID = 2 AND date_log BETWEEN DATE_SUB(NOW(), INTERVAL 30 DAY) AND NOW()\n" +
						");\n";
		try(Connection connection = DBConfig.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				caloriesIntake = resultSet.getDouble("total_calories");
			}
			preparedStatement.close();
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return caloriesIntake;
	}
}
