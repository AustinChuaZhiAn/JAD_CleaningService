package Model;

import java.sql.SQLException;
import java.util.List;


public interface UserAccountCRUD {
	//Authentication acc
	UserAccount verifyUser(String username,String password) throws SQLException;
	
	//CRUD fOr user
    boolean createUser(UserAccount user) throws SQLException;
    UserAccount getUserAccount(String username, String password) throws SQLException;
    List<UserAccount> getAllUser() throws SQLException;
	boolean updateUser(UserAccount user)throws SQLException;
	boolean updateUser(int id)throws SQLException;
	
	
	UserAccount findByUsername(String username) throws SQLException;
}
