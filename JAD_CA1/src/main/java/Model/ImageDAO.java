package Model;

import java.sql.SQLException;

public interface ImageDAO {
    int insertImage(String imgUrl, String deleteHash) throws SQLException;
    boolean updateImage(int imgId, String imgUrl, String deleteHash) throws SQLException;
    boolean deleteImage(int imgId) throws SQLException;
    Image getImageById(int imgId) throws SQLException;
}