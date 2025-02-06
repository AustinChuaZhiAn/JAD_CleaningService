package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import utils.DatabaseConnection;

public class CleanerList implements CleanerRead {
	@Override
	public List<Cleaner> getAllCleaner() throws SQLException {
		List<Cleaner> cleanerList = new ArrayList<>();
		String sql = "SELECT * FROM cleaner";

		try (Connection conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				cleanerList.add(new Cleaner(rs.getInt("cleaner_id"), rs.getString("cleaner_name"),
						rs.getInt("contact")));
			}
		}
		return cleanerList;
	}

	@Override
	public Cleaner getCleanerByBookingId(int id) throws SQLException {
	    String sql = """
	        SELECT c.*
	        FROM cleaner c
	        INNER JOIN booking b ON c.cleaner_id = b.cleaner_id
	        WHERE b.booking_id = ?;
	        """;

	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setInt(1, id);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            Cleaner cleaner = new Cleaner(
	                rs.getInt("cleaner_id"),          
	                rs.getString("cleaner_name"),     
	                rs.getInt("contact")      
	            );
	            return cleaner;
	        } else {
	            return null;
	        }
	    }
	}
	
	@Override
	public Cleaner getCleanerByCleanerId(int id) throws SQLException {
	    String sql = """
	        SELECT * FROM cleaner
	        WHERE cleaner_id = ?;
	        """;

	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setInt(1, id);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            Cleaner cleaner = new Cleaner(
	                rs.getInt("cleaner_id"),          
	                rs.getString("cleaner_name"),     
	                rs.getInt("contact")      
	            );
	            return cleaner;
	        } else {
	            return null;
	        }
	    }
	}
}
