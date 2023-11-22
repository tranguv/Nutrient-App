package src.server.DataServices;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import src.model.DateLog;
import src.model.User;

public class DateQueries {
	//	GET SELECTED DATE INFO BY USER
		public static Date getDatebyUserID(int userID) {
			try (Connection connection = DBConfig.getConnection()) {
				String sql = "SELECT date_log FROM DATE_LOG WHERE userID = ? ";
				try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
					preparedStatement.setInt(1, userID);
	//				preparedStatement.setDate(2, date);
					try (ResultSet rs = preparedStatement.executeQuery()) {
						if (rs.next()) {
							return rs.getDate("date_log");
						}
					}
					throw new RuntimeException("Cannot find date_log");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("Error accessing the database", e);
			}
		}

	//RETRIEVE DATE LOG ID BY USER ID AND DATE IF EXISTS
	public static int getDateLogId(User user, Date date) {
		try (Connection connection = DBConfig.getConnection()) {
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

	//	GET SELECTED DATE INFO BY USER
//	public static Date getDate(User user) {
//		try (Connection connection = DBConfig.getConnection()) {
//			String sql = "SELECT date_log FROM DATE_LOG WHERE userID = ? ";
//			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//				preparedStatement.setInt(1, UserQueries.getUserIDbyUsername(user.getUsername()));
////				preparedStatement.setDate(2, date);
//				try (ResultSet rs = preparedStatement.executeQuery()) {
//					if (rs.next()) {
//						return rs.getDate("date_log");
//					}
//				}
//				throw new RuntimeException("Cannot find date_log");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new RuntimeException("Error accessing the database", e);
//		}
//	}


	//INSERT NEW DATE LOG IF NOT EXISTS
//	public static int addDate(User user, Date date) {

    //	LOG DATE



		//	LOG DATE
		public static int addDate (DateLog dateLog){

			// Date curr_date = java.sql.Date.valueOf(LocalDate.now());
			try (Connection connection = DBConfig.getConnection()) {
				String sql = "INSERT INTO DATE_LOG (userID, date_log) VALUES(?, ?)";

				try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					String formattedDate = dateFormat.format(dateLog.getDate());

					preparedStatement.setInt(1, dateLog.getUserID());
					System.out.println("dateLog.getDate().toString(): " + dateLog.getDate().toString());
					System.out.println("dateLog.getUserID(): " + dateLog.getUserID());
					preparedStatement.setDate(2, java.sql.Date.valueOf(formattedDate));
					preparedStatement.executeUpdate();
					try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
						if (rs.next()) {
							return rs.getInt(1);
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

	}



