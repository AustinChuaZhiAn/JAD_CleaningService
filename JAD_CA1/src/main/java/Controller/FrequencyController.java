package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import Model.Frequency;
import Model.FrequencyCRUD;
import Model.FrequencyList;

/**
 * Servlet implementation class FrequencyController
 */
@WebServlet("/FrequencyController")
public class FrequencyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FrequencyCRUD frequencyList;

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
		String action = request.getParameter("action");
		try {
			if (action == null) {
				listFrequency(request, response);
			} else {
				switch (action) {
				case "new":
//					showNewForm(request, response);
					break;
				case "edit":
//					showEditForm(request, response);
					break;
				case "delete":
//					deleteCategory(request, response);
					break;
				default:
					listFrequency(request, response);
				}
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
		doGet(request, response);
	}

	private void listFrequency(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		List<Frequency> frequency = frequencyList.getAllFrequency();
		request.setAttribute("frequencyList", frequency);
		request.getRequestDispatcher("/FrequencyOption.jsp").forward(request, response);
	}

}
