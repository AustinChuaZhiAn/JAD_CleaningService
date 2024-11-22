package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import utils.DatabaseConnection;

public class CleanerList implements CleanerRead{
	@Override
    public List<Cleaner> getAllCleaner() throws SQLException {
		List<Cleaner> cleanerList = new ArrayList<>();
		String sql = "SELECT * FROM cleaner";

		try (Connection conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				cleanerList.add(new Cleaner(rs.getInt("cleaner_id")
						,rs.getString("cleaner_name"), rs.getInt("cleaner_contact")));
			}
		}
		return cleanerList;
    }
}
