package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import Model.UserAccount;
import Model.UserAccountCRUD;
import Model.UserAccountSQL;
import Model.UserDetails;

@WebServlet("/UserController")
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserAccountCRUD userDAO;
       
    public UserController() {
        super();
        userDAO = new UserAccountSQL();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            switch (action == null ? "list" : action) {
                case "list":
                    listUsers(request, response);
                    break;
                case "showEdit":
                    showEditForm(request, response);
                    break;
                case "showCreate":
                    showCreateForm(request, response);
                    break;
                case "verify":
                    verifyUser(request, response);
                    break;
                default:
                    listUsers(request, response);
                    break;
            }
        } catch (SQLException ex) {
            handleError(request, response, "Database error: " + ex.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            switch (action) {
                case "create":
                    createUser(request, response);
                    break;
                case "edit":
                    updateUser(request, response);
                    break;
                case "delete":
                    deleteUser(request, response);
                    break;
                case "verify":
                    verifyUser(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/View/admin/Users.jsp");
                    break;
            }
        } catch (SQLException ex) {
            handleError(request, response, "Database error: " + ex.getMessage());
        }
    }
    
    private void listUsers(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        try {
            List<UserAccount> users = userDAO.getAllUser();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/View/admin/Users.jsp").forward(request, response);
        } catch (SQLException e) {
            handleError(request, response, "Error retrieving users: " + e.getMessage());
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        try {
            String username = request.getParameter("username");
            if (username == null || username.trim().isEmpty()) {
                handleError(request, response, "Username parameter is required");
                return;
            }

            UserAccount user = userDAO.getUserByUsername(username);
            if (user == null) {
                handleError(request, response, "User not found");
                return;
            }

            request.setAttribute("userAccount", user);
            request.setAttribute("action", "edit");
            request.getRequestDispatcher("/View/admin/UserForm.jsp").forward(request, response);
        } catch (SQLException e) {
            handleError(request, response, "Error retrieving user details: " + e.getMessage());
        }
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setAttribute("action", "create");
        request.getRequestDispatcher("/View/admin/UserForm.jsp").forward(request, response);
    }

    private void createUser(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        try {
            // Validate input
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String phoneNumber = request.getParameter("phone_number");
            int roleId = Integer.parseInt(request.getParameter("role_id"));

            if (username == null || username.trim().isEmpty() || 
                password == null || password.trim().isEmpty() || 
                email == null || email.trim().isEmpty()) {
                setSessionMessage(request, "error", "All required fields must be filled out");
                response.sendRedirect(request.getContextPath() + "/View/admin/UserForm.jsp");
                return;
            }

            // Check if username exists
            if (userDAO.getUserByUsername(username) != null) {
                setSessionMessage(request, "error", "Username already exists");
                response.sendRedirect(request.getContextPath() + "/View/admin/UserForm.jsp");
                return;
            }

            // Check if email exists
            if (userDAO.getUserDetailsByEmail(email) != null) {
                setSessionMessage(request, "error", "Email already registered");
                response.sendRedirect(request.getContextPath() + "/View/admin/UserForm.jsp");
                return;
            }

            // Validate phone number if provided
            if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
                if (!phoneNumber.matches("\\d+")) {
                    setSessionMessage(request, "error", "Phone number must contain only numbers");
                    response.sendRedirect(request.getContextPath() + "/View/admin/UserForm.jsp");
                    return;
                }
                if (userDAO.getUserDetailsByPhone(phoneNumber) != null) {
                    setSessionMessage(request, "error", "Phone number already registered");
                    response.sendRedirect(request.getContextPath() + "/View/admin/UserForm.jsp");
                    return;
                }
            }

            // Create user objects
            UserAccount newUser = new UserAccount();
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setRole_id(roleId);

            UserDetails userDetails = new UserDetails();
            userDetails.setEmail(email);
            userDetails.setPhone_number(phoneNumber);

            // Create user
            if (userDAO.createUser(newUser, userDetails)) {
                setSessionMessage(request, "success", "User created successfully");
                response.sendRedirect(request.getContextPath() + "/View/admin/Users.jsp");
            } else {
                setSessionMessage(request, "error", "Failed to create user");
                response.sendRedirect(request.getContextPath() + "/View/admin/UserForm.jsp");
            }
        } catch (NumberFormatException e) {
            setSessionMessage(request, "error", "Invalid role selected");
            response.sendRedirect(request.getContextPath() + "/View/admin/UserForm.jsp");
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        try {
            // Get and validate input
            int userId = Integer.parseInt(request.getParameter("user_id"));
            String email = request.getParameter("email");
            String phoneNumber = request.getParameter("phone_number");
            int roleId = Integer.parseInt(request.getParameter("role_id"));

            if (email == null || email.trim().isEmpty()) {
                setSessionMessage(request, "error", "Email is required");
                response.sendRedirect(request.getContextPath() + "/View/admin/UserForm.jsp?id=" + userId);
                return;
            }

            // Validate phone number if provided
            if (phoneNumber != null && !phoneNumber.trim().isEmpty() && !phoneNumber.matches("\\d+")) {
                setSessionMessage(request, "error", "Phone number must contain only numbers");
                response.sendRedirect(request.getContextPath() + "/View/admin/UserForm.jsp?id=" + userId);
                return;
            }

            // Update user
            UserAccount user = new UserAccount();
            user.setUser_id(userId);
            user.setRole_id(roleId);

            if (userDAO.updateUser(user)) {
                setSessionMessage(request, "success", "User updated successfully");
                response.sendRedirect(request.getContextPath() + "/View/admin/Users.jsp");
            } else {
                setSessionMessage(request, "error", "Failed to update user");
                response.sendRedirect(request.getContextPath() + "/View/admin/UserForm.jsp?id=" + userId);
            }
        } catch (NumberFormatException e) {
            setSessionMessage(request, "error", "Invalid user ID or role");
            response.sendRedirect(request.getContextPath() + "/View/admin/Users.jsp");
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        try {
            int userId = Integer.parseInt(request.getParameter("id"));
            
            if (userDAO.deleteUser(userId)) {
                setSessionMessage(request, "success", "User deleted successfully");
            } else {
                setSessionMessage(request, "error", "Failed to delete user");
            }
            response.sendRedirect(request.getContextPath() + "/View/admin/Users.jsp");
        } catch (NumberFormatException e) {
            setSessionMessage(request, "error", "Invalid user ID");
            response.sendRedirect(request.getContextPath() + "/View/admin/Users.jsp");
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
                case 1: //Admin Login
                    response.sendRedirect(request.getContextPath() + "/View/admin/Dashboard.jsp");
                    break;
                case 2: //Member Login
                    response.sendRedirect(request.getContextPath() + "/View/Home.jsp");
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/View/Home.jsp");
            }
        } else {
            setSessionMessage(request, "error", "Invalid username or password");
            response.sendRedirect(request.getContextPath() + "/View/Login.jsp");
        }
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, String errorMessage) 
            throws ServletException, IOException {
        setSessionMessage(request, "error", errorMessage);
        response.sendRedirect(request.getContextPath() + "/View/admin/Users.jsp");
    }

    private void setSessionMessage(HttpServletRequest request, String type, String message) {
        HttpSession session = request.getSession();
        session.setAttribute(type, message);
    }
}