package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import utils.DatabaseConnection;

public class BookingList implements BookingCRUD {
	@Override
	public int addBooking(int user_id, int address_id, LocalTime time, LocalDate date, int cleaner_id, int service_id, String special_request) throws SQLException {
	    // SQL query for inserting a new booking into the 'booking' table
	    String sql = """
	        INSERT INTO booking 
	        (user_id, address_id, time, date, cleaner_id, service_id, special_request, status_id) 
	        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
	    """;
	    
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

	        pstmt.setInt(1, user_id);
	        pstmt.setInt(2, address_id);
	        
	        if (time != null) {
	            pstmt.setTime(3, java.sql.Time.valueOf(time)); // Set time
	        } else {
	            pstmt.setNull(3, java.sql.Types.TIME);  // Handle null values
	        }

	        if (date != null) {
	            pstmt.setDate(4, java.sql.Date.valueOf(date)); // Set date
	        } else {
	            pstmt.setNull(4, java.sql.Types.DATE);  // Handle null values
	        }
	        
	        pstmt.setInt(5, cleaner_id);
	        pstmt.setInt(6, service_id);
	        pstmt.setString(7, special_request);
	        pstmt.setInt(8, 1);

	        pstmt.executeUpdate();

	        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                return generatedKeys.getInt(1);
	            }
	        }
	    }
	    return -1; // Return -1 if booking_id could not be retrieved
	}

	@Override
	public List<Booking> getBookingByUserId(int userId) throws SQLException {
	    String sql = """
	    		SELECT * FROM booking WHERE user_id = ?
	    """;

	    List<Booking> listBooking = new ArrayList<>();

	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setInt(1, userId);

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	            	Booking booking = new Booking(
	                        rs.getInt("booking_id"),                   
	                        rs.getInt("user_id"),
	                        rs.getInt("address_id"),
	                        rs.getTime("time").toLocalTime(),          
	                        rs.getDate("date").toLocalDate(),          
	                        rs.getInt("cleaner_id"),                   
	                        rs.getInt("service_id"),                   
	                        rs.getString("special_request"),           
	                        rs.getInt("status_id") 
	                );
	            	listBooking.add(booking);
	            }
	        }
	    }

	    return listBooking;
	}
}
