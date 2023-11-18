package src.server.DataServices;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import src.model.User;

public class DateQueries {
    private static DBConfig dbConfig = new DBConfig();

	private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUsername(), dbConfig.getPassword());
    }

	//RETRIEVE DATE LOG ID BY USER ID AND DATE IF EXISTS
	public static int getDateLogId(User user, Date date) {
		try (Connection connection = getConnection()) {
			String sql = "SELECT date_log_id FROM DATE_LOG WHERE userID = ? AND date_log = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setInt(1, UserQueries.getUserIDbyUsername(user.getUsername()));
				preparedStatement.setDate(2, date);
				try (ResultSet rs = preparedStatement.executeQuery()) {
					if (rs.next()) {
						return rs.getInt("date_log_id");
					}
				}
				throw new RuntimeException("Cannot find date_log_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error accessing the database", e);
		}
	}

    //INSERT NEW DATE LOG IF NOT EXISTS
	public static int addDate(User user, Date date) {
		// Date curr_date = java.sql.Date.valueOf(LocalDate.now());
		try (Connection connection = getConnection()) {
			String sql = "INSERT INTO DATE_LOG (userID, date_log) VALUES(?, ?, ?)" +
					"SELECT LAST_INSERT_ID() AS date_log_id;";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setInt(0, 0);
				preparedStatement.setDate(1, date);
				try (ResultSet rs = preparedStatement.executeQuery()) {
					if (rs.next()) {
						return rs.getInt("date_log_id");
					}
				}
				throw new RuntimeException("Cannot find last inserted date_log_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error accessing the database", e);
		}
	}

}
