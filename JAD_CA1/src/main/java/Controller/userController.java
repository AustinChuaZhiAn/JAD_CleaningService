package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.*;

@WebServlet("/userController")
public class userController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserAccountCRUD userDAO;
	private AddressCRUD addressList;
	private CleanerRead cleanerList;
	private BookingCRUD bookingList;

	public userController() {
		super();
		userDAO = new UserAccountSQL();
		addressList = new AddressList();
		cleanerList = new CleanerList();
		bookingList = new BookingList();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());

		String action = request.getParameter("action");

		try {
			if (action != null) {
				switch (action) {
				case "AddAddress":
					getAddressType(request, response);
					break;
				case "UpdateProfile":
					getUserAndProfile(request, response);
					break;
				default:
					response.sendRedirect(request.getContextPath() + "/View/Home.jsp");
					break;
				}
			} else {
				getProfileByUserId(request, response);
			}

		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		try {
			if (action != null) {
				switch (action) {

				case "create":
					createUser(request, response);
					break;
				case "UpdateProfile":
					UpdateProfileByUserDetailsId(request, response);
					getProfileByUserId(request, response);
					break;
				case "AddAddress":
					AddAddressByUserDetailsId(request, response);
					break;
				default:
					break;
				}
			} else {
				response.sendRedirect(request.getContextPath() + "/View/Home.jsp");
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}


	private void createUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		String password = request.getParameter("password");
	    
	    // Check password requirements
	    if (password == null || password.length() < 8) {
	        HttpSession session = request.getSession();
	        session.setAttribute("error", "Password must be at least 8 characters long");
	        response.sendRedirect(request.getContextPath() + "/View/Register.jsp");
	        return;
	    }
	    
	    // Check for lowercase letter
	    if (!password.matches(".*[a-z].*")) {
	        HttpSession session = request.getSession();
	        session.setAttribute("error", "Password must contain at least one lowercase letter");
	        response.sendRedirect(request.getContextPath() + "/View/Register.jsp");
	        return;
	    }
	    
	    // Check for uppercase letter
	    if (!password.matches(".*[A-Z].*")) {
	        HttpSession session = request.getSession();
	        session.setAttribute("error", "Password must contain at least one uppercase letter");
	        response.sendRedirect(request.getContextPath() + "/View/Register.jsp");
	        return;
	    }
	    
	    // Check for number
	    if (!password.matches(".*\\d.*")) {
	        HttpSession session = request.getSession();
	        session.setAttribute("error", "Password must contain at least one number");
	        response.sendRedirect(request.getContextPath() + "/View/Register.jsp");
	        return;
	    }
	    
	    // Check for special character
	    if (!password.matches(".*[@$!%*?&].*")) {
	        HttpSession session = request.getSession();
	        session.setAttribute("error", "Password must contain at least one special character (@$!%*?&)");
	        response.sendRedirect(request.getContextPath() + "/View/Register.jsp");
	        return;
	    }


		UserAccount newUser = new UserAccount();
		newUser.setUsername(request.getParameter("username"));
		newUser.setPassword(password);
		newUser.setRole_id(Integer.parseInt(request.getParameter("role_id")));

		UserDetails userDetails = new UserDetails();
		userDetails.setEmail(request.getParameter("email"));

		String phoneNumber = request.getParameter("phone_number");
		if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
			if (!phoneNumber.matches("\\d+")) {
				HttpSession session = request.getSession();
				session.setAttribute("error", "Phone number must contain only numbers");
				response.sendRedirect(request.getContextPath() + "/View/Register.jsp");
				return;
			}
			if (userDAO.getUserDetailsByPhone(phoneNumber) != null) {
				HttpSession session = request.getSession();
				session.setAttribute("error", "Phone number already registered");
				response.sendRedirect(request.getContextPath() + "/View/Register.jsp");
				return;
			}

			userDetails.setPhone_number(phoneNumber);
		}

		if (userDAO.getUserByUsername(newUser.getUsername()) != null) {
			HttpSession session = request.getSession();
			session.setAttribute("error", "Username already exists");
			response.sendRedirect(request.getContextPath() + "/View/Register.jsp");
			return;
		}

		if (userDAO.getUserDetailsByEmail(userDetails.getEmail()) != null) {
			HttpSession session = request.getSession();
			session.setAttribute("error", "Email already registered");
			response.sendRedirect(request.getContextPath() + "/View/Register.jsp");
			return;
		}

		if (userDAO.createUser(newUser, userDetails)) {
			UserAccount user = userDAO.getUserByUsername(newUser.getUsername());
			HttpSession session = request.getSession();
			session.setAttribute("user_id", user.getUser_id());
			session.setAttribute("username", user.getUsername());
			session.setAttribute("role_id", user.getRole_id());

			switch (user.getRole_id()) {
			case 1: // Admin Login
				response.sendRedirect(request.getContextPath() + "/View/admin/AdminPage.jsp");
				break;
			default: // Member Login
				response.sendRedirect(request.getContextPath() + "/View/Home.jsp");
			}
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("error", "Registration failed");
			response.sendRedirect(request.getContextPath() + "/View/Register.jsp");
		}
	}

	private void getProfileByUserId(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {

		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		if (username == null || username.isEmpty()) {
			String contextPath = request.getContextPath();
			String loginPage = contextPath + "/View/Login.jsp";
			response.sendRedirect(loginPage);
			return;
		}

		int user_id = (int) session.getAttribute("user_id");
		UserDetails profileDetails = userDAO.getUserDetailsByUserId(user_id);
		UserAccount userDetails = userDAO.getUserByUsername(username);
		List<Address> listAddress = addressList.getAddressesByUserId(user_id);
		List<Booking> listBooking = bookingList.getBookingByUserId(user_id);
		List<Cleaner> listCleaner = new ArrayList<>();
		for (Booking booking : listBooking) {
			listCleaner.add(cleanerList.getCleanerByBookingId(booking.getBooking_id()));
		}

		request.setAttribute("bookingList", listBooking);
		request.setAttribute("cleanerList", listCleaner);
		request.setAttribute("addressList", listAddress);
		request.setAttribute("user", userDetails);
		request.setAttribute("userdetails", profileDetails);
		request.getRequestDispatcher("/View/Profile.jsp").forward(request, response);
	}

	private void getAddressType(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException, ServletException {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		if (username == null) {
			String contextPath = request.getContextPath();
			String loginPage = contextPath + "/View/Login.jsp";
			response.sendRedirect(loginPage);
			return;
		}

		String errorMessage = (String) request.getAttribute("errorMessage");
		if (errorMessage != null) {
			request.setAttribute("errorMessage", errorMessage);
		}

		List<AddressType> listAddressType = addressList.getAllAddressType();
		request.setAttribute("addressType", listAddressType);

		request.getRequestDispatcher("/View/CreateAddress.jsp").forward(request, response);
	}

	private void getUserAndProfile(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException, ServletException {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		if (username == null || username.isEmpty()) {
			String contextPath = request.getContextPath();
			String loginPage = contextPath + "/View/Login.jsp";
			response.sendRedirect(loginPage);
			return;
		}

		int user_id = (int) session.getAttribute("user_id");
		UserDetails profileDetails = userDAO.getUserDetailsByUserId(user_id);
		
		String email = profileDetails.getEmail();
		String phone_number = profileDetails.getPhone_number(); 
		request.setAttribute("username", username);
		request.setAttribute("email", email);
		request.setAttribute("phone", phone_number);
		request.getRequestDispatcher("/View/UpdateProfile.jsp").forward(request, response);
	}

	private void UpdateProfileByUserDetailsId(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException, ServletException {
		HttpSession session = request.getSession();
		Integer user_id = (Integer) session.getAttribute("user_id");

		if (user_id == null || user_id == 0) {
			response.sendRedirect(request.getContextPath() + "/View/Login.jsp");
			return;
		}

		String username = request.getParameter("name");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");

		if (username == null || email == null || phone == null || username.isEmpty() || email.isEmpty()
				|| phone.isEmpty()) {
			request.setAttribute("errorMessage", "All fields are required.");
			request.getRequestDispatcher("/userController").forward(request, response);
			return;
		}

		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		if (!email.matches(emailRegex)) {
			request.setAttribute("errorMessage", "Invalid email format.");
			request.setAttribute("username", username);
			request.setAttribute("email", email);
			request.setAttribute("phone", phone);
			request.getRequestDispatcher("/View/UpdateProfile.jsp").forward(request, response);
			return;
		}

		String phoneRegex = "^\\+?\\d{8,15}$";
		if (!phone.matches(phoneRegex)) {
			request.setAttribute("errorMessage", "Phone number must be between 8 and 15 digits.");
			request.setAttribute("username", username);
			request.setAttribute("email", email);
			request.setAttribute("phone", phone);
			request.getRequestDispatcher("/View/UpdateProfile.jsp").forward(request, response);
			return;
		}

		userDAO.updateUsernameByUserId(username, user_id);
		userDAO.updateUserDetailsByUserId(email, phone, user_id);
	}

	private void AddAddressByUserDetailsId(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {

		String address_type_idStr = request.getParameter("address_type_id");
		String postal_codeStr = request.getParameter("postal_code");
		String block_number = request.getParameter("block_number");
		String street_name = request.getParameter("street_name");
		String unit_number = request.getParameter("unit_number");
		String building_name = request.getParameter("building_name");

		if (address_type_idStr == null || postal_codeStr == null || block_number == null || street_name == null
				|| unit_number == null || building_name == null || address_type_idStr.isEmpty()
				|| postal_codeStr.isEmpty() || block_number.isEmpty() || street_name.isEmpty() || unit_number.isEmpty()
				|| building_name.isEmpty()) {

			request.setAttribute("errorMessage", "All fields are required.");
			getAddressType(request, response);
			return;
		}

		int address_type_id = -1;
		int postal_code = -1;

		try {
			address_type_id = Integer.parseInt(address_type_idStr);

			if (postal_codeStr.length() != 6 || !postal_codeStr.matches("\\d{6}")) {
				request.setAttribute("errorMessage", "Postal Code must be exactly 6 digits.");
				getAddressType(request, response);
				return;
			}

			postal_code = Integer.parseInt(postal_codeStr);
		} catch (NumberFormatException e) {
			request.setAttribute("errorMessage", "Address Type ID and Postal Code must be numeric.");
			getAddressType(request, response);
			return;
		}

		HttpSession session = request.getSession();
		Integer user_id = (Integer) session.getAttribute("user_id");

		if (user_id == null) {
			response.sendRedirect(request.getContextPath() + "/View/Login.jsp");
			return;
		}

		UserDetails userDetails = userDAO.getUserDetailsByUserId(user_id);
		int user_details_id = userDetails.getUser_details_id();

		int addAddress = addressList.addAddress(user_details_id, postal_code, block_number, street_name, unit_number,
				building_name, address_type_id);

		if (addAddress == -1) {
			request.setAttribute("errorMessage", "Address addition failed.");
		}
		getProfileByUserId(request, response);
		return;
	}
}