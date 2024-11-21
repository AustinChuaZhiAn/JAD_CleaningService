package Model;

public class UserAccount {

	private int user_id;
	private String username;
	private String password;
	private int role_id;

	
    public UserAccount() {}
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public UserAccount(int user_id, String username, String password, int role_id) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.password = password;
		this.role_id = role_id;
	}
	
}
