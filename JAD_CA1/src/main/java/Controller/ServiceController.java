package Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.net.URLEncoder;

import Model.*;

@WebServlet("/ServiceController")
public class ServiceController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ServiceDAO serviceDAO;
    private CategoryDAO categoryDAO;
    private ServiceTypeRead serviceTypeRead;
    private FrequencyRead frequencyRead;
    
    public ServiceController() {
        super();
        initializeDAOs();
    }
    
    public void init() {
        initializeDAOs();
    }
    
    private void initializeDAOs() {
        serviceDAO = new ServiceDAOImpl();
        categoryDAO = new CategoryDAOImpl();
        serviceTypeRead = new ServiceTypeList();
        frequencyRead = new FrequencyList();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        
        try {
            switch (action) {
                case "list":
                    listServices(request, response);
                    break;
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
        } catch (Exception e) {
            handleError(request, response, e);
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
        } catch (Exception e) {
            handleError(request, response, e);
        }
    }
    
    private void listServices(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        ArrayList<Service> services = serviceDAO.getAllServices();
        request.setAttribute("services", services);
        request.getRequestDispatcher("/View/admin/Services.jsp").forward(request, response);
    }
    

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ArrayList<Category> categories = categoryDAO.getAllCategory();
            List<ServiceType> serviceTypes = serviceTypeRead.getAllServiceType();
            List<Frequency> frequencies = frequencyRead.getAllFrequency();
            
            Service emptyService = new Service();  // Create empty service for the form
            
            request.setAttribute("service", emptyService);
            request.setAttribute("categories", categories);
            request.setAttribute("serviceTypes", serviceTypes);
            request.setAttribute("frequencies", frequencies);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/View/admin/ServiceForm.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Failed to load form data: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/View/admin/ServiceForm.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Service service = serviceDAO.getServiceById(id);
            
            if (service == null) {
                request.setAttribute("error", "Service not found");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/ServiceController");
                dispatcher.forward(request, response);
                return;
            }

            ArrayList<Category> categories = categoryDAO.getAllCategory();
            List<ServiceType> serviceTypes = serviceTypeRead.getAllServiceType();
            List<Frequency> frequencies = frequencyRead.getAllFrequency();
            
            request.setAttribute("service", service);
            request.setAttribute("categories", categories);
            request.setAttribute("serviceTypes", serviceTypes);
            request.setAttribute("frequencies", frequencies);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/View/admin/ServiceForm.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Failed to load form data: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/View/admin/ServiceForm.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, Exception e) 
            throws ServletException, IOException {
        e.printStackTrace();
        request.setAttribute("error", e.getMessage());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/View/admin/ServiceForm.jsp");
        dispatcher.forward(request, response);
    }

    // Update error handling in methods to match this pattern:
    private void updateService(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (!validateInputs(request)) {
                response.sendRedirect(request.getContextPath() + 
                    "/View/admin/ServiceForm.jsp?error=" + URLEncoder.encode("All fields are required", "UTF-8"));
                return;
            }
            
            int id = Integer.parseInt(request.getParameter("service_id"));
            int categoryId = Integer.parseInt(request.getParameter("category_id"));
            int serviceTypeId = Integer.parseInt(request.getParameter("service_type_id"));
            int frequencyId = Integer.parseInt(request.getParameter("frequency_id"));
            String price = request.getParameter("price");
            
            Service service = new Service(id, categoryId, serviceTypeId, frequencyId, price, null, null, null, "active");
            
            if (serviceDAO.updateService(service)) {
                response.sendRedirect(request.getContextPath() + 
                    "/ServiceController?action=list&success=" + URLEncoder.encode("Service updated successfully", "UTF-8"));
            } else {
                response.sendRedirect(request.getContextPath() + 
                    "/View/admin/ServiceForm.jsp?error=" + URLEncoder.encode("Failed to update service", "UTF-8"));
            }
        } catch (Exception e) {
            handleError(request, response, e);
        }
    }

    private void deleteService(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Service service = serviceDAO.getServiceById(id);
            
            if (service == null) {
                response.sendRedirect(request.getContextPath() + 
                    "/View/admin/ServiceForm.jsp?error=" + URLEncoder.encode("Service not found", "UTF-8"));
                return;
            }
            
            if (serviceDAO.deleteService(id)) {
                response.sendRedirect(request.getContextPath() + 
                    "/ServiceController?action=list&success=" + URLEncoder.encode("Service deleted successfully", "UTF-8"));
            } else {
                response.sendRedirect(request.getContextPath() + 
                    "/View/admin/ServiceForm.jsp?error=" + URLEncoder.encode("Failed to delete service", "UTF-8"));
            }
        } catch (Exception e) {
            handleError(request, response, e);
        }
    }

    private void createService(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (!validateInputs(request)) {
                response.sendRedirect(request.getContextPath() + 
                    "/View/admin/ServiceForm.jsp?error=" + URLEncoder.encode("All fields are required", "UTF-8"));
                return;
            }
            
            int categoryId = Integer.parseInt(request.getParameter("category_id"));
            int serviceTypeId = Integer.parseInt(request.getParameter("service_type_id"));
            int frequencyId = Integer.parseInt(request.getParameter("frequency_id"));
            String price = request.getParameter("price");
            
            Service service = new Service(categoryId, serviceTypeId, frequencyId, price);
            
            if (serviceDAO.createService(service)) {
                response.sendRedirect(request.getContextPath() + 
                    "/ServiceController?action=list&success=" + URLEncoder.encode("Service created successfully", "UTF-8"));
            } else {
                response.sendRedirect(request.getContextPath() + 
                    "/View/admin/ServiceForm.jsp?error=" + URLEncoder.encode("Failed to create service", "UTF-8"));
            }
        } catch (Exception e) {
            handleError(request, response, e);
        }
    }
    
    private boolean validateInputs(HttpServletRequest request) {
        String categoryId = request.getParameter("category_id");
        String serviceTypeId = request.getParameter("service_type_id");
        String frequencyId = request.getParameter("frequency_id");
        String price = request.getParameter("price");
        
        return categoryId != null && !categoryId.trim().isEmpty() &&
               serviceTypeId != null && !serviceTypeId.trim().isEmpty() &&
               frequencyId != null && !frequencyId.trim().isEmpty() &&
               price != null && !price.trim().isEmpty();
    }
}