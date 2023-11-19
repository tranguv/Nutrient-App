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
	// public static int logExercise(DateLog date, Exercise exercise) {
	// 	String sqlInsert = "INSERT INTO `EXERCISE_LOG` (`date_log_id`, `exercise_type`, `duration`, `intensity`) VALUES (?, ?, ?, ?)";
	// 	String sqlSelect = "SELECT LAST_INSERT_ID()";

	// 	try (Connection connection = DBConfig.getConnection()) {
	// 		try (PreparedStatement insertStatement = connection.prepareStatement(sqlInsert)) {
	// 			insertStatement.setInt(1, date.getDateLogId());
	// 			insertStatement.setString(2, exercise.getExerciseType());
	// 			insertStatement.setInt(3, exercise.getDuration());
	// 			insertStatement.setString(4, exercise.getIntensity());
	// 			insertStatement.executeUpdate();
	// 		}

	// 		try (PreparedStatement selectStatement = connection.prepareStatement(sqlSelect)) {
	// 			try (ResultSet rs = selectStatement.executeQuery()) {
	// 				if (rs.next()) {
	// 					return rs.getInt(1); // Use the appropriate column index
	// 				} else {
	// 					throw new RuntimeException("Cannot find last inserted meal_id");
	// 				}
	// 			}
	// 		}
	// 	} catch (SQLException e) {
	// 		e.printStackTrace();
	// 		throw new RuntimeException("Error accessing the database", e);
	// 	}
	// }


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

}
