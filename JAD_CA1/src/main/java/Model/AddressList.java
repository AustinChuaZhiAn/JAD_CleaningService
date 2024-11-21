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
	public void addAddress(Address address) throws SQLException {
		String sql = "INSERT INTO frequency (frequency_id, frequency) VALUES (?, ?)";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			
			pstmt.setInt(1, address.getAddress());
			pstmt.setInt(2, address.getUser_details());

			pstmt.executeUpdate();

			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					address.setAddress(generatedKeys.getInt(1));
				}
			}
		}
	}

	@Override
	public Address getAddressById(int id) throws SQLException {
		String sql = "SELECT * FROM address WHERE address = ?";
		try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, id);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new Address(rs.getInt("address"), rs.getInt("user_details")
							, rs.getInt("postal_code"), rs.getString("block_number"), rs.getString("street_name")
							, rs.getString("unit_number"), rs.getString("building_name"), rs.getInt("address_type_id"));
				}
			}
		}
		return null;
	}

	@Override
	public List<Address> getAllAddress() throws SQLException {
		List<Address> address = new ArrayList<>();
		String sql = "SELECT * FROM address";

		try (Connection conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				address.add(new Address(rs.getInt("address"), rs.getInt("user_details")
						, rs.getInt("postal_code"), rs.getString("block_number"), rs.getString("street_name")
						, rs.getString("unit_number"), rs.getString("building_name"), rs.getInt("address_type_id")));
			}
		}
		return address;
	}

	@Override
	public void updateAddress(Address address) throws SQLException {
		String sql = "UPDATE Address SET user_details = ?, postal_code = ?, block_number = ?, street_name = ?, unit_number = ?, building_name = ?, address_type_id = ? WHERE address = ?;";

		try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, address.getUser_details());
			pstmt.setInt(2, address.getPostal_code());
			pstmt.setString(3, address.getBlock_number());
			pstmt.setString(4, address.getStreet_name());
			pstmt.setString(5, address.getUnit_number());
			pstmt.setString(6, address.getBuilding_name());
			pstmt.setInt(7, address.getAddress_type_id());
			pstmt.setInt(8, address.getAddress());

			pstmt.executeUpdate();
		}
	}

	@Override
	public void deleteAddress(int id) throws SQLException {
		String sql = "DELETE FROM address WHERE address = ?";

		try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		}
	}
}
