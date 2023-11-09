package src.viewmodel.DataServices;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBConfig {
    private String url;
    private String username;
    private String password;
    private String driver;

    public DBConfig() {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("C:\\Users\\austi\\OneDrive\\Documents\\GitHub\\Nutrient-App\\Nutrient_App\\src\\dbconfig.properties");
            prop.load(input);

            this.url = prop.getProperty("db.url");
            this.username = prop.getProperty("db.username");
            this.password = prop.getProperty("db.password");
            this.driver = prop.getProperty("db.driver");
        } catch (IOException ex) {
            ex.printStackTrace();
            // Handle the exception (e.g., log the error, throw a specific exception, etc.)
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDriver() {
        return driver;
    }
}
