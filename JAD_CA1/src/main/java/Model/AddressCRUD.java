package Model;

import java.sql.SQLException;
import java.util.List;

public interface AddressCRUD {
	
    // Create
    int addAddress(int user_details_id, int postal_code, String block_number, String street_name
			, String unit_number, String building_name, int address_type_id) throws SQLException;
    
    // Read
    Address getAddressByAddressId(int id) throws SQLException;
    List<Address> getAddressesByUserId(int id) throws SQLException;
    List<Address> getAllAddress() throws SQLException;
    List<AddressType> getAllAddressType() throws SQLException;
    
}
