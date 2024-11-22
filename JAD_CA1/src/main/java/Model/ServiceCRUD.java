package Model;
import java.sql.*;
import java.util.List;

public interface ServiceCRUD {
	// Read
    List<Service> getAllServices() throws SQLException;
    int getServiceIdByDetails(int category_id, int service_type_id, int frequency_id) throws SQLException;
}
