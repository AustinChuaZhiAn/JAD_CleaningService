package utils;

import java.sql.*;

public class DatabaseConnection {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/JAD_Project";
    private static final String USER = "root";
    private static final String PASSWORD = "mypassword";
    private static final String TIMEZONE = "serverTimezone=UTC";
    
    private DatabaseConnection() {}
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(
                URL + "?user=" + USER + 
                "&password=" + PASSWORD + 
                "&" + TIMEZONE
            );
        } catch (ClassNotFoundException e) {
            throw new SQLException("Database driver not found: " + e.getMessage());
        }
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