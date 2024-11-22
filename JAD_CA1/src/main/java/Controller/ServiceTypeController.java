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
import java.util.ArrayList;

import Model.*;

/**
 * Servlet implementation class ServiceController
 */
@WebServlet("/ServiceTypeController")
public class ServiceTypeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServiceTypeRead serviceTypeList;

	public void init() {
		serviceTypeList = new ServiceTypeList();
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
    	
        List<Service> categories = categoryCRUD.getAllCategories();
        List<ServiceType> serviceType = serviceTypeList.getAllServiceType();
        ArrayList<ServiceType>  requiredServiceType = new ArrayList<>();
        
        for(Service category: categories) {
        	if(category.getCategoryName() == categoryName) {
        		for (ServiceType serviceTypeForPage : serviceType) {
        			if(serviceTypeForPage.getService_type_id() == category.getServicetype()) {
        				requiredServiceType.add(serviceTypeForPage);
        			}
        		}
        	}
        }
        request.setAttribute("serviceTypeList", requiredServiceType);
        request.getRequestDispatcher(request.getContextPath() + "/ServiceType.jsp").forward(request, response);
    }

}