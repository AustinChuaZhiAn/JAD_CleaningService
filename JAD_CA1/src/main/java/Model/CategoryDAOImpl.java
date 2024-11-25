package Model;

import java.sql.*;
import java.util.ArrayList;
import utils.DatabaseConnection;

public class CategoryDAOImpl implements CategoryDAO {
    
    @Override
    public ArrayList<Category> getAllCategory() throws SQLException {
        ArrayList<Category> categories = new ArrayList<>();
        String sql = "SELECT c.*, i.img_url, i.deletehash " +
                    "FROM category c " +
                    "LEFT JOIN image i ON c.img_id = i.img_id";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Category category = extractCategoryFromResultSet(rs);
                categories.add(category);
            }
        }
        return categories;
    }

    @Override
    public Integer getTotalCategory() throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM category";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            return rs.next() ? rs.getInt("total") : 0;
        }
    }

    @Override
    public Category getCategoryById(int category_id) throws SQLException {
        String sql = "SELECT c.*, i.img_url, i.deletehash " +
                    "FROM category c " +
                    "LEFT JOIN image i ON c.img_id = i.img_id " +
                    "WHERE c.category_id = ?";
        
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
        String sql = "SELECT c.*, i.img_url, i.deletehash FROM category c " +
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
        String sql = "INSERT INTO category (category_name, description, img_id) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, category.getCategory_name());
                pstmt.setString(2, category.getDescription());
                pstmt.setInt(3, category.getImg_id()); 
                
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    conn.commit();
                    return true;
                }
                
                conn.rollback();
                return false;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    @Override
    public boolean updateCategory(Category category) throws SQLException {
        String sql = "UPDATE category SET category_name = ?, description = ?, img_id = ? WHERE category_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            
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
                
                conn.rollback();
                return false;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    @Override
    public boolean deleteCategory(int category_id) throws SQLException {
        String sql = "DELETE FROM category WHERE category_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, category_id);
                int affectedRows = pstmt.executeUpdate();
                
                if (affectedRows > 0) {
                    conn.commit();
                    return true;
                }
                
                conn.rollback();
                return false;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    private Category extractCategoryFromResultSet(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setCategory_id(rs.getInt("category_id"));
        category.setCategory_name(rs.getString("category_name"));
        category.setDescription(rs.getString("description"));
        category.setImg_id(rs.getInt("img_id"));
        category.setImg_url(rs.getString("img_url"));
        category.setDelete_hash(rs.getString("deletehash"));
        return category;
    }
    }