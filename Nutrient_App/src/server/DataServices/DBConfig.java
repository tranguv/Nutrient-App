package src.server.DataServices;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConfig {

    private static Connection dbConnection;

    public static Connection getConnection() {
        Properties prop = new Properties();
        InputStream input = null;

        try {
//            input = new FileInputStream("src\\dbconfig.properties");
            input = new FileInputStream("C:\\Users\\austi\\OneDrive\\Documents\\GitHub\\Nutrient-App\\Nutrient_App\\src\\dbconfig.properties");
            prop.load(input);

            String url = prop.getProperty("db.url");
            String username = prop.getProperty("db.username");
            String password = prop.getProperty("db.password");
            if (dbConnection == null) {
                return DriverManager.getConnection(url, username, password);
            }
            return dbConnection;
        } catch (IOException ex) {
            ex.printStackTrace();
            // Handle the exception (e.g., log the error, throw a specific exception, etc.)
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}

//package src.server.DataServices;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.util.Properties;
//
//public class DBConfig {
//
//    private static Connection dbConnection;
//
//    public static Connection getConnection() {
//        Properties prop = new Properties();
//        InputStream input = null;
//
//        try {
//            input = new FileInputStream("src\\dbconfig.properties");
//            prop.load(input);
//
//            String url = prop.getProperty("db.url");
//            String username = prop.getProperty("db.username");
//            String password = prop.getProperty("db.password");
//            if (dbConnection == null) {
//                return DriverManager.getConnection(url, username, password);
//            }
//            return dbConnection;
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            // Handle the exception (e.g., log the error, throw a specific exception, etc.)
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if (input != null) {
//                try {
//                    input.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return null;
//    }
//}