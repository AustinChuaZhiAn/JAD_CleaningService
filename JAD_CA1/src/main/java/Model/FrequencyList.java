package Model;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import utils.DatabaseConnection; 

public class FrequencyList implements FrequencyRead {
	@Override
	public Frequency getFrequencyById(int id) throws SQLException {
		String sql = "SELECT * FROM frequencyoption WHERE frequency_id = ?";
		try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, id);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new Frequency(rs.getInt("frequency_id"), rs.getString("frequency"));
				}
			}
		}
		return null;
	}

	@Override
	public List<Frequency> getAllFrequency() throws SQLException {
		List<Frequency> frequency = new ArrayList<>();
		String sql = "SELECT * FROM frequencyoption";

		try (Connection conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				frequency.add(new Frequency(rs.getInt("frequency_id"), rs.getString("frequency")));
			}
		}
		return frequency;
	}
}
