package Model;

import java.sql.*;
import java.util.ArrayList;
import utils.DatabaseConnection;

public class ServiceDAOImpl implements ServiceDAO {
    
    @Override
    public ArrayList<Service> getAllServices() throws SQLException {
        ArrayList<Service> services = new ArrayList<>();
        String sql = "SELECT s.*, c.category_name, st.service_type, " +
                     "f.frequency " +
                     "FROM services s " +
                     "JOIN category c ON s.category_id = c.category_id " +
                     "JOIN servicetype st ON s.service_type_id = st.service_type_id " +
                     "JOIN frequencyoption f ON s.frequency_id = f.frequency_id";
                     
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                services.add(mapResultSetToService(rs));
            }
        }
        return services;
    }
    
    @Override
    public Service getServiceById(int service_id) throws SQLException {
        String sql = "SELECT s.*, c.category_name, st.service_type, " +
                     "f.frequency " +
                     "FROM services s " +
                     "JOIN category c ON s.category_id = c.category_id " +
                     "JOIN servicetype st ON s.service_type_id = st.service_type_id " +
                     "JOIN frequencyoption f ON s.frequency_id = f.frequency_id " +
                     "WHERE s.service_id = ?";
                     
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, service_id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToService(rs);
                }
            }
        }
        return null;
    }
    
    @Override
    public ArrayList<Service> getServicesByCategory(int category_id) throws SQLException {
        ArrayList<Service> services = new ArrayList<>();
        String sql = "SELECT s.*, c.category_name, st.service_type, " +
                     "f.frequency " +
                     "FROM services s " +
                     "JOIN category c ON s.category_id = c.category_id " +
                     "JOIN servicetype st ON s.service_type_id = st.service_type_id " +
                     "JOIN frequencyoption f ON s.frequency_id = f.frequency_id " +
                     "WHERE s.category_id = ?";
                     
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, category_id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    services.add(mapResultSetToService(rs));
                }
            }
        }
        return services;
    }
    
    @Override
    public boolean createService(Service service) throws SQLException {
        String sql = "INSERT INTO services (category_id, service_type_id, frequency_id, price) " +
                     "VALUES (?, ?, ?, ?)";
                     
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, service.getCategory_id());
            stmt.setInt(2, service.getService_type_id());
            stmt.setInt(3, service.getFrequency_id());
            stmt.setString(4, service.getPrice());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    @Override
    public boolean updateService(Service service) throws SQLException {
        String sql = "UPDATE services SET category_id = ?, service_type_id = ?, " +
                     "frequency_id = ?, price = ? WHERE service_id = ?";
                     
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, service.getCategory_id());
            stmt.setInt(2, service.getService_type_id());
            stmt.setInt(3, service.getFrequency_id());
            stmt.setString(4, service.getPrice());
            stmt.setInt(5, service.getService_id());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    @Override
    public boolean deleteService(int service_id) throws SQLException {
        String sql = "DELETE FROM services WHERE service_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, service_id);
            return stmt.executeUpdate() > 0;
        }
    }
    
    private Service mapResultSetToService(ResultSet rs) throws SQLException {
        return new Service(
            rs.getInt("service_id"),
            rs.getInt("category_id"),
            rs.getInt("service_type_id"),
            rs.getInt("frequency_id"),
            rs.getString("price"),
            rs.getString("category_name"),
            rs.getString("service_type"),
            rs.getString("frequency"),
            "active" // Default status or you can add a status column in database
        );
    }
}