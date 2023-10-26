package viewmodel;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.Date;
//
//public class DatabaseConnect {
//    private Connection connect = null;
//    private Statement statement = null;
//    private PreparedStatement preparedStatement = null;
//    private ResultSet resultSet = null;
//
//    public void readDataBase() throws Exception {
//        try {
//            // This will load the MySQL driver, each DB has its own driver
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            // Setup the connection with the DB
//            connect = DriverManager
//                    .getConnection("jdbc:mysql://localhost:3306/database_3311", "root", "Trang@2003"); //third column for password
//
//            // Statements allow to issue SQL queries to the database
//            Statement stmnt = connect.createStatement();
//            stmnt
//                    .execute(
//                    		" LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/18100205.csv' "
//                    		+ " INTO TABLE NHPI "
//                    		+ " FIELDS TERMINATED BY ',' "
//                    		+ " ENCLOSED BY '\"'"
//                    		+ " LINES TERMINATED BY '\n'"
//                    		+ " IGNORE 1 ROWS;");
//
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            close();
//        }
//
//    }
//
//    // You need to close the resultSet
//    private void close() {
//        try {
//            if (resultSet != null) {
//                resultSet.close();
//            }
//
//            if (statement != null) {
//                statement.close();
//            }
//
//            if (connect != null) {
//                connect.close();
//            }
//        } catch (Exception e) {
//
//        }
//    }
//    
//    public static void main(String[] args) throws Exception {
//    	DatabaseConnect d = new DatabaseConnect();
//		d.readDataBase();
//	}
//
//}

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class DatabaseConnect {
	
	public static void main(String[] args) {
		 String url = "jdbc:mysql://127.0.0.1:3306/FRISBEE";
		 String username = "root";
		 String password = "Qj329032003!";

		 System.out.println("Connecting database...");

		 try (Connection connection = DriverManager.getConnection(url, username, password)) {
		     System.out.println("Database connected!");
		 } catch (SQLException e) {
		     throw new IllegalStateException("Cannot connect the database!", e);
		 }
		 
		 System.out.println("Loading driver...");

		 try {
           Class.forName("com.mysql.cj.jdbc.Driver");
		     System.out.println("Driver loaded!");
		 } catch (ClassNotFoundException e) {
		     throw new IllegalStateException("Cannot find the driver in the classpath!", e);
		 }
	}
}
