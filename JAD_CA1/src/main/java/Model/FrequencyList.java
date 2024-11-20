package Model;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class FrequencyList implements FrequencyCRUD {
	private Connection getConnection() {
		try {
		// Step1: Load JDBC Driver
		Class.forName("com.mysql.jdbc.Driver");
		
		// Step 2: Define Connection URL
		String connURL ="jdbc:mysql://localhost/jad_db1?user=root&password=mypassword&serverTimezone=UTC";
		
		// Step 3: Establish connection to URL
		return DriverManager.getConnection(connURL);
		} catch (SQLException e) {
			System.out.println("Sql Error: " + e);
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found: " + e);
		}
	}

	@Override
	public void addFrequency(Frequency frequency) throws SQLException {
		String sql = "INSERT INTO frequency (frequency_id, frequency) VALUES (?, ?)";
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			
			pstmt.setInt(1, frequency.getFrequencyId());
			pstmt.setString(2, frequency.getFrequency());

			pstmt.executeUpdate();

			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					frequency.setFrequencyId(generatedKeys.getInt(1));
				}
			}
		}
	}

	@Override
	public Frequency getFrequencyById(int id) throws SQLException {
		String sql = "SELECT * FROM frequency WHERE frequency_id = ?";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

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
		String sql = "SELECT * FROM frequency";

		try (Connection conn = getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				frequency.add(new Frequency(rs.getInt("frequency_id"), rs.getString("frequency")));
			}
		}
		return frequency;
	}

	@Override
	public void updateFrequency(Frequency frequency) throws SQLException {
		String sql = "UPDATE frequency SET frequency = ? WHERE frequency_id = ?";

		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, frequency.getFrequencyId());
			pstmt.setString(2, frequency.getFrequency());

			pstmt.executeUpdate();
		}
	}

	@Override
	public void deleteFrequency(int id) throws SQLException {
		String sql = "DELETE FROM frequency WHERE frequency_id = ?";

		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		}
	}
}