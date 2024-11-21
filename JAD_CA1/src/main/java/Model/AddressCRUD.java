package Model;

import java.sql.SQLException;
import java.util.List;

public interface AddressCRUD {
	
    // Create
    void addAddress(Address address) throws SQLException;
    
    // Read
    Address getAddressById(int id) throws SQLException;
    List<Address> getAllAddress() throws SQLException;
    
    // Update
    void updateAddress(Address address) throws SQLException;
    
    // Delete
    void deleteAddress(int id) throws SQLException;
}
