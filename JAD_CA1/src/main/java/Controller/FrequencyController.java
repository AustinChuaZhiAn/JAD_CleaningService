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
import Model.Frequency;
import Model.FrequencyRead;
import Model.FrequencyList;

/**
 * Servlet implementation class FrequencyController
 */
@WebServlet("/FrequencyController")
public class FrequencyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FrequencyRead frequencyList;

	public void init() {
		frequencyList = new FrequencyList();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FrequencyController() {
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
			listFrequency(request, response);
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
		doGet(request, response);
	}

	private void listFrequency(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		HttpSession session = request.getSession();
		
		if(session.getAttribute("service_type_id") == null) {
			String service_type_idStr = request.getParameter("serviceType");
			int service_type_id = Integer.parseInt(service_type_idStr);
			session.setAttribute("service_type_id", service_type_id);
		}
		

		List<Frequency> frequency = frequencyList.getAllFrequency();
		request.setAttribute("frequencyList", frequency);
		request.getRequestDispatcher("/View/FrequencyOption.jsp").forward(request, response);
		
	}

}
