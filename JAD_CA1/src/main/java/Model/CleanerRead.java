package Model;

import java.sql.SQLException;
import java.util.List;

public interface CleanerRead {
	//Read
    List<Cleaner> getAllCleaner() throws SQLException;
}
