package Model;

public class Cleaner {
	private int cleaner_id;
	private String cleaner_name;
	private int cleaner_contact;
	
	public Cleaner(int cleaner_id, String cleaner_name, int cleaner_contact) {
		this.cleaner_id = cleaner_id;
		this.cleaner_name = cleaner_name;
		this.cleaner_contact = cleaner_contact;
	}

	public int getCleaner_id() {
		return cleaner_id;
	}

	public void setCleaner_id(int cleaner_id) {
		this.cleaner_id = cleaner_id;
	}

	public String getCleaner_name() {
		return cleaner_name;
	}

	public void setCleaner_name(String cleaner_name) {
		this.cleaner_name = cleaner_name;
	}

	public int getCleaner_contact() {
		return cleaner_contact;
	}

	public void setCleaner_contact(int cleaner_contact) {
		this.cleaner_contact = cleaner_contact;
	}
}
