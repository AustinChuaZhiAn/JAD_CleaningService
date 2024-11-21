package Model;

import java.sql.Timestamp;

public class UserDetails {
    private int user_details_id;
    private int user_id;
    private String email;
    private String phone_number;
    private Timestamp created_at;
    private Timestamp updated_at;
    
    // Constructors
    public UserDetails() {}
    
    // Getters and Setters
    public int getUser_details_id() {
        return user_details_id;
    }
    
    public void setUser_details_id(int user_details_id) {
        this.user_details_id = user_details_id;
    }
    
    public int getUser_id() {
        return user_id;
    }
    
    public UserDetails(int user_details_id, int user_id, String email, String phone_number, Timestamp created_at,
			Timestamp updated_at) {
		super();
		this.user_details_id = user_details_id;
		this.user_id = user_id;
		this.email = email;
		this.phone_number = phone_number;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone_number() {
        return phone_number;
    }
    
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
    
    public Timestamp getCreated_at() {
        return created_at;
    }
    
    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
    
    public Timestamp getUpdated_at() {
        return updated_at;
    }
    
    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}