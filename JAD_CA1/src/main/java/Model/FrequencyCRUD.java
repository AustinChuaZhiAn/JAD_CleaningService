package Model;
import java.sql.*;
import java.util.List;

public interface FrequencyCRUD {
	
    // Create
    void addFrequency(Frequency frequency) throws SQLException;
    
    // Read
    Frequency getFrequencyById(int id) throws SQLException;
    List<Frequency> getAllFrequency() throws SQLException;
    
    // Update
    void updateFrequency(Frequency frequency) throws SQLException;
    
    // Delete
    void deleteFrequency(int id) throws SQLException;
}
