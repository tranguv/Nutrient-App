package src.server.DataServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExerciseQueries {
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

}
