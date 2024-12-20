package utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static Properties props = new Properties();
    
    static {
        try {
            InputStream input = DatabaseConnection.class.getClassLoader()
                    .getResourceAsStream("JAD.properties");
                    
            if (input == null) {
                throw new RuntimeException("Unable to find JAD.properties");
            }
            
            props.load(input);
            Class.forName(props.getProperty("db.driver"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load database properties", e);
        }
    }
    
    // Private constructor to prevent instantiation
    private DatabaseConnection() {}
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            props.getProperty("db.url") +
            "?user=" + props.getProperty("db.user") +
            "&password=" + props.getProperty("db.password") +
            "&serverTimezone=" + props.getProperty("db.timezone")
        );
    }
    
    public static Properties getProperties() {
        return props;
    }
}