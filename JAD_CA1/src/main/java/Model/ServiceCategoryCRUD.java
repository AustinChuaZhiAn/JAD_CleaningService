package Model;

import java.sql.SQLException;
import java.util.List;

public interface ServiceCategoryCRUD {
    List<Category> getAllCategory() throws SQLException;
    Category getCategoryById(int category_id) throws SQLException;
    Category getCategoryByName(String category_name) throws SQLException;
    boolean createCategory(Category category) throws SQLException;
    boolean updateCategory(Category category) throws SQLException;
    boolean deleteCategory(int category_id) throws SQLException;

    // Service operations
    List<Service> getAllServices() throws SQLException;
    Service getServiceById(int service_id) throws SQLException;
    List<Service> getServicesByCategory(int category_id) throws SQLException;
    boolean createService(Service service) throws SQLException;
    boolean updateService(Service service) throws SQLException;
    boolean deleteService(int service_id) throws SQLException;
    
    // Optional: Combined operations that might be useful
//    List<ServiceCategoryDTO> getServicesWithCategories() throws SQLException;
//    int getServiceIdByDetails(int category_id, int service_type_id, int frequency_id) throws SQLException;
}
