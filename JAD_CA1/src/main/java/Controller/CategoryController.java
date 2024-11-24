package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import Model.*;

@WebServlet("/CategoryController")
public class CategoryController extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CategoryDAO categoryDAO;

    public void init() {
        categoryDAO = new CategoryDAOImpl();
        }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String categoryIdParam = request.getParameter("categoryId");
        if (categoryIdParam != null) {
            try {
                int categoryId = Integer.parseInt(categoryIdParam);
                Category category = categoryDAO.getCategoryById(categoryId);
                if (category != null) {
                    request.setAttribute("category", category);
                    request.getRequestDispatcher("/View/category.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Category not found");
                }
            } catch (SQLException e) {
                throw new ServletException("Database error", e);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid category ID");
            }
        } else {
            try {
                List<Category> categories = categoryDAO.getAllCategories();
                request.setAttribute("Categories", categories);
                
                request.getRequestDispatcher("/View/ServiceCategories.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new ServletException("Database error", e);
            }
        }
    }
}