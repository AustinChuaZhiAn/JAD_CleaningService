package Model;
import java.sql.*;
import java.util.List;

public interface ServiceTypeRead {
    // Read
    ServiceType getServiceTypeById(int id) throws SQLException;
    List<ServiceType> getAllServiceType() throws SQLException;
}
