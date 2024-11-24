package Model;

import java.sql.*;
import utils.DatabaseConnection;

public class ImageDAOImpl implements ImageDAO {
    
    @Override
    public int insertImage(String imgUrl, String deleteHash) throws SQLException {
        String sql = "INSERT INTO image (img_url, deletehash) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, imgUrl);
            pstmt.setString(2, deleteHash);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
            throw new SQLException("Creating image failed, no ID obtained.");
        }
    }

    @Override
    public boolean updateImage(int imgId, String imgUrl, String deleteHash) throws SQLException {
        String sql = "UPDATE image SET img_url = ?, deletehash = ? WHERE img_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, imgUrl);
            pstmt.setString(2, deleteHash);
            pstmt.setInt(3, imgId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    @Override
    public boolean deleteImage(int imgId) throws SQLException {
        String sql = "DELETE FROM image WHERE img_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, imgId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    @Override
    public Image getImageById(int imgId) throws SQLException {
        String sql = "SELECT * FROM image WHERE img_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, imgId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractImageFromResultSet(rs);
                }
            }
        }
        return null;
    }

    private Image extractImageFromResultSet(ResultSet rs) throws SQLException {
        return new Image(
            rs.getInt("img_id"),
            rs.getString("img_url"),
            rs.getString("deletehash")
        );
    }
}