package Model;
import java.sql.*;
import java.util.List;

public interface FrequencyRead {
	
    // Read
    Frequency getFrequencyById(int id) throws SQLException;
    List<Frequency> getAllFrequency() throws SQLException;

}
