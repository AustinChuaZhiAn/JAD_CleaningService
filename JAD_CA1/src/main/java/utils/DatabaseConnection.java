package utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static Properties props = new Properties();
    
    static {
        try (InputStream input = DatabaseConnection.class.getClassLoader()
                .getResourceAsStream("database.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find database.properties");
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
    
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}

//package utils;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class DatabaseConnection {
//    // Hard-coded values for testing
//    private static final String URL = "jdbc:mysql://localhost:3306/jadproject";
//    private static final String USER = "root";
//    private static final String PASSWORD = "root"; // Change this to your MySQL password
//    
//    public static Connection getConnection() throws SQLException {
//        try {
//            // Load the MySQL driver
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            
//            // Create connection
//            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
//            System.out.println("Database connection successful!");
//            return conn;
//            
//        } catch (ClassNotFoundException e) {
//            throw new SQLException("MySQL Driver not found: " + e.getMessage());
//        } catch (SQLException e) {
//            System.out.println("Database connection failed: " + e.getMessage());
//            throw e;
//        }
//    }
//}