package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import utils.DatabaseConnection;

public class AddressList implements AddressCRUD {
	@Override
	public int addAddress(int user_details_id, int postal_code, String block_number, String street_name,
			String unit_number, String building_name, int address_type_id) throws SQLException {
		String sql = """
				    INSERT INTO address
				    (user_details_id, postal_code, block_number, street_name, unit_number, building_name, address_type_id)
				    VALUES (?, ?, ?, ?, ?, ?, ?)
				""";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			// Set the parameters based on the Address object
			pstmt.setInt(1, user_details_id);
			pstmt.setInt(2, postal_code);
			pstmt.setString(3, block_number);
			pstmt.setString(4, street_name);
			pstmt.setString(5, unit_number);
			pstmt.setString(6, building_name);
			pstmt.setInt(7, address_type_id);

			// Execute the update
			pstmt.executeUpdate();

			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1);
				}
			}
		}
		return -1;
	}

	@Override
	public List<Address> getAddressesByUserId(int userId) throws SQLException {
		String sql = """
				    SELECT a.address_id, a.user_details_id, a.postal_code, a.block_number, a.street_name,
				           a.unit_number, a.building_name, a.address_type_id
				    FROM address a
				    JOIN userdetails ud ON a.user_details_id = ud.user_details_id
				    JOIN user u ON ud.user_id = u.user_id
				    WHERE u.user_id = ?;
				""";

		List<Address> addresses = new ArrayList<>();

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, userId);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Address address = new Address(rs.getInt("address_id"), rs.getInt("user_details_id"),
							rs.getInt("postal_code"), rs.getString("block_number"), rs.getString("street_name"),
							rs.getString("unit_number"), rs.getString("building_name"), rs.getInt("address_type_id"));
					addresses.add(address);
				}
			}
		}

		return addresses;
	}

	@Override
	public List<Address> getAllAddress() throws SQLException {
		List<Address> address = new ArrayList<>();
		String sql = "SELECT * FROM address";

		try (Connection conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				address.add(new Address(rs.getInt("address_id"), rs.getInt("user_details_id"), rs.getInt("postal_code"),
						rs.getString("block_number"), rs.getString("street_name"), rs.getString("unit_number"),
						rs.getString("building_name"), rs.getInt("address_type_id")));
			}
		}
		return address;
	}

	@Override
	public List<AddressType> getAllAddressType() throws SQLException {
		List<AddressType> addresstype = new ArrayList<>();
		String sql = "SELECT * FROM addresstype";

		try (Connection conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				addresstype.add(new AddressType(rs.getInt("address_type_id"), rs.getString("address_type")));
			}
		}
		return addresstype;
	}

	@Override
	public Address getAddressByAddressId(int id) throws SQLException {
		String sql = """
				    SELECT *
				    FROM address
				    WHERE address_id = ?;
				""";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, id);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
				return new Address(rs.getInt("address_id"), rs.getInt("user_details_id"), rs.getInt("postal_code"),
						rs.getString("block_number"), rs.getString("street_name"), rs.getString("unit_number"),
						rs.getString("building_name"), rs.getInt("address_type_id"));
				}
			}
		}
		return null;
	}
}
