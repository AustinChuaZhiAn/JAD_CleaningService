package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import utils.DatabaseConnection;

public class ServiceCategoryList implements ServiceCategoryCRUD {
    
	@Override
	public List<Category> getAllCategory() throws SQLException {
	    List<Category> categories = new ArrayList<>();
	    String sql = "SELECT c.*, i.img_url FROM category c LEFT JOIN image i ON c.img_id = i.img_id";
	    Connection conn = null;
	    
	    try {
	        conn = DatabaseConnection.getConnection();
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            Category category = new Category();
	            category.setCategory_id(rs.getInt("category_id"));
	            category.setCategory_name(rs.getString("category_name"));
	            category.setDescription(rs.getString("description"));
	            
	            // Handle null for img_id
	            Object imgId = rs.getObject("img_id");
	            if(imgId != null) {
	                category.setImg_id((Integer)imgId);
	            }
	            category.setImg_url(rs.getString("img_url"));
	            
	            categories.add(category);
	            // Debug print
	            System.out.println("Found category: " + category.getCategory_name());
	        }
	        
	        return categories;
	    } catch (SQLException e) {
	        System.out.println("Error in getAllCategory: " + e.getMessage());
	        throw e;
	    } finally {
	        DatabaseConnection.closeConnection(conn);
	    }
	}

    @Override
    public Category getCategoryById(int category_id) throws SQLException {
        String sql = "SELECT c.*, i.img_url FROM category c " +
                    "LEFT JOIN image i ON c.img_id = i.img_id WHERE c.category_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, category_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractCategoryFromResultSet(rs);
                }
            }
        }
        return null;
    }

    @Override
    public Category getCategoryByName(String category_name) throws SQLException {
        String sql = "SELECT c.*, i.img_url FROM category c " +
                    "LEFT JOIN image i ON c.img_id = i.img_id WHERE c.category_name = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, category_name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractCategoryFromResultSet(rs);
                }
            }
        }
        return null;
    }

    @Override
    public boolean createCategory(Category category) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);  

            // First insert the image
            int imageId;
            String imageSql = "INSERT INTO image (img_url) VALUES (?)";
            try (PreparedStatement imageStmt = conn.prepareStatement(imageSql, Statement.RETURN_GENERATED_KEYS)) {
                imageStmt.setString(1, category.getImg_url());
                imageStmt.executeUpdate();
                
                try (ResultSet rs = imageStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        imageId = rs.getInt(1);
                    } else {
                        throw new SQLException("Creating image failed, no ID obtained.");
                    }
                }
            }

            // Then create the category
            String categorySql = "INSERT INTO category (category_name, description, img_id) VALUES (?, ?, ?)";
            try (PreparedStatement categoryStmt = conn.prepareStatement(categorySql)) {
                categoryStmt.setString(1, category.getCategory_name());
                categoryStmt.setString(2, category.getDescription());
                categoryStmt.setInt(3, imageId);
                
                int affectedRows = categoryStmt.executeUpdate();
                if (affectedRows > 0) {
                    conn.commit();
                    return true;
                }
            }
            conn.rollback();
            return false;
            
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new SQLException("Error rolling back transaction", ex);
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    @Override
    public boolean updateCategory(Category category) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Update image if exists, create new if doesn't
            if (category.getImg_id() != 0) {
                String updateImageSql = "UPDATE image SET img_url = ? WHERE img_id = ?";
                try (PreparedStatement imageStmt = conn.prepareStatement(updateImageSql)) {
                    imageStmt.setString(1, category.getImg_url());
                    imageStmt.setInt(2, category.getImg_id());
                    imageStmt.executeUpdate();
                }
            } else {
                // Create new image
                String insertImageSql = "INSERT INTO image (img_url) VALUES (?)";
                try (PreparedStatement imageStmt = conn.prepareStatement(insertImageSql, Statement.RETURN_GENERATED_KEYS)) {
                    imageStmt.setString(1, category.getImg_url());
                    imageStmt.executeUpdate();
                    
                    try (ResultSet rs = imageStmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            category.setImg_id(rs.getInt(1));
                        }
                    }
                }
            }

            // Update category
            String sql = "UPDATE category SET category_name = ?, description = ?, img_id = ? WHERE category_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, category.getCategory_name());
                pstmt.setString(2, category.getDescription());
                pstmt.setInt(3, category.getImg_id());
                pstmt.setInt(4, category.getCategory_id());
                
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    conn.commit();
                    return true;
                }
            }
            conn.rollback();
            return false;
            
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new SQLException("Error rolling back transaction", ex);
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    @Override
    public boolean deleteCategory(int category_id) throws SQLException {
        String sql = "DELETE FROM category WHERE category_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, category_id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    private Category extractCategoryFromResultSet(ResultSet rs) throws SQLException {
        System.out.println("Extracting category from ResultSet"); 
        Category category = new Category(
            rs.getInt("category_id"),
            rs.getString("category_name"),
            rs.getString("description"),
            rs.getObject("img_id") != null ? rs.getInt("img_id") : null,
            rs.getString("img_url")
        );
        System.out.println("Category extracted: " + category.getCategory_name()); 
        return category;
    }
    
    @Override
    public List<Service> getAllServices() throws SQLException {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT s.*, c.category_name, c.img_id, c.description, i.img_url, st.service_type, f.frequency " +
                    "FROM services s " +
                    "JOIN category c ON s.category_id = c.category_id " +
                    "JOIN image i ON c.img_id = i.img_id " +
                    "JOIN servicetype st ON s.service_type_id = st.service_type_id " +
                    "JOIN frequencyoption f ON s.frequency_id = f.frequency_id";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Service service = new Service();
                service.setService_id(rs.getInt("service_id"));
                service.setCategory_id(rs.getInt("category_id"));
                service.setService_type_id(rs.getInt("service_type_id"));
                service.setFrequency_id(rs.getInt("frequency_id"));
                service.setPrice(rs.getString("price"));
                // Set the display fields
                service.setCategory_name(rs.getString("category_name"));
                service.setCategory_img_url("img_url");
                service.setDescription("description");
                service.setService_type(rs.getString("service_type"));
                service.setFrequency(rs.getString("frequency"));
                
                services.add(service);
            }
        }
        return services;
    }

    @Override
    public Service getServiceById(int service_id) throws SQLException {
        String sql = "SELECT s.*, c.category_name, c.img_id, c.description, i.img_url, st.service_type, f.frequency " +
                    "FROM services s " +
                    "JOIN category c ON s.category_id = c.category_id " +
                    "JOIN image i ON c.img_id = i.img_id " +
                    "JOIN servicetype st ON s.service_type_id = st.service_type_id " +
                    "JOIN frequencyoption f ON s.frequency_id = f.frequency_id " +
                    "WHERE s.service_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, service_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Service service = new Service();
                    service.setService_id(rs.getInt("service_id"));
                    service.setCategory_id(rs.getInt("category_id"));
                    service.setService_type_id(rs.getInt("service_type_id"));
                    service.setFrequency_id(rs.getInt("frequency_id"));
                    service.setPrice(rs.getString("price"));
                    // Set the display fields
                    service.setCategory_name(rs.getString("category_name"));
                    service.setCategory_img_url("img_url");
                    service.setDescription("description");
                    service.setService_type(rs.getString("service_type"));
                    service.setFrequency(rs.getString("frequency"));
                    
                    return service;
                }
            }
        }
        return null;
    }

    @Override
    public List<Service> getServicesByCategory(int category_id) throws SQLException {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT s.*, c.category_name, c.img_id, c.description, i.img_url, st.service_type, f.frequency " +
                    "FROM services s " +
                    "JOIN category c ON s.category_id = c.category_id " +
                    "JOIN image i ON c.img_id = i.img_id " +
                    "JOIN servicetype st ON s.service_type_id = st.service_type_id " +
                    "JOIN frequencyoption f ON s.frequency_id = f.frequency_id " +
                    "WHERE s.category_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, category_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Service service = new Service();
                    service.setService_id(rs.getInt("service_id"));
                    service.setCategory_id(rs.getInt("category_id"));
                    service.setService_type_id(rs.getInt("service_type_id"));
                    service.setFrequency_id(rs.getInt("frequency_id"));
                    service.setPrice(rs.getString("price"));
                    // Set the display fields
                    service.setCategory_name(rs.getString("category_name"));
                    service.setCategory_img_url("img_url");
                    service.setDescription("description");
                    service.setService_type(rs.getString("service_type"));
                    service.setFrequency(rs.getString("frequency"));
                    
                    services.add(service);
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
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, service.getCategory_id());
            pstmt.setInt(2, service.getService_type_id());
            pstmt.setInt(3, service.getFrequency_id());
            pstmt.setString(4, service.getPrice());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    @Override
    public boolean updateService(Service service) throws SQLException {
        String sql = "UPDATE services SET category_id = ?, service_type_id = ?, " +
                    "frequency_id = ?, price = ? WHERE service_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, service.getCategory_id());
            pstmt.setInt(2, service.getService_type_id());
            pstmt.setInt(3, service.getFrequency_id());
            pstmt.setString(4, service.getPrice());
            pstmt.setInt(5, service.getService_id());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    @Override
    public boolean deleteService(int service_id) throws SQLException {
        String sql = "DELETE FROM services WHERE service_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, service_id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

	public int getServiceIdByDetails(int category_id, int service_type_id, int frequency_id) throws SQLException {
	String sql = "SELECT service_id FROM services WHERE category_id = ? AND service_type_id = ? AND frequency_id = ?";
	try (Connection conn = DatabaseConnection.getConnection(); 
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setInt(1, category_id);
	        pstmt.setInt(2, service_type_id);
	        pstmt.setInt(3, frequency_id);

	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt("service_id");
	            }
	        }
	    }
	    return -1;
}

	
}
	
	

