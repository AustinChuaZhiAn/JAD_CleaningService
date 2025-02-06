package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import utils.DatabaseConnection;

public class BookingToServiceList implements BookingToServiceCRUD {
	// Create
	public int addBookingServiceEntry(int booking_id, List<String> serviceDetails) throws SQLException {
		// SQL query for inserting a new booking into the 'booking' table
		String sql = """
				    INSERT INTO bookingtoservice
				    (booking_id, service_id, special_request, time, date, cleaner_id, address_id, status_id)
				    VALUES (?, ?, ?, ?, ?, ?, ?, ?)
				""";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			String specialRequest = serviceDetails.get(0);
			int service_id = Integer.parseInt(serviceDetails.get(1));

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm");

			LocalDateTime dateTime = LocalDateTime.parse(serviceDetails.get(2), formatter);

			LocalDate date = dateTime.toLocalDate(); // Extracts only the date for date column
			LocalTime time = dateTime.toLocalTime(); // Extracts only the time for time column

			int cleaner_id = Integer.parseInt(serviceDetails.get(3));
			int address_id = Integer.parseInt(serviceDetails.get(4));

			// 0 = special Request, 1 = service_id, 2 = date&time, 3 = cleaner_id, 4 =
			// address_id
			pstmt.setInt(1, booking_id);
			pstmt.setInt(2, service_id);
			pstmt.setString(3, specialRequest);

			if (time != null) {
				pstmt.setTime(4, java.sql.Time.valueOf(time)); // Set time
			} else {
				pstmt.setNull(4, java.sql.Types.TIME); // Handle null values
			}

			if (date != null) {
				pstmt.setDate(5, java.sql.Date.valueOf(date)); // Set date
			} else {
				pstmt.setNull(5, java.sql.Types.DATE); // Handle null values
			}

			pstmt.setInt(6, cleaner_id);
			pstmt.setInt(7, address_id);
			pstmt.setInt(8, 4);

			pstmt.executeUpdate();

			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1);
				}
			}
		}
		return -1; // Return -1 if booking_id could not be retrieved
	}

	// Read
	public List<BookingToService> getbtsByBookingId(int booking_id) throws SQLException {
		String sql = """
				SELECT
					bts.booking_service_id,
				    bts.booking_id,
				    bts.service_id,
				    bts.special_request,
				    bts.date,
				    bts.time,
				    bts.cleaner_id,
				    bts.address_id,
				    bts.status_id,
				    s.status_name
				FROM bookingtoservice bts
				JOIN status s ON bts.status_id = s.status_id
				WHERE bts.booking_id = ?
					    """;

		List<BookingToService> listBookingToService = new ArrayList<>();

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, booking_id);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					BookingToService bts = new BookingToService(rs.getInt("booking_service_id"), rs.getInt("booking_id"),
							rs.getInt("service_id"), rs.getInt("address_id"), rs.getTime("time").toLocalTime(), rs.getDate("date").toLocalDate(),
							rs.getInt("cleaner_id"), rs.getString("special_request"), rs.getInt("status_id"), rs.getString("status_name"));
					listBookingToService.add(bts);
				}
			}
		}

		return listBookingToService;
	}
}
