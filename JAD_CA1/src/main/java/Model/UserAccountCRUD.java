package Model;

import java.sql.SQLException;
import java.util.List;

public interface UserAccountCRUD {
    //Authentication acc
    UserAccount verifyUser(String username, String password) throws SQLException;
    Integer getTotalUser() throws SQLException;
    //CRUD for user
    boolean createUser(UserAccount user, UserDetails userDetails) throws SQLException;
    List<UserAccount> getAllUser() throws SQLException;
    boolean updateUser(UserAccount user) throws SQLException;
    boolean deleteUser(int id) throws SQLException;
    UserAccount getUserByUsername(String username) throws SQLException;
    UserAccount getUserById(int id) throws SQLException;  // Added this method
    UserDetails getUserDetailsByEmail(String email) throws SQLException;
    UserDetails getUserDetailsByPhone(String phone) throws SQLException;
    UserDetails getUserDetailsByUserId(int id) throws SQLException;
    void updateUsernameByUserId(String username, int id) throws SQLException;
    void updateUserDetailsByUserId(String email, String phone, int id) throws SQLException;
}