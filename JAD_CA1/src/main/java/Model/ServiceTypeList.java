package Model;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import utils.DatabaseConnection;

public class ServiceTypeList implements ServiceTypeRead {
	@Override
	public ServiceType getServiceTypeById(int id) throws SQLException {
		String sql = "SELECT * FROM servicetype WHERE service_type_id = ?";
		try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, id);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new ServiceType(rs.getInt("service_type_id"), rs.getString("service_type"));
				}
			}
		}
		return null;
	}

	@Override
	public List<ServiceType> getAllServiceType() throws SQLException {
		List<ServiceType> serviceType = new ArrayList<>();
		String sql = "SELECT * FROM servicetype";

		try (Connection conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				serviceType.add(new ServiceType(rs.getInt("service_type_id"), rs.getString("service_type")));
			}
		}
		return serviceType;
	}
}
