package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import Model.*;

/**
 * Servlet implementation class bookingController
 */
@WebServlet("/BookingController")
public class BookingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AddressCRUD addressList;
	private BookingCRUD bookingList;
	private CleanerRead cleanerList;
	private ServiceDAO serviceList;
	private BookingToServiceCRUD btsList;

	public void init() {
		addressList = new AddressList();
		bookingList = new BookingList();
		cleanerList = new CleanerList();
		serviceList = new ServiceDAOImpl();
		btsList = new BookingToServiceList();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookingController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		try {
			if (action != null) {
				switch (action) {
				case "bookingById":
					getBookingDetailsById(request, response);
					break;
				case "employeeDashboard":
					getEmployeeDashboard(request, response);
					break;
				default:
					response.sendRedirect(request.getContextPath() + "/View/Home.jsp");
					break;
				}
			} else {
				listBooking(request, response);
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");

		try {
			if (action != null) {
				switch (action) {

				case "AddToCart":
					postAddToCart(request, response);
					break;
				case "AddBookings":
					postAddBooking(request, response);
					break;
				case "EditFeedback":
					postEditFeedback(request, response);
					break;
				case "updateStatus":
					updateBookingStatus(request, response);
					break;
				default:
					break;
				}
			} else {
				response.sendRedirect(request.getContextPath() + "/View/Home.jsp");
			}
//		try {
//			postAddBooking(request, response);
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void postAddBooking(HttpServletRequest request, HttpServletResponse response)
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

		List<List<String>> listOfServices = (List<List<String>>) session.getAttribute("listOfServices");

		int booking_id = bookingList.addBooking(user_id);

		for (List<String> serviceDetails : listOfServices) {
			btsList.addBookingServiceEntry(booking_id, serviceDetails);
		}

		// int bookingToService_id = btsList.addBookingServiceEntry(booking_id,
		// List<String> servicesInCart);

		session.removeAttribute("listOfServices");

		response.sendRedirect(request.getContextPath() + "/userController");
	}

	private void postAddToCart(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {

		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		if (username == null || username.isEmpty()) {
			String contextPath = request.getContextPath();
			String loginPage = contextPath + "/View/Login.jsp";
			response.sendRedirect(loginPage);
			return;
		}

		String cleaner_idStr = request.getParameter("cleaner");
		String address_idStr = request.getParameter("address");

		String selectedDateTime = request.getParameter("selectedDateTime");

		String special_request = request.getParameter("specialRequests");

//		int cleaner_id = Integer.parseInt(cleaner_idStr);
//		int address_id = Integer.parseInt(address_idStr);
		int category_id = (int) session.getAttribute("category_id");
		int service_type_id = (int) session.getAttribute("service_type_id");
		int frequency_id = (int) session.getAttribute("frequency_id");

		String service_id = String
				.valueOf(serviceList.getServiceIdByDetails(category_id, service_type_id, frequency_id));

		List<String> newService = new ArrayList<>();
		newService.add(special_request);
		newService.add(service_id);
		newService.add(selectedDateTime.toString());
		newService.add(cleaner_idStr);
		newService.add(address_idStr);

		List<List<String>> listOfServices = (List<List<String>>) session.getAttribute("listOfServices");
		if (listOfServices == null) {
			listOfServices = new ArrayList<>();
			listOfServices.add(newService);
			session.setAttribute("listOfServices", listOfServices);
		} else {
			listOfServices.add(newService);
			session.setAttribute("listOfServices", listOfServices);
		}
		;

//		int booking_id = bookingList.addBooking(user_id, address_id, time, date, cleaner_id, service_id, special_request);

		// get all service type name, frequency option name, category name, and cleaner
		// name
		List<Service> listOfServiceDisplay = new ArrayList<>();
		List<Cleaner> listOfCleaners = new ArrayList<>();
		for (List<String> serviceDetails : listOfServices) {
			listOfServiceDisplay.add(serviceList.getServiceById(Integer.parseInt(serviceDetails.get(1))));
			listOfCleaners.add(cleanerList.getCleanerByCleanerId(Integer.parseInt(serviceDetails.get(3))));
		}

		session.setAttribute("listOfServiceDisplay", listOfServiceDisplay);
		session.setAttribute("listOfCleaners", listOfCleaners);

		session.removeAttribute("category_id");
		session.removeAttribute("service_type_id");
		session.removeAttribute("frequency_id");

		// Change Add to a list type for add to cart, keep in the cookie
		response.sendRedirect(request.getContextPath() + "/View/AddToCart.jsp");
	}

	private void postEditFeedback(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {

		System.out.println(request.getParameter("bookingId"));
		int bookingId = Integer.parseInt(request.getParameter("bookingId"));
		String feedback = request.getParameter("feedback");

		// Logging
		System.out.println("Received Feedback Submission:");
		System.out.println("Booking ID: " + bookingId);
		System.out.println("Feedback: " + feedback);

		bookingList.updateFeedback(bookingId, feedback); // Update feedback in DB

		response.sendRedirect(request.getContextPath() + "/userController");
	}

	private void listBooking(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		HttpSession session = request.getSession();
		Integer prevFrequency_id = (Integer) session.getAttribute("frequency_id");
		System.out.println(prevFrequency_id);
		if (prevFrequency_id == null) {
			String frequency_idStr = request.getParameter("frequency_id");
			int frequency_id = Integer.parseInt(frequency_idStr);
			System.out.println(frequency_id);
			session.setAttribute("frequency_id", frequency_id);
		}
		;

		Integer user_id = (Integer) session.getAttribute("user_id");
		if (user_id == null || user_id == 0) {
			System.out.println("Redirecting to login page");
			String contextPath = request.getContextPath();
			String loginPage = contextPath + "/View/Login.jsp";
			response.sendRedirect(loginPage);
			return;
		} else {
			System.out.println("user_id is present");
		}

		List<Address> listAddress = addressList.getAddressesByUserId(user_id);
		List<Cleaner> listCleaner = cleanerList.getAllCleaner();
		request.setAttribute("cleanerList", listCleaner);
		request.setAttribute("addressList", listAddress);
		request.getRequestDispatcher("/View/Booking.jsp").forward(request, response);
	}

	private void getEmployeeDashboard(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		HttpSession session = request.getSession();

		// Check if cleaner is logged in
		Integer cleanerId = (Integer) session.getAttribute("cleaner_id");
		if (cleanerId == null || cleanerId == 0) {
			response.sendRedirect(request.getContextPath() + "/View/EmployeeLogin.jsp");
			return;
		}

		// Fetch bookings with service details for this cleaner
		ServiceDAO serviceDAO = new ServiceDAOImpl();
		List<BookingToService> bookings = btsList.getBookingsByCleanerId(cleanerId);
		List<Service> services = new ArrayList<>();

		// Get service details for each booking
		for (BookingToService booking : bookings) {
			services.add(serviceDAO.getServiceById(booking.getService_id()));
		}

		// Set attributes for the JSP
		request.setAttribute("bookings", bookings);
		request.setAttribute("services", services);

		// Forward to employee dashboard
		request.getRequestDispatcher("/View/employee/EmployeeDashboard.jsp").forward(request, response);
	}

	private void updateBookingStatus(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		HttpSession session = request.getSession();

		// Check if cleaner is logged in
		Integer cleanerId = (Integer) session.getAttribute("cleaner_id");
		if (cleanerId == null || cleanerId == 0) {
			response.sendRedirect(request.getContextPath() + "/View/EmployeeLogin.jsp");
			return;
		}

		int bookingServiceId = Integer.parseInt(request.getParameter("bookingId"));
		int statusId = Integer.parseInt(request.getParameter("statusId"));

		boolean updated = btsList.updateBookingStatus(bookingServiceId, statusId);

		if (!updated) {
			session.setAttribute("error", "Failed to update booking status");
		}

		response.sendRedirect(request.getContextPath() + "/BookingController?action=employeeDashboard");
	}

	private void getBookingDetailsById(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		HttpSession session = request.getSession();
		Integer user_id = (Integer) session.getAttribute("user_id");
		if (user_id == null || user_id == 0) {
			System.out.println("Redirecting to login page");
			String contextPath = request.getContextPath();
			String loginPage = contextPath + "/View/Login.jsp";
			response.sendRedirect(loginPage);
			return;
		}

		int booking_id = Integer.parseInt(request.getParameter("id"));

		boolean matchedUserId = bookingList.checkBookingIdToUserId(booking_id, user_id);

		if (!matchedUserId) {
			String contextPath = request.getContextPath();
			String loginPage = contextPath + "/View/Login.jsp";
			response.sendRedirect(loginPage);
			return;
		}

		List<BookingToService> listBookingToService = btsList.getbtsByBookingId(booking_id);

		List<Service> listOfServices = new ArrayList<>();
		List<Cleaner> listOfCleaners = new ArrayList<>();
		List<Address> listOfAddresses = new ArrayList<>();
		String feedback = bookingList.getFeedbackByBookingId(booking_id);

		for (BookingToService serviceDetails : listBookingToService) {
			listOfServices.add(serviceList.getServiceById(serviceDetails.getService_id()));
			listOfCleaners.add(cleanerList.getCleanerByCleanerId(serviceDetails.getCleaner_id()));
			listOfAddresses.add(addressList.getAddressByAddressId(serviceDetails.getAddress_id()));
		}
		request.setAttribute("feedback", feedback);
		request.setAttribute("btsList", listBookingToService);
		request.setAttribute("serviceList", listOfServices);
		request.setAttribute("cleanerList", listOfCleaners);
		request.setAttribute("addressList", listOfAddresses);
		request.getRequestDispatcher("/View/BookingDetails.jsp").forward(request, response);
	}
}
