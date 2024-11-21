package Model;

public class Address {
	private int address;
	private int user_details;
	private int postal_code;
	private String block_number;
	private String street_name;
	private String unit_number;
	private String building_name;
	private int address_type_id;
	
	public Address(int address, int user_details, int postal_code, String block_number
			, String street_name, String unit_number, String building_name, int address_type_id) {
		this.address = address;
		this.user_details = user_details;
		this.postal_code = postal_code;
		this.block_number = block_number;
		this.street_name = street_name;
		this.unit_number = unit_number;
		this.building_name = building_name;
		this.address_type_id = address_type_id;
	}

	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}

	public int getUser_details() {
		return user_details;
	}

	public void setUser_details(int user_details) {
		this.user_details = user_details;
	}

	public int getPostal_code() {
		return postal_code;
	}

	public void setPostal_code(int postal_code) {
		this.postal_code = postal_code;
	}

	public String getBlock_number() {
		return block_number;
	}

	public void setBlock_number(String block_number) {
		this.block_number = block_number;
	}

	public String getStreet_name() {
		return street_name;
	}

	public void setStreet_name(String street_name) {
		this.street_name = street_name;
	}

	public String getUnit_number() {
		return unit_number;
	}

	public void setUnit_number(String unit_number) {
		this.unit_number = unit_number;
	}

	public String getBuilding_name() {
		return building_name;
	}

	public void setBuilding_name(String building_name) {
		this.building_name = building_name;
	}

	public int getAddress_type_id() {
		return address_type_id;
	}

	public void setAddress_type_id(int address_type_id) {
		this.address_type_id = address_type_id;
	}
}
