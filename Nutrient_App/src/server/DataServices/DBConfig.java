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
        // Use try-with-resources to ensure that resources are closed properly
        try (InputStream input = new FileInputStream("src/dbconfig.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            String url = prop.getProperty("db.url");
            String username = prop.getProperty("db.username");
            String password = prop.getProperty("db.password");

            // Check if the connection is null or closed before creating a new one
            if (dbConnection == null || dbConnection.isClosed()) {
                dbConnection = DriverManager.getConnection(url, username, password);
            }
            return dbConnection;
        } catch (IOException ex) {
            ex.printStackTrace();
            // Consider re-throwing the exception or handling it more specifically
        } catch (SQLException e) {
            e.printStackTrace();
            // Similar handling as IOException
        }
        return null; // Consider throwing an exception instead of returning null
    }
}
