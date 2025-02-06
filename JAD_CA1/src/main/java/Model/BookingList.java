package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import utils.DatabaseConnection;

public class BookingList implements BookingCRUD {
	@Override
	public int addBooking(int user_id) throws SQLException {
		// SQL query for inserting a new booking into the 'booking' table
		String sql = """
				    INSERT INTO booking
				    (user_id, status_id)
				    VALUES (?, ?)
				""";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			pstmt.setInt(1, user_id);

//			if (time != null) {
//				pstmt.setTime(3, java.sql.Time.valueOf(time)); // Set time
//			} else {
//				pstmt.setNull(3, java.sql.Types.TIME); // Handle null values
//			}
//
//			if (date != null) {
//				pstmt.setDate(4, java.sql.Date.valueOf(date)); // Set date
//			} else {
//				pstmt.setNull(4, java.sql.Types.DATE); // Handle null values
//			}

//			pstmt.setInt(5, cleaner_id);
			pstmt.setInt(2, 1);

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
				SELECT
				    b.booking_id,
				    b.user_id,
				    b.created_at,
				    b.updated_at,
				    b.status_id,
				    b.feedback,
				    s.status_name
				FROM booking b
				JOIN status s ON b.status_id = s.status_id
				WHERE b.user_id = ?
					    """;

		List<Booking> listBooking = new ArrayList<>();

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, userId);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Booking booking = new Booking(rs.getInt("booking_id"), rs.getInt("user_id"),
							rs.getInt("status_id"),
							rs.getString("status_name"), rs.getString("feedback"));
					listBooking.add(booking);
				}
			}
		}

		return listBooking;
	}

	@Override
	public boolean checkBookingIdToUserId(int booking_id, int user_id) throws SQLException {
		String sql = """
				SELECT COUNT(*) FROM booking WHERE booking_id = ? AND user_id = ?;
					    """;
		
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, booking_id);
			pstmt.setInt(2, user_id);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		}

		return false;
	}
	
	@Override
	public void updateFeedback(int bookingId, String feedback) throws SQLException {
		String sql = """
				UPDATE booking SET feedback = ? WHERE booking_id = ?
					    """;

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, feedback);
			stmt.setInt(2, bookingId);

			int rowsUpdated = stmt.executeUpdate();
			System.out
					.println("Feedback updated for booking ID: " + bookingId + " (Rows affected: " + rowsUpdated + ")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getFeedbackByBookingId(int id) throws SQLException {
		String sql = """
				SELECT feedback FROM booking WHERE booking_id = ?;
				""";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, id);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getString(1);
				}
			}
		}
		return "";
	}
}
