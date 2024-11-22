package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import utils.DatabaseConnection;

public class ServiceList implements ServiceCRUD {
	@Override
	public List<Service> getAllServices() throws SQLException {
		List<Service> services = new ArrayList<>();
		String sql = """
				SELECT s.*, c.category_name
                FROM services s
                JOIN categories c ON s.category_id = c.category_id
				""";

		try (Connection conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				services.add(new Service(rs.getInt("service_id")
						, rs.getInt("category_id"), rs.getString("category_name")
						, rs.getInt("service_type_id") , rs.getInt("frequency_id"), rs.getDouble("price")));
			}
		}
		return services;
	}
	
	@Override
	public int getServiceIdByDetails(int category_id, int service_type_id, int frequency_id) throws SQLException {
		String sql = "SELECT service_id FROM services WHERE category_id = ? AND service_type_id = ? AND frequency_id = ?";
		try (Connection conn = DatabaseConnection.getConnection(); 
		         PreparedStatement pstmt = conn.prepareStatement(sql)) {
		        
		        pstmt.setInt(1, category_id);
		        pstmt.setInt(2, service_type_id);
		        pstmt.setInt(3, frequency_id);

		        try (ResultSet rs = pstmt.executeQuery()) {
		            if (rs.next()) {
		                return rs.getInt("service_id");
		            }
		        }
		    }
		    return -1;
	}
}
