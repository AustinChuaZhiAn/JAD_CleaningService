package Controller;

import Model.CategoryDAOImpl;
import Model.UserAccount;
import Model.UserAccountSQL;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/AdminController")
public class AdminController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CategoryDAOImpl categoryDAO;
    private UserAccountSQL userDAO;

       
    public AdminController() {
        super();
        categoryDAO = new CategoryDAOImpl();
        userDAO = new UserAccountSQL();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        // Changed to check role_id instead of userId and role
        if (session.getAttribute("role_id") == null || 
            !session.getAttribute("role_id").equals(1)) {
            response.sendRedirect(request.getContextPath() + "/View/Login.jsp");
            return;
        }

        try {
            // Get counts from respective DAOs
            int userCount = userDAO.getTotalUser();
            int categoryCount = categoryDAO.getTotalCategory();

            // Set attributes for the JSP
            request.setAttribute("userCount", userCount);
            request.setAttribute("categoryCount", categoryCount);

            // Forward to admin dashboard JSP
            request.getRequestDispatcher("/View/admin/AdminPage.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            // Set error message
            request.setAttribute("errorMessage", "Error loading dashboard data: " + e.getMessage());
            request.getRequestDispatcher("/View/Home.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
         
        try {
            if (action != null) {
                switch(action) {
                    case "verify":
                        verifyUser(request, response);
                        break;
                    default:
                        response.sendRedirect(request.getContextPath() + "/View/Home.jsp");
                        break;
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/View/Home.jsp");
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
        // Remove the doGet(request, response) call
    }
    
    private void verifyUser(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
            
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        UserAccount user = userDAO.verifyUser(username, password);
        
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user_id", user.getUser_id());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role_id", user.getRole_id());
            
            switch (user.getRole_id()) {
                case 1: //Admin Login
                    // Instead of direct JSP redirect, redirect to the servlet
                    response.sendRedirect(request.getContextPath() + "/AdminController");
                    break;
                case 2: //Member Login
                    response.sendRedirect(request.getContextPath() + "/View/Home.jsp");
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/View/Home.jsp");
            }
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("error", "Invalid username or password");  
            response.sendRedirect(request.getContextPath() + "/View/Login.jsp"); 
        }
    }
    
}
