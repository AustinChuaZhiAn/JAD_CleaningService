package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import utils.DatabaseConnection;

public class AddressTypeList implements AddressTypeRead{
	@Override
    public List<AddressType> getAllAddressType() throws SQLException {
		List<AddressType> addressTypeList = new ArrayList<>();
		String sql = "SELECT * FROM addresstype";

		try (Connection conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				addressTypeList.add(new AddressType(rs.getInt("address_type_id")
						,rs.getString("address_type")));
			}
		}
		return addressTypeList;
    }
}
