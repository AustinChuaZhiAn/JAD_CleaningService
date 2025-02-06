package Model;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingToService {
	private int bookingToService_id;
    private int booking_id;
    private int service_id;
    private int address_id;
    private LocalTime time;
    private LocalDate date;
    private int cleaner_id;
    private int status_id;
    private String status_name;
    private String specialRequest;

    public BookingToService(int bookingToService_id, int booking_id, int service_id, int address_id, LocalTime time, LocalDate date,
    		int cleaner_id, String specialRequest, int status_id, String status_name) {
    	this.bookingToService_id = bookingToService_id; 
        this.booking_id = booking_id;
        this.service_id = service_id;
        this.specialRequest = specialRequest;
        this.address_id = address_id;
        this.time = time;
        this.date = date;
        this.cleaner_id = cleaner_id;
        this.status_id = status_id;
        this.status_name = status_name;
    }

	public int getBookingToService_id() {
		return bookingToService_id;
	}

	public void setBookingToService_id(int bookingToService_id) {
		this.bookingToService_id = bookingToService_id;
	}

	public int getBooking_id() {
		return booking_id;
	}

	public void setBooking_id(int booking_id) {
		this.booking_id = booking_id;
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

	public int getAddress_id() {
		return address_id;
	}

	public void setAddress_id(int address_id) {
		this.address_id = address_id;
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

	public int getStatus_id() {
		return status_id;
	}

	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}

	public String getStatus_name() {
		return status_name;
	}

	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}
}
