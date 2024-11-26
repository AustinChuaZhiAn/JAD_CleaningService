package Controller;

import Model.CategoryDAOImpl;
import Model.UserAccount;
import Model.UserDetails;
import Model.UserAccountSQL;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
@WebServlet("/AdminController")
public class AdminController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserAccountSQL userDAO;
    private CategoryDAOImpl categoryDAO;
       
    public AdminController() {
        super();
        userDAO = new UserAccountSQL();
        categoryDAO = new CategoryDAOImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        if (session.getAttribute("role_id") == null || 
            !session.getAttribute("role_id").equals(1)) {
            response.sendRedirect(request.getContextPath() + "/View/Login.jsp");
            return;
        }

        try {
            String action = request.getParameter("action");
            if (action == null) {
                // Regular dashboard display if no action
                displayDashboard(request, response);
                return;
            }

            switch (action) {
                case "list":
                    showUserList(request, response);
                    break;
                case "deleteUser":
                	deleteUser(request,response);
                	break;
                default:
                    displayDashboard(request, response);
                    break;
            }

        } catch (SQLException e) {
            handleError(request, response, e);
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
                    case "updateUser":
                        updateUser(request, response);
                        break;
                    case "deleteUser":
                        deleteUser(request, response);
                        break;
                    default:
                        response.sendRedirect(request.getContextPath() + "/View/Home.jsp");
                        break;
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/View/Home.jsp");
            }
        } catch (SQLException ex) {
            handleError(request, response, ex);
        }
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
                case 1: // Admin Login
                    response.sendRedirect(request.getContextPath() + "/AdminController");
                    break;
                case 2: // Member Login
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

    private void updateUser(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        HttpSession session = request.getSession();
        try {
            int userId = Integer.parseInt(request.getParameter("user_id"));
            int roleId = Integer.parseInt(request.getParameter("role_id"));

            // Prevent changing own role
            if (userId == (Integer)session.getAttribute("user_id")) {
                session.setAttribute("error", "Cannot modify your own role");
            } else if (userDAO.updateUserRole(userId, roleId)) {
                session.setAttribute("success", "User role updated successfully");
            } else {
                session.setAttribute("error", "Failed to update user role");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Invalid user ID or role ID");
        }
        
        response.sendRedirect(request.getContextPath() + "/AdminController?action=list");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        HttpSession session = request.getSession();
        try {
            int userId = Integer.parseInt(request.getParameter("id"));
            System.out.println("Attempting to delete user ID: " + userId);
            System.out.println("Current user ID: " + session.getAttribute("user_id")); 
            // Prevent self-deletion
            if (userId == (Integer)session.getAttribute("user_id")) {
                session.setAttribute("error", "Cannot delete your own account");
            } else if (userDAO.deleteUser(userId)) {
                session.setAttribute("success", "User deleted successfully");
            } else {
                session.setAttribute("error", "Failed to delete user");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Invalid user ID");
        }
        
        response.sendRedirect(request.getContextPath() + "/AdminController?action=list");
    }

    private void showUserList(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        ArrayList<UserAccount> users = userDAO.getAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/View/admin/Users.jsp").forward(request, response);
    }

    private void displayDashboard(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        int userCount = userDAO.getTotalUser();
        int categoryCount = categoryDAO.getTotalCategory(); 
        request.setAttribute("userCount", userCount);
        request.setAttribute("categoryCount", categoryCount); 
        request.getRequestDispatcher("/View/admin/AdminPage.jsp").forward(request, response);
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, Exception e) 
            throws ServletException, IOException {
        e.printStackTrace();
        request.getSession().setAttribute("error", "An error occurred: " + e.getMessage());
        response.sendRedirect(request.getContextPath() + "/AdminController?userConfig");
    }
}