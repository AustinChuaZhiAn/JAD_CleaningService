package Model;

import java.sql.SQLException;
import java.util.List;

public interface BookingToServiceCRUD {
    // Create
    int addBookingServiceEntry(int booking_id, List<String> serviceDetails) throws SQLException;
    
    // Read
    List<BookingToService> getbtsByBookingId(int booking_id) throws SQLException;
    
    
    List<BookingToService> getBookingsByCleanerId(int cleaner_id) throws SQLException;
    boolean updateBookingStatus(int bookingServiceId, int statusId) throws SQLException;
}
