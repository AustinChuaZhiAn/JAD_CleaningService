package Model;

import java.sql.SQLException;
import java.util.List;

public interface CleanerRead {
	//Read
    List<Cleaner> getAllCleaner() throws SQLException;
    Cleaner getCleanerByCleanerId(int id) throws SQLException;
    Cleaner getCleanerByBookingId(int id) throws SQLException;
    boolean createCleaner(String cleanerName, String contact) throws SQLException;
    boolean deleteCleaner(int id) throws SQLException;
}
