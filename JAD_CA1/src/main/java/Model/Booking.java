package Model;

public class Booking {
    private int booking_id;
    private int user_id;       
    private int status_id;
    private String status;
    private String feedback;

    public Booking(int booking_id, int user_id, int status_id, String status, String feedback) {
        this.booking_id = booking_id;
        this.user_id = user_id;
        this.status_id = status_id;
        this.status = status;
        this.feedback = feedback;
    }

	public int getBooking_id() {
		return booking_id;
	}

	public void setBooking_id(int booking_id) {
		this.booking_id = booking_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	public int getStatus_id() {
		return status_id;
	}

	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

}

