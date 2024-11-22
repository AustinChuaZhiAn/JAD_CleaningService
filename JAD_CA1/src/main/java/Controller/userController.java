package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import Model.UserAccount;
import Model.UserAccountCRUD;
import Model.UserAccountSQL;
import Model.UserDetails;

@WebServlet("/userController")
public class userController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserAccountCRUD userDAO;
       
    public userController() {
        super();
        userDAO = new UserAccountSQL();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
//        String action = request.getParameter("action");
//        
//        try {
//            if (action != null) {
//                switch(action) {
//                    case "getAll":
//                        getAllUsers(request, response);
//                        break;
//                        
//                    case "viewProfile":
//                        viewProfile(request, response);
//                        break;
//                        
//                    default:
//                        response.sendRedirect(request.getContextPath() + "error.jsp");
//                        break;
//                }
//            } else {
//                response.sendRedirect(request.getContextPath() + "error.jsp");
//            }
//        } catch (SQLException e) {
//		throw new ServletException(ex);
//        }
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
                        
                    case "create":
                        createUser(request, response);
                        break;
//                        
//                    case "update":
//                        updateUser(request, response);
//                        break;
                        
                    default:
                        response.sendRedirect(request.getContextPath() + "error.jsp");
                        break;
                }
            } else {
                response.sendRedirect(request.getContextPath() + "Home.jsp");
            }
        } catch (SQLException ex) {
			throw new ServletException(ex);
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
                    response.sendRedirect(request.getContextPath() + "View/AdminPage.jsp");
                    break;
                case 2: //Member Login
                    response.sendRedirect(request.getContextPath() + "View/Home.jsp");
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "View/Home.jsp");
            }
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("error", "Invalid username or password");  
            response.sendRedirect(request.getContextPath() + "View/Login.jsp"); 
        }
    }
    
    private void createUser(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
                
        UserAccount newUser = new UserAccount();
        newUser.setUsername(request.getParameter("username"));
        newUser.setPassword(request.getParameter("password"));
        newUser.setRole_id(Integer.parseInt(request.getParameter("role_id")));
        
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail(request.getParameter("email"));
        
        // Validate phone number if provided
        String phoneNumber = request.getParameter("phone_number");
        if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
            //number check
            if (!phoneNumber.matches("\\d+")) {
                HttpSession session = request.getSession();
                session.setAttribute("error", "Phone number must contain only numbers");
                response.sendRedirect(request.getContextPath() + "View/Register.jsp");
                return;
            }
            if (userDAO.getUserDetailsByPhone(phoneNumber) != null) {
                HttpSession session = request.getSession();
                session.setAttribute("error", "Phone number already registered");
                response.sendRedirect(request.getContextPath() + "View/Register.jsp");
                return;
            }
            
            userDetails.setPhone_number(phoneNumber);
        }
        
        //duplicate username check
        if (userDAO.getUserByUsername(newUser.getUsername()) != null) {
            HttpSession session = request.getSession();
            session.setAttribute("error", "Username already exists");
            response.sendRedirect(request.getContextPath() + "View/Register.jsp");
            return;
        }

        //Email check
        if (userDAO.getUserDetailsByEmail(userDetails.getEmail()) != null) {
            HttpSession session = request.getSession();
            session.setAttribute("error", "Email already registered");
            response.sendRedirect(request.getContextPath() + "View/Register.jsp");
            return;
        }

        if (userDAO.createUser(newUser, userDetails)) {
            UserAccount user = userDAO.getUserByUsername(newUser.getUsername());
            HttpSession session = request.getSession();
            session.setAttribute("user_id", user.getUser_id());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role_id", user.getRole_id());
            
            switch (user.getRole_id()) {
                case 1: //Admin Login
                    response.sendRedirect(request.getContextPath() + "View/AdminPage.jsp");
                    break;
                default: //Member Login
                    response.sendRedirect(request.getContextPath() + "View/Home.jsp");
            }
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("error", "Registration failed");
            response.sendRedirect(request.getContextPath() + "View/Register.jsp");
        }
    }
    
//    private void updateUser(HttpServletRequest request, HttpServletResponse response) 
//            throws SQLException, ServletException, IOException {
//            
//        UserAccount user = new UserAccount();
//        user.setUser_id(Integer.parseInt(request.getParameter("user_id")));
//        user.setUsername(request.getParameter("username"));
//        user.setPassword(request.getParameter("password"));
//        user.setRole_id(Integer.parseInt(request.getParameter("role_id")));
//        
//        if (userDAO.updateUser(user)) {
//            response.sendRedirect(request.getContextPath() + "profile.jsp?updated=true");
//        } else {
//            request.setAttribute("error", "Update failed");
//            request.getRequestDispatcher("profile.jsp").forward(request, response);
//        }
//    }
    
//    private void getAllUsers(HttpServletRequest request, HttpServletResponse response) 
//            throws SQLException, ServletException, IOException {
//            
//        request.setAttribute("userList", userDAO.getAllUser());
//        request.getRequestDispatcher("userList.jsp").forward(request, response);
//    }
//    
//    private void viewProfile(HttpServletRequest request, HttpServletResponse response) 
//            throws SQLException, ServletException, IOException {
//            
//        HttpSession session = request.getSession();
//        int userId = (int) session.getAttribute("user_id");
//        
//        // Additional profile logic here if needed
//        request.getRequestDispatcher("profile.jsp").forward(request, response);
//    }
}