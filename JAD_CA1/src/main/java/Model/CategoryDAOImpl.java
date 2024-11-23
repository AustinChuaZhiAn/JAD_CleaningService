package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.DatabaseConnection;

public class CategoryDAOImpl implements CategoryDAO {
    
    @Override
    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM category";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Category category = new Category();
                category.setCategory_id(rs.getInt("category_id"));
                category.setCategory_name(rs.getString("category_name"));
                category.setDescription(rs.getString("description"));
                category.setImg_id(rs.getInt("img_id"));
                categories.add(category);
            }
        }
        return categories;
    }
    
    
    @Override
    public Category getCategoryById(int categoryId) throws SQLException {
        String sql = "SELECT * FROM category WHERE category_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Category category = new Category();
                    category.setCategory_id(rs.getInt("category_id"));
                    category.setCategory_name(rs.getString("category_name"));
                    category.setDescription(rs.getString("description"));
                    category.setImg_id(rs.getInt("img_id"));
                    return category;
                }
            }
        }

        return null;
    }
}