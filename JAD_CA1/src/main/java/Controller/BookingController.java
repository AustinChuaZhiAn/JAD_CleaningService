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

import Model.*;

/**
 * Servlet implementation class bookingController
 */
@WebServlet("/BookingController")
public class BookingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AddressCRUD addressList;
	private BookingCRUD bookingList;
	public void init() {
		addressList = new AddressList();
		bookingList = new BookingList();
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookingController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		try {
			listBooking(request, response);
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void listBooking(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		String selectedFrequency = request.getParameter("frequency");
        HttpSession session = request.getSession();
        session.setAttribute("selectedFrequency", selectedFrequency);
        
		List<Address> address = addressList.getAllAddress();
		request.setAttribute("addressList", address);
		request.getRequestDispatcher("/Booking.jsp").forward(request, response);
	}

}
