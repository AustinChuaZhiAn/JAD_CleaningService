package Model;

import java.sql.SQLException;
import java.util.List;

public interface AddressTypeRead {
	//Read
    List<AddressType> getAllAddressType() throws SQLException;
}
