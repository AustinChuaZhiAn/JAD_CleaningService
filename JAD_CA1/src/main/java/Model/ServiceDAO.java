package Model;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ServiceDAO {
    ArrayList<Service> getAllServices() throws SQLException;
    Service getServiceById(int service_id) throws SQLException;
    ArrayList<Service> getServicesByCategory(int category_id) throws SQLException;
    boolean createService(Service service) throws SQLException;
    boolean updateService(Service service) throws SQLException;
    boolean deleteService(int service_id) throws SQLException;
    int getServiceIdByDetails(int category_id, int service_type_id, int frequency_id) throws SQLException;
}
