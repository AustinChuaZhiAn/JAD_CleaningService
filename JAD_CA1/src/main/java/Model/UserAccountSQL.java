package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import utils.DatabaseConnection;

public class UserAccountSQL implements UserAccountCRUD{
	
	 @Override
	    public UserAccount verifyUser(String username, String password) throws SQLException {
	        try (Connection conn = DatabaseConnection.getConnection()) {
	            if (conn == null) {
	                throw new SQLException("Unable to establish database connection");
	            }
	            
	            String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
	            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                pstmt.setString(1, username);
	                pstmt.setString(2, password);
	                
	                try (ResultSet rs = pstmt.executeQuery()) {
	                    if (rs.next()) {
	                        return extractUserFromResultSet(rs);
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            System.out.println("Database error in verifyUser: " + e.getMessage());
	            e.printStackTrace();
	            throw e;
	        }
	        return null;
	    }
    
	 @Override
	 public boolean createUser(UserAccount user, UserDetails userDetails) throws SQLException {
		    String checkSql = "SELECT username FROM user WHERE username = ?";
		    String insertUserSql = "INSERT INTO user (username, password, role_id) VALUES (?, ?, ?)"; 
		    String insertDetailsSql = "INSERT INTO userdetails (user_id, email, phone_number) VALUES (?, ?, ?)";

		    Connection conn = null;
		    try {
		        conn = DatabaseConnection.getConnection();
		        conn.setAutoCommit(false);  // Start transaction

		        // Check if username exists
		        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
		            checkStmt.setString(1, user.getUsername());
		            ResultSet rs = checkStmt.executeQuery();
		            if (rs.next()) {
		                return false;
		            }
		        }

		        // Insert user
		        int userId;
		        try (PreparedStatement userStmt = conn.prepareStatement(insertUserSql, Statement.RETURN_GENERATED_KEYS)) {
		            userStmt.setString(1, user.getUsername());
		            userStmt.setString(2, user.getPassword());
		            userStmt.setInt(3, user.getRole_id());
		            
		            int affectedRows = userStmt.executeUpdate();
		            
		            if (affectedRows == 0) {
		                throw new SQLException("Creating user failed, no rows affected.");
		            }

		            try (ResultSet generatedKeys = userStmt.getGeneratedKeys()) {
		                if (generatedKeys.next()) {
		                    userId = generatedKeys.getInt(1);
		                } else {
		                    throw new SQLException("Creating user failed, no ID obtained.");
		                }
		            }
		        }

		        // Insert user details
		        try (PreparedStatement detailsStmt = conn.prepareStatement(insertDetailsSql)) {
		            detailsStmt.setInt(1, userId);
		            detailsStmt.setString(2, userDetails.getEmail());
		            if (userDetails.getPhone_number() == null || userDetails.getPhone_number().trim().isEmpty()) {
		                detailsStmt.setNull(3, java.sql.Types.VARCHAR);
		            } else {
		                detailsStmt.setString(3, userDetails.getPhone_number());
		            }
		            detailsStmt.executeUpdate();
		        }

		        conn.commit();  
		        return true;

		    } catch (SQLException e) {
		        if (conn != null) {
		            try {
		                conn.rollback(); 
		            } catch (SQLException ex) {
		                throw new SQLException("Error rolling back transaction", ex);
		            }
		        }
		        throw new SQLException("Error creating user and details", e);
		    } finally {
		        if (conn != null) {
		            try {
		                conn.setAutoCommit(true);
		                conn.close();
		            } catch (SQLException e) {
		                throw new SQLException("Error closing connection", e);
		            }
		        }
		    }
		}
	 @Override
	 public UserDetails getUserDetailsByEmail(String email) throws SQLException {
		    String sql = "SELECT * FROM userdetails WHERE email = ?";
		    
		    try (Connection conn = DatabaseConnection.getConnection();
		         PreparedStatement pstmt = conn.prepareStatement(sql)) {
		        
		        pstmt.setString(1, email);
		        try (ResultSet rs = pstmt.executeQuery()) {
		            if (rs.next()) {
		                UserDetails details = new UserDetails();
		                details.setUser_details_id(rs.getInt("user_details_id"));
		                details.setUser_id(rs.getInt("user_id"));
		                details.setEmail(rs.getString("email"));
		                details.setPhone_number(rs.getString("phone_number"));
		                details.setCreated_at(rs.getTimestamp("created_at"));
		                details.setUpdated_at(rs.getTimestamp("updated_at"));
		                return details;
		            }
		        }
		    }
		    return null;
		}
	 
	 
	 @Override
	 public UserDetails getUserDetailsByPhone(String phone) throws SQLException {
		    String sql = "SELECT * FROM userdetails WHERE phone_number = ?";
		    
		    try (Connection conn = DatabaseConnection.getConnection();
		         PreparedStatement pstmt = conn.prepareStatement(sql)) {
		        
		        pstmt.setString(1, phone);
		        try (ResultSet rs = pstmt.executeQuery()) {
		            if (rs.next()) {
		                UserDetails details = new UserDetails();
		                details.setUser_details_id(rs.getInt("user_details_id"));
		                details.setUser_id(rs.getInt("user_id"));
		                details.setEmail(rs.getString("email"));
		                details.setPhone_number(rs.getString("phone_number"));
		                details.setCreated_at(rs.getTimestamp("created_at"));
		                details.setUpdated_at(rs.getTimestamp("updated_at"));
		                return details;
		            }
		        }
		    }
		    return null;
		}
	 
	 
	 
    @Override
    public List<UserAccount> getAllUser() throws SQLException {
        List<UserAccount> users = new ArrayList<>();
        String sql = "SELECT * FROM user";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                users.add(extractUserFromResultSet(rs));
            }
        }
        return users;
    }
    
    public boolean updateUser(UserAccount user) throws SQLException {
        String sql = "UPDATE user SET username = ?, password = ?, role_id = ?, " +
                    "updated_at = CURRENT_TIMESTAMP WHERE user_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setInt(3, user.getRole_id());
            pstmt.setInt(4, user.getUser_id());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    public boolean deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM user WHERE user_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    public UserAccount getUserByUsername(String username) throws SQLException {
    	   String sql = "SELECT * FROM user WHERE username = ?";
    	   
    	   try (Connection conn = DatabaseConnection.getConnection();
    	        PreparedStatement pstmt = conn.prepareStatement(sql)) {
    	       
    	       pstmt.setString(1, username);
    	       try (ResultSet rs = pstmt.executeQuery()) {
    	           if (rs.next()) {
    	               return extractUserFromResultSet(rs);
    	           }
    	       }
    	   }
    	   return null;
    	}
    
    
    
    
    private UserAccount extractUserFromResultSet(ResultSet rs) throws SQLException {
        UserAccount user = new UserAccount();
        user.setUser_id(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setRole_id(rs.getInt("role_id"));
        user.setCreated_at(rs.getTimestamp("created_at"));
        user.setUpdated_at(rs.getTimestamp("updated_at"));
        return user;
    }
}