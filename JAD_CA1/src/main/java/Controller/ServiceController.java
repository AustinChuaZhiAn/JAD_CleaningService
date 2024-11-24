package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.*;

@WebServlet("/ServiceController")
public class ServiceController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ServiceDAO serviceDAO;
    private CategoryDAO categoryDAO;
    private ServiceTypeRead serviceTypeRead;
    private FrequencyRead frequencyRead;
    
    public void init() {
        serviceDAO = new ServiceDAOImpl();
        categoryDAO = new CategoryDAOImpl();
        serviceTypeRead = new ServiceTypeList();
        frequencyRead = new FrequencyList();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            if (action == null) {
                listServices(request, response);
            } else {
                switch (action) {
                    case "create":
                        showCreateForm(request, response);
                        break;
                    case "edit":
                        showEditForm(request, response);
                        break;
                    case "delete":
                        deleteService(request, response);
                        break;
                    default:
                        listServices(request, response);
                        break;
                }
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            switch (action) {
                case "create":
                    createService(request, response);
                    break;
                case "edit":
                    updateService(request, response);
                    break;
                default:
                    listServices(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
    
    private void listServices(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        ArrayList<Service> services = serviceDAO.getAllServices();
        request.setAttribute("services", services);
        request.getRequestDispatcher("/admin/Services.jsp").forward(request, response);
    }
    
    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        ArrayList<Category> categories = categoryDAO.getAllCategory();
        List<ServiceType> serviceTypes = serviceTypeRead.getAllServiceType();
        List<Frequency> frequencies = frequencyRead.getAllFrequency();
        
        request.setAttribute("categories", categories);
        request.setAttribute("serviceTypes", serviceTypes);
        request.setAttribute("frequencies", frequencies);
        request.getRequestDispatcher("/admin/ServiceForm.jsp").forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Service service = serviceDAO.getServiceById(id);
        ArrayList<Category> categories = categoryDAO.getAllCategory();
        List<ServiceType> serviceTypes = serviceTypeRead.getAllServiceType();
        List<Frequency> frequencies = frequencyRead.getAllFrequency();
        
        request.setAttribute("service", service);
        request.setAttribute("categories", categories);
        request.setAttribute("serviceTypes", serviceTypes);
        request.setAttribute("frequencies", frequencies);
        request.getRequestDispatcher("/admin/ServiceForm.jsp").forward(request, response);
    }
    
    private void createService(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("category_id"));
        int serviceTypeId = Integer.parseInt(request.getParameter("service_type_id"));
        int frequencyId = Integer.parseInt(request.getParameter("frequency_id"));
        String price = request.getParameter("price");
        
        Service service = new Service(categoryId, serviceTypeId, frequencyId, price);
        
        if (serviceDAO.createService(service)) {
            response.sendRedirect("ServiceController?success=Service created successfully");
        } else {
            response.sendRedirect("ServiceController?error=Failed to create service");
        }
    }
    
    private void updateService(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("service_id"));
        int categoryId = Integer.parseInt(request.getParameter("category_id"));
        int serviceTypeId = Integer.parseInt(request.getParameter("service_type_id"));
        int frequencyId = Integer.parseInt(request.getParameter("frequency_id"));
        String price = request.getParameter("price");
        
        Service service = new Service(id, categoryId, serviceTypeId, frequencyId, price, null, null, null, "active");
        
        if (serviceDAO.updateService(service)) {
            response.sendRedirect("ServiceController?success=Service updated successfully");
        } else {
            response.sendRedirect("ServiceController?error=Failed to update service");
        }
    }
    
    private void deleteService(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        
        if (serviceDAO.deleteService(id)) {
            response.sendRedirect("ServiceController?success=Service deleted successfully");
        } else {
            response.sendRedirect("ServiceController?error=Failed to delete service");
        }
    }
}