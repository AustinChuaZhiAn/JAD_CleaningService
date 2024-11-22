package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import utils.DatabaseConnection;

@WebServlet("/TestDatabaseController")
public class TestDatabaseController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html><body>");
        out.println("<h2>Database Connection Test</h2>");
        
        try {
            // Test 1: Check if properties file exists
            out.println("<h3>Step 1: Checking database.properties</h3>");
            String dbPropertiesPath = getServletContext().getRealPath("/WEB-INF/classes/database.properties");
            out.println("Looking for properties file at: " + dbPropertiesPath + "<br>");
            
            // Test 2: Try to get database connection
            out.println("<h3>Step 2: Testing Database Connection</h3>");
            try (Connection conn = DatabaseConnection.getConnection()) {
                if (conn != null) {
                    out.println("<p style='color:green'>Database connection successful!</p>");
                    out.println("URL: " + conn.getMetaData().getURL() + "<br>");
                    out.println("Database: " + conn.getCatalog() + "<br>");
                    out.println("User: " + conn.getMetaData().getUserName() + "<br>");
                }
            } catch (SQLException e) {
                out.println("<p style='color:red'>Database connection failed: " + e.getMessage() + "</p>");
                e.printStackTrace(new PrintWriter(out));
            }
            
        } catch (Exception e) {
            out.println("<p style='color:red'>Error: " + e.getMessage() + "</p>");
            e.printStackTrace(new PrintWriter(out));
        }
        
        out.println("</body></html>");
    }
}