package src.server.DataServices;

import src.model.Intensity;
import src.model.User;

import java.sql.*;


//    GET CALORIES INFO
//    public static double getKcal(User user) {
//        double kcal = 0;
//        try (Connection connection = DBConfig.getConnection()) {
//            String sql = ""
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import src.model.DateLog;
import src.model.Exercise;
import java.util.ArrayList;
import java.sql.Connection;
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

	// GET CALORIES EXPENDED FOR ALL EXERCISES LAST 365 DAYS
	public static double getCaloriesExpended(int userId){
		double caloriesExpended = 0;
		String sql = String.format("SELECT SUM(\n" +
				"        CASE\n" +
				"            WHEN UPPER(E.intensity) = 'MEDIUM' THEN M.MEDIUM\n" +
				"            WHEN UPPER(E.intensity) = 'HIGH' THEN M.HIGH\n" +
				"            WHEN UPPER(E.intensity) = 'LOW' THEN M.LOW\n" +
				"            ELSE 0  -- You can handle other cases or set a default value\n" +
				"        END * E.duration * (\n" +
				"        CASE\n" +
				"\t\t\tWHEN U.sex = 'F' THEN 88.362+(13.397*U.weight)+(4.799*U.height)-(5.677* TIMESTAMPDIFF(YEAR, U.dob, NOW()))\n" +
				"            WHEN U.sex = 'M' THEN 447.593+(9.247*U.weight)+(3.098*U.height)-(4.330* TIMESTAMPDIFF(YEAR, U.dob, NOW()))\n" +
				"\t\tEND)\n" +
				"    ) AS total_met_values\n" +
				"FROM USER U\n" +
				"JOIN DATE_LOG D ON D.userID = U.userID\n" +
				"JOIN EXERCISE_LOG E ON E.date_log_id = D.date_log_id\n" +
				"JOIN METvalues M ON M.CATEGORIES = E.exercise_type\n" +
				"WHERE\n" +
				"    E.date_log_id IN (\n" +
				"        SELECT date_log_id\n" +
				"        FROM DATE_LOG\n" +
				"        WHERE userID = %d AND date_log >= DATE_SUB(NOW(), INTERVAL 365 DAY) AND date_log <= NOW()\n" +
				"    );", userId);
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



	// GET NUMBER OF EXERCISES ALL THE TIME
	public static int getNumberOfExercises(int userId){
		int numberOfExercises = 0;
		String sql = String.format("SELECT COUNT(*) AS total_exercises\n" +
				"FROM EXERCISE_LOG E\n" +
				"JOIN DATE_LOG D ON E.date_log_id = D.date_log_id\n" +
				"WHERE D.userID = %d", userId);
		try(Connection connection = DBConfig.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				numberOfExercises = resultSet.getInt("total_exercises");
			}
			preparedStatement.close();
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return numberOfExercises;
	}

	public static boolean userHasExerciseRecords(int userID) {
		try (Connection connection = DBConfig.getConnection()) {
			// Query to check if the user has any entries in the EXERCISE_LOG table
			String sql = "SELECT COUNT(*) AS total_records FROM EXERCISE_LOG E JOIN DATE_LOG D ON E.date_log_id = D.date_log_id WHERE D.userID = ?";

			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setInt(1, userID);

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

	public static void main(String[] args) {
		String start = "2003-01-01";
		String end = "2025-12-31";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(start, formatter);
		LocalDate localDate2 = LocalDate.parse(end, formatter);
		Date startDate = Date.valueOf(localDate);
		Date endDate = Date.valueOf(localDate2);
		ArrayList<Exercise> exercises = getExercisesByDate(2, startDate, endDate);
		for (Exercise exercise : exercises) {
			System.out.println(exercise.getName() + " " + exercise.getCaloriesBurnt() + " " + exercise.getDate());
		}
	}

	// GET EXERCISES ACCORDING TO START AND END DATE
	public static ArrayList<Exercise> getExercisesByDate(int userId, Date startDate, Date endDate) {
		ArrayList<Exercise> exercises = new ArrayList<>();
		String sql = String.format("SELECT E.exercise_log_id, E.exercise_type, E.duration, E.intensity, E.date_log_id, D.date_log\n" +
				"FROM EXERCISE_LOG E\n" +
				"JOIN DATE_LOG D ON E.date_log_id = D.date_log_id\n" +
				"WHERE D.userID = %d AND D.date_log >= '%s' AND D.date_log <= '%s'", userId, startDate, endDate);
		try(Connection connection = DBConfig.getConnection()){
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Exercise exerciseLog = new Exercise();
				exerciseLog.setId(resultSet.getInt("exercise_log_id"));
				exerciseLog.setName(resultSet.getString("exercise_type"));
				exerciseLog.setDuration(resultSet.getInt("duration"));
				exerciseLog.setIntensity(Intensity.valueOf(resultSet.getString("intensity")));
				exerciseLog.setCaloriesBurnt(getCaloriesExpended(exerciseLog.getName(), exerciseLog.getDuration(), exerciseLog.getIntensity().toString().toUpperCase()));
				exerciseLog.setDate(String.valueOf(resultSet.getDate("date_log")));
				exercises.add(exerciseLog);
			}
			preparedStatement.close();
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exercises;
	}
}
