package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import Model.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Servlet implementation class categoryController
 */
@WebServlet("/CategoryController")
public class CategoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoryController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private ServiceCRUD categoryCRUD;

    public void init() {
    	categoryCRUD = new ServiceList();
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
    private void listCategories(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Service> categories = categoryCRUD.getAllServices();
        List<Service> categoryOnlyList = new ArrayList<>();
        for(Service category: categories) {
        	if(category.getServiceType() == 1) {
        	categoryOnlyList.add(category);
        	}
        }
        request.setAttribute("categories", categories);
        request.getRequestDispatcher(request.getContextPath() + "/ServiceCategories.jsp").forward(request, response);
    }

}
