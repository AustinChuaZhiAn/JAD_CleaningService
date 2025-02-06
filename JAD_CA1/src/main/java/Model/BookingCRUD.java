package Model;

import java.sql.SQLException;
import java.util.List;

public interface BookingCRUD {
    // Create
    int addBooking(int user_id) throws SQLException;
    
    // Read
    List<Booking> getBookingByUserId(int id) throws SQLException;
    
    // Read
    String getFeedbackByBookingId(int id) throws SQLException;
    //Read
    boolean checkBookingIdToUserId(int booking_id, int user_id) throws SQLException;
    
    //Update
    void updateFeedback(int bookingId, String feedback) throws SQLException;
}
