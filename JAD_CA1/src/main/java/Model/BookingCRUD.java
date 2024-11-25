package Model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface BookingCRUD {
    // Create
    int addBooking(int user_id, int address_id, LocalTime time, LocalDate date, int cleaner_id, int service_id, String special_request) throws SQLException;
    
    // Read
    List<Booking> getBookingByUserId(int id) throws SQLException;
}
