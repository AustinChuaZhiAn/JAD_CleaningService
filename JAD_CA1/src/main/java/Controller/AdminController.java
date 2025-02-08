package Controller;

import Model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet("/AdminController")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserAccountSQL userDAO;
	private AddressList addressDAO;
	private final Client client;
	private final String categoryBaseUrl;
	private CleanerRead cleanerDAO;

	public AdminController() {
		super();
		userDAO = new UserAccountSQL();
		addressDAO = new AddressList();
		cleanerDAO = new CleanerList();
		client = ClientBuilder.newClient();
		categoryBaseUrl = "http://localhost:3000/api/categories";
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("role_id") == null || !session.getAttribute("role_id").equals(1)) {
			response.sendRedirect(request.getContextPath() + "/View/Login.jsp");
			return;
		}

		try {
			String action = request.getParameter("action");
			if (action == null) {
				displayDashboard(request, response);
				return;
			}

			switch (action) {
			case "list":
				showUserList(request, response);
				break;
			case "CustomerInquiry":
				getCustomerList(request, response);
				break;
			case "deleteCleaner":
			    deleteCleaner(request, response);
			    break;
			case "deleteUser":
				deleteUser(request, response);
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
				switch (action) {
				case "verify":
					verifyUser(request, response);
					break;
				case "verifyEmployee":
				    verifyEmployee(request, response);
				    break;
				case "create":
					createUser(request, response);
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
			Integer visitedOptions = (Integer) session.getAttribute("frequency_id");
			session.setAttribute("user_id", user.getUser_id());
			session.setAttribute("username", user.getUsername());
			session.setAttribute("role_id", user.getRole_id());

			switch (user.getRole_id()) {
			case 1:
				response.sendRedirect(request.getContextPath() + "/AdminController");
				break;
			case 2:
				if (visitedOptions == null) {
					response.sendRedirect(request.getContextPath() + "/View/Home.jsp");
				} else {
					response.sendRedirect(request.getContextPath() + "/BookingController");
				}
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

private void verifyEmployee(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, ServletException, IOException {
    String name = request.getParameter("name");
    String phone = request.getParameter("phone");
    
    try {
        int contactNumber = Integer.parseInt(phone);
        
        List<Cleaner> cleaners = cleanerDAO.getAllCleaner();
        Cleaner matchedCleaner = null;
        
        for (Cleaner cleaner : cleaners) {
            if (cleaner.getCleaner_contact() == contactNumber && 
                cleaner.getCleaner_name().trim().equalsIgnoreCase(name.trim())) {
                matchedCleaner = cleaner;
                break;
            }
        }
        
        if (matchedCleaner != null) {
            HttpSession session = request.getSession();
            session.setAttribute("cleaner_id", matchedCleaner.getCleaner_id());
            session.setAttribute("cleaner_name", matchedCleaner.getCleaner_name());
            session.setAttribute("isEmployee", true);
            response.sendRedirect(request.getContextPath() + "/BookingController?action=employeeDashboard");
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("error", "Invalid name or phone number");
            response.sendRedirect(request.getContextPath() + "/View/EmployeeLogin.jsp");
        }
        
    } catch (NumberFormatException e) {
        HttpSession session = request.getSession();
        session.setAttribute("error", "Phone number must be numeric");
        response.sendRedirect(request.getContextPath() + "/View/EmployeeLogin.jsp");
    }
}
	
	private void createUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		HttpSession session = request.getSession();

		try {
			int roleId = Integer.parseInt(request.getParameter("role_id"));

			if (roleId == 3) {
				// Handle cleaner creation using DAO
				String cleanerName = request.getParameter("cleaner_name");
				String contact = request.getParameter("contact");

				if (cleanerDAO.createCleaner(cleanerName, contact)) {
					session.setAttribute("success", "Cleaner added successfully");
				} else {
					session.setAttribute("error", "Failed to add cleaner");
					response.sendRedirect(request.getContextPath() + "/View/admin/UserForm.jsp");
					return;
				}
			} else {
				// Handle normal user creation
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				String email = request.getParameter("email");
				String phoneNumber = request.getParameter("phone_number");

				UserAccount newUser = new UserAccount();
				newUser.setUsername(username);
				newUser.setPassword(password);
				newUser.setRole_id(roleId);

				UserDetails userDetails = new UserDetails();
				userDetails.setEmail(email);
				userDetails.setPhone_number(phoneNumber);

				if (userDAO.createUser(newUser, userDetails)) {
					session.setAttribute("success", "User created successfully");
				} else {
					session.setAttribute("error", "Failed to create user. Username may already exist.");
					response.sendRedirect(request.getContextPath() + "/View/admin/UserForm.jsp");
					return;
				}
			}

			response.sendRedirect(request.getContextPath() + "/AdminController?action=list");

		} catch (Exception e) {
			session.setAttribute("error", "Error creating user/cleaner: " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/View/admin/UserForm.jsp");
		}
	}

	private void deleteCleaner(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		HttpSession session = request.getSession();
		try {
			int cleanerId = Integer.parseInt(request.getParameter("id"));

			if (cleanerDAO.deleteCleaner(cleanerId)) {
				session.setAttribute("success", "Cleaner deleted successfully");
			} else {
				session.setAttribute("error", "Failed to delete cleaner");
			}
		} catch (NumberFormatException e) {
			session.setAttribute("error", "Invalid cleaner ID");
		}

		response.sendRedirect(request.getContextPath() + "/AdminController?action=list");
	}

	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		HttpSession session = request.getSession();
		try {
			int userId = Integer.parseInt(request.getParameter("user_id"));
			int roleId = Integer.parseInt(request.getParameter("role_id"));

			if (userId == (Integer) session.getAttribute("user_id")) {
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

	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		HttpSession session = request.getSession();
		try {
			int userId = Integer.parseInt(request.getParameter("id"));
			if (userId == (Integer) session.getAttribute("user_id")) {
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
		List<Cleaner> cleaners = cleanerDAO.getAllCleaner();

		request.setAttribute("users", users);
		request.setAttribute("cleaners", cleaners);
		request.getRequestDispatcher("/View/admin/Users.jsp").forward(request, response);
	}

	private void displayDashboard(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int userCount = userDAO.getTotalUser();
			WebTarget target = client.target(categoryBaseUrl + "/count");
			Integer categoryCount = target.request(MediaType.APPLICATION_JSON).get(Integer.class);

			request.setAttribute("userCount", userCount);
			request.setAttribute("categoryCount", categoryCount != null ? categoryCount : 0);
			request.getRequestDispatcher("/View/admin/AdminPage.jsp").forward(request, response);
		} catch (SQLException e) {
			handleError(request, response, e);
		}
	}

	private void handleError(HttpServletRequest request, HttpServletResponse response, Exception e)
			throws ServletException, IOException {
		e.printStackTrace();
		request.getSession().setAttribute("error", "An error occurred: " + e.getMessage());
		response.sendRedirect(request.getContextPath() + "/AdminController?userConfig");
	}

	private void getCustomerList(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		HttpSession session = request.getSession();
		int role_id = (int) session.getAttribute("role_id");
		if (role_id != 1) {
			response.sendRedirect(request.getContextPath() + "/View/Login.jsp");
			return;
		}

		List<UserAccount> customerUserAccount = userDAO.getAllUserByRoleId(2);
		List<UserDetails> customerDetails = new ArrayList<>();
		List<List<Address>> customerAddresses = new ArrayList<>();
		List<AddressType> listOfAddressTypes = addressDAO.getAllAddressType();

		for (UserAccount user : customerUserAccount) {
			customerDetails.add(userDAO.getUserDetailsByUserId(user.getUser_id()));
			customerAddresses.add(addressDAO.getAddressesByUserId(user.getUser_id()));
		}

		request.setAttribute("listOfUsers", customerUserAccount);
		request.setAttribute("listOfUserDetails", customerDetails);
		request.setAttribute("listOfAddresses", customerAddresses);
		request.setAttribute("listOfAddressTypes", listOfAddressTypes);
		request.getRequestDispatcher("/View/admin/CustomerInquiry.jsp").forward(request, response);
	}
}