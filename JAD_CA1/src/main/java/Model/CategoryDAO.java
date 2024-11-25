package Model;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CategoryDAO {
    ArrayList<Category> getAllCategory() throws SQLException;
    Integer getTotalCategory() throws SQLException;
    Category getCategoryById(int category_id) throws SQLException;
    Category getCategoryByName(String category_name) throws SQLException;
    boolean createCategory(Category category) throws SQLException;
    boolean updateCategory(Category category) throws SQLException;
    boolean deleteCategory(int category_id) throws SQLException;
}