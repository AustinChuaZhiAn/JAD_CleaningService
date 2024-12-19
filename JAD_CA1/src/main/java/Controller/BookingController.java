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

	public void init() {
		addressList = new AddressList();
		bookingList = new BookingList();
		cleanerList = new CleanerList();
		serviceList = new ServiceDAOImpl();
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
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		try {
			listBooking(request, response);
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
		try {
			postAddBooking(request, response);
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void postAddBooking(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {

		HttpSession session = request.getSession();
		int user_id = (int) session.getAttribute("user_id");

		String cleaner_idStr = request.getParameter("cleaner");
		String address_idStr = request.getParameter("address");

		String selectedDateTime = request.getParameter("selectedDateTime");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm");

		LocalDateTime dateTime = LocalDateTime.parse(selectedDateTime, formatter);

		LocalDate date = dateTime.toLocalDate(); // Extracts only the date for date column
		LocalTime time = dateTime.toLocalTime(); // Extracts only the time for time column
		
		String special_request = request.getParameter("specialRequests");

		int cleaner_id = Integer.parseInt(cleaner_idStr);
		int address_id = Integer.parseInt(address_idStr);
		int category_id = (int) session.getAttribute("category_id");
		int service_type_id = (int) session.getAttribute("service_type_id");
		int frequency_id = (int) session.getAttribute("frequency_id");

		int service_id = serviceList.getServiceIdByDetails(category_id, service_type_id, frequency_id);
		
		int booking_id = bookingList.addBooking(user_id, address_id, time, date, cleaner_id, service_id, special_request);
		
	    session.removeAttribute("category_id");
	    session.removeAttribute("service_type_id");
	    session.removeAttribute("frequency_id");
	    
		session.setAttribute("booking_id", booking_id);
		response.sendRedirect(request.getContextPath() + "/userController");
	}

	private void listBooking(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		HttpSession session = request.getSession();
		Integer prevFrequency_id = (Integer) session.getAttribute("frequency_id");
		System.out.println(prevFrequency_id);
		if(prevFrequency_id == null) {
			String frequency_idStr = request.getParameter("frequency_id");
			int frequency_id = Integer.parseInt(frequency_idStr);
			System.out.println(frequency_id);
			session.setAttribute("frequency_id", frequency_id);
		};
		
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
}
