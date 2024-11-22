package Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Booking {
    private int booking_id;
    private int user_id;
    private int address_id;
    private LocalTime time;  
    private LocalDate date;        
    private int cleaner_id;
    private int service_id;
    private String specialRequest;
    private int status_id;

    public Booking(int booking_id, int user_id, int address_id, LocalTime time, LocalDate date, int cleaner_id, 
                   int service_id, String specialRequest, int status_id) {
        this.booking_id = booking_id;
        this.user_id = user_id;
        this.address_id = address_id;
        this.time = time;
        this.date = date;
        this.cleaner_id = cleaner_id;
        this.service_id = service_id;
        this.specialRequest = specialRequest;
        this.status_id = status_id;
    }

	public int getAddress_id() {
		return address_id;
	}

	public void setAddress_id(int address_id) {
		this.address_id = address_id;
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

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getCleaner_id() {
		return cleaner_id;
	}

	public void setCleaner_id(int cleaner_id) {
		this.cleaner_id = cleaner_id;
	}

	public int getService_id() {
		return service_id;
	}

	public void setService_id(int service_id) {
		this.service_id = service_id;
	}

	public String getSpecialRequest() {
		return specialRequest;
	}

	public void setSpecialRequest(String specialRequest) {
		this.specialRequest = specialRequest;
	}

	public int getStatus_id() {
		return status_id;
	}

	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}

}

