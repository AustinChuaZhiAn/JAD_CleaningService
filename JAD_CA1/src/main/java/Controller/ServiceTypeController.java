
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
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

import Model.*;

/**
 * Servlet implementation class ServiceController
 */
@WebServlet("/ServiceTypeController")
public class ServiceTypeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServiceTypeRead serviceTypeList;
	private ServiceDAO categoryList;

	public void init() {
		serviceTypeList = new ServiceTypeList();
		categoryList = new ServiceDAOImpl();
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServiceTypeController() {
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
			listServiceType(request, response);
		} catch(SQLException ex) {
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
	
	private void listServiceType(HttpServletRequest request, HttpServletResponse response)
	        throws SQLException, ServletException, IOException {

	    String categoryName = request.getParameter("categoryName");
	    String category_idStr = request.getParameter("category_id");
	    int category_id = Integer.parseInt(category_idStr);

	    HttpSession session = request.getSession();
	    session.setAttribute("category_id", category_id);

	    List<Service> categories = categoryList.getAllServices();
	    List<ServiceType> serviceType = serviceTypeList.getAllServiceType();
	    ArrayList<ServiceType> requiredServiceType = new ArrayList<>();

	    // Set to keep track of unique service types for the category
	    Set<Integer> uniqueServiceTypeIds = new HashSet<>();
	    int targetServiceTypeId = -1;

	    for (Service category : categories) {
	        if (category.getCategory_name().equals(categoryName)) {
	            for (ServiceType serviceTypeForPage : serviceType) {
	                // If the service type matches the current category
	                if (serviceTypeForPage.getService_type_id() == category.getService_type_id()) {
	                    uniqueServiceTypeIds.add(serviceTypeForPage.getService_type_id());
	                    if (!requiredServiceType.contains(serviceTypeForPage)) {
	                        requiredServiceType.add(serviceTypeForPage);
	                    }
	                }
	            }
	        }
	    }

	    if (uniqueServiceTypeIds.size() == 1) {
	        targetServiceTypeId = uniqueServiceTypeIds.iterator().next();
	        session.setAttribute("service_type_id", targetServiceTypeId);

	        response.sendRedirect(request.getContextPath() + "/FrequencyController");
	        return;
	    }

	    request.setAttribute("serviceTypeList", requiredServiceType);
	    request.getRequestDispatcher("/View/ServiceType.jsp").forward(request, response);
	}


}

